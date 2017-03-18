/*
 * CallWebService.java
 *
 * assignment : 1
 * Classical  : Write a program to call APis
 *
 * @version   : CallWebService.java, v 1.1 2016/23/02 21:12:20
 *
 * @author    : Apurwa Dandekar  Id$ ard5145
 *              
 *
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//class to call various apis
public class CallWebService {

	Country country = new Country();

	/*
	 * function : method
	 * 
	 * @param String Countryname .
	 * 
	 * @return Country Object
	 */
	public Country getCountryInfo(String countryName) {

		// call APi which will fetch the details about the countries and store
		// it in the Country class
		country = new Country();
		countryName = countryName.toLowerCase();
		try {
			String urlFrom = "http://restcountries.eu/rest/v1/name/"
					+ countryName;
			String jsonStringfromCountry = callUrl(urlFrom);
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(jsonStringfromCountry.toString());
			JSONArray jsonOutput = (JSONArray) obj;
			JSONObject countryObj = new JSONObject();
			Iterator<?> iterator = jsonOutput.iterator();

			// iterate to get the country specified in parameter
			while (iterator.hasNext()) {
				countryObj = (JSONObject) iterator.next();
				String name = (String) countryObj.get("name");
				if (name.toLowerCase().equals(countryName)) {
					break;
				}
			}

			// get the curreny details
			JSONArray currencies = (JSONArray) countryObj.get("currencies");
			Iterator<?> currenciesIt = currencies.iterator();
			String currency = new String();
			while (currenciesIt.hasNext()) {
				currency = (String) currenciesIt.next();
				country.currencies.add(currency);
			}

			// language details
			JSONArray languages = (JSONArray) countryObj.get("languages");
			Iterator<?> languagesIt = languages.iterator();
			String language = new String();
			while (languagesIt.hasNext()) {
				language = (String) languagesIt.next();
				country.languages.add(language);
			}

			// get timezone details
			JSONArray timeZones = (JSONArray) countryObj.get("timezones");
			JSONObject timeZone = new JSONObject();

			Iterator<?> timeZonesIt = timeZones.iterator();
			String b = new String();
			while (timeZonesIt.hasNext()) {
				b = (String) timeZonesIt.next();
				country.timeZones.add(b);
			}

		} catch (Exception ex) {

		}
		return country;
	}

	/*
	 * function : method
	 * 
	 * @param String, String Countryname .
	 * 
	 * @return Double
	 */
	Double convertCurrency(String source, String destination)
			throws MalformedURLException {
		/*
		 * call APi which will return the current excahnge rate between source
		 * and destination country
		 */
		
		String urlCurrency = "http://apilayer.net/api/live?access_key=acf2da50fbb6484ab933a5c5f56bcc00";
		URL url;
		Double exchangeRate = 0.0;
		try {
			String currencyUrl = callUrl(urlCurrency);
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(currencyUrl.toString());
			JSONObject jsonObj = (JSONObject) obj;
			JSONObject rates = (JSONObject) jsonObj.get("quotes");
			Double rateFrom = 1.0;
			if (!source.equals("USD")) {
				rateFrom = (Double) rates.get("USD" + source);
			}

			Double rateTo = 1.0;
			if (!destination.equals("USD")) {
				rateTo = (Double) rates.get("USD" + destination);
			}
			exchangeRate = rateTo / rateFrom;

		} catch (Exception ex) {
			return exchangeRate;
		}
		return exchangeRate;
	}

	/*
	 * function : method
	 * 
	 * @param String, String, String .
	 * 
	 * @return String
	 */
	String translate(String textFrom, String languageFrom, String languageTo) {
		/*
		 * calls Api which will Translate the text into the language passed in
		 * the above parameter
		 */

		String urlTranslate = "https://translate.yandex.net/api/v1.5/tr/translate?key=trnsl.1.1.20160220T003605Z.5abc9a5d0c01ad1e.8b191f8218d9af3087273e871eea2fb178b778f1&text="
				+ textFrom
				+ ""
				+ "&lang="
				+ languageFrom
				+ "-"
				+ languageTo
				+ "&[format=plain]";
		URL url;
		String result = null;
		try {
			String xmlText = callUrl(urlTranslate);
			DocumentBuilder docBuild = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xmlText));
			Document document = docBuild.parse(inputSource);
			NodeList nodes = document.getElementsByTagName("Translation");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList text = element.getElementsByTagName("text");
				Element textNode = (Element) text.item(0);
				result = getValue(textNode);

			}

		} catch (Exception ex) {

		} finally {
			return result;
		}
	}

	/*
	 * function : method
	 * 
	 * @param Element .
	 * 
	 * @return String
	 */
	public static String getValue(Element e) {
		Node node = e.getFirstChild();
		return node.getNodeValue();
	}

	/*
	 * function : method
	 * 
	 * @param String location .
	 * 
	 * @return ArrayList
	 */
	ArrayList<Double> findLocation(String location) throws ParseException {
		/*
		 * This will call the google places APi with the name provided and will
		 * return the longitude and latitude of that place
		 */
		location = location.replaceAll(" ", "%20").toLowerCase();
		
		ArrayList<Double> latLng = new ArrayList<Double>();
		String urlString = "http://maps.googleapis.com/maps/api/geocode/json?address="
				+ location + "&sensor=true";
		String jsonResult = callUrl(urlString);
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(jsonResult.toString());
		JSONObject result = (JSONObject) obj;
		JSONArray locationAtt = (JSONArray) result.get("results");
		if (locationAtt.size() != 0) {
			JSONObject loc = (JSONObject) locationAtt.get(0);
			JSONObject geometry = (JSONObject) loc.get("geometry");
			JSONObject loct = (JSONObject) geometry.get("location");
			Double lat = 0.0, lng = 0.0;
			if (loct.get("lat") instanceof Long) {
				Long lat_l = (Long) loct.get("lat");
				lat = lat_l.doubleValue();
			}
			if (loct.get("lng") instanceof Long) {
				Long lng_l = (Long) loct.get("lng");
				lng = lng_l.doubleValue();
			}

			if (loct.get("lat") instanceof Double) {
				lat = (Double) loct.get("lat");
			}

			if (loct.get("lng") instanceof Double) {
				lng = (Double) loct.get("lng");
			}

			latLng.add(lat);
			latLng.add(lng);
			return latLng;
		} else {
			latLng.add(0.0);
			latLng.add(0.0);
			return latLng;
		}
	}

	/*
	 * function : method
	 * 
	 * @param Arraylist,String Countryname .
	 * 
	 * @return HashMap
	 */
	HashMap<String, String> findNearByPlaces(ArrayList<Double> langLat,
			String type) throws ParseException {

		/*
		 * Will take the longitude and latitude passed as a parameter will
		 * return the nearby place depending upon the type in the form of
		 * longitude and latitude
		 */

		HashMap<String, String> locationAttributes = new HashMap<String, String>();
		String location = langLat.get(0) + ",";
		location += langLat.get(1);

		String placeUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
				+ location
				+ "&radius=500&types="
				+ type
				+ "&key=AIzaSyCOGFEo6K74co0IlpuHpGdIjYrY5Vxj0dI";

		String resultJSon = callUrl(placeUrl);
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(resultJSon);
		JSONObject result = (JSONObject) obj;
		JSONArray locationAtt = (JSONArray) result.get("results");
		JSONObject resultObj = new JSONObject();
		Iterator<?> iterator = locationAtt.iterator();
		while (iterator.hasNext()) {
			resultObj = (JSONObject) iterator.next();
			String locationName = (String) resultObj.get("name");
			JSONObject geometry = (JSONObject) resultObj.get("geometry");
			JSONObject loct = (JSONObject) geometry.get("location");
			Double lat = (Double) loct.get("lat");
			Double lng = (Double) loct.get("lng");
			String longitudeLatitude = lat + "," + lng;
			String mapUrl = createMapUrl(longitudeLatitude);
			locationAttributes.put(locationName, mapUrl);
		}

		return locationAttributes;

	}

	/*
	 * function : method
	 * 
	 * @param String .
	 * 
	 * @return String
	 */
	String createMapUrl(String attributes) {
		// append longitude and latitude to the google map url
		String url = "https://maps.google.com/?q=@" + attributes;
		return url;
	}

	/*
	 * function : method
	 * 
	 * @param String url .
	 * 
	 * @return String
	 */
	@SuppressWarnings("finally")
	String callUrl(String urlString) {

		/*
		 * calls different restful APis and return the response received in the
		 * form of a string
		 */
		StringBuffer stringVal = new StringBuffer();
		try {
			URL url = new URL(urlString);
			HttpURLConnection httpConnection;
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();
			BufferedReader bufReader = new BufferedReader(
					new InputStreamReader(httpConnection.getInputStream()));
			String tempString;

			while ((tempString = bufReader.readLine()) != null) {
				stringVal.append(tempString);
				stringVal.append("\n");
			}

		} catch (Exception ex) {

		} finally {
			return stringVal.toString();
		}

	}

}
