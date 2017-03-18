/*
 *  TouristGuide.java
 *
 * assignment : 1
 * Classical  : Write a program to populate the GUI and handle events
 *
 * @version   : TouristGuide.java, v 1.1 2016/23/02 21:12:20
 *
 * @author    : Apurwa Dandekar  Id$ ard5145
 *              
 *
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.json.simple.parser.ParseException;

/*
 * class which will Populate the GUI and will handle user interaction events
 */
public class TouristGuide {

	CallWebService makeCall = new CallWebService();
	Map<String, ArrayList<String>> listOfCountries = new HashMap<String, ArrayList<String>>();
	JComboBox countryListFrom = new JComboBox();
	JComboBox countryListTo = new JComboBox();
	JComboBox currencyListFrom = new JComboBox();
	JComboBox currencyListTo = new JComboBox();
	JComboBox languages = new JComboBox();
	Country sourceCountry = new Country();
	Country destCountry = new Country();
	JLabel regionText = new JLabel();
	JLabel exchangeText = new JLabel();
	JTextArea sourceText = new JTextArea();
	JTextArea destText = new JTextArea();
	JTextField placeQuery = new JTextField();
	JComboBox placeType = new JComboBox();
	JTextArea placeResult = new JTextArea();

	/*
	 * Counstructor call
	 */
	TouristGuide() throws ParseException {
		try {
			getCountryList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// create a mainpanel and frame
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new java.awt.Color(200, 200, 250));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JFrame frame = new JFrame("Guide");

		// crate a captionpanel for caption for the App
		JPanel captionPanel = new JPanel();
		JLabel captionLabel = new JLabel(
				"Tourist Guide: Select Source and Destination Country");
		captionPanel.add(captionLabel);
		captionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		captionPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		captionPanel.setPreferredSize(new Dimension(900, 100));
		captionPanel.setLayout(new FlowLayout());
		captionPanel.setMaximumSize(new Dimension(900, 100));
		captionPanel.setBackground(new java.awt.Color(150, 160, 250));

		// craate a panel to select the countries
		JLabel from1 = new JLabel("Source Country:");
		JLabel to1 = new JLabel("Destination Country:");
		JPanel convertPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JButton enterButton = new JButton("Get Details");
		convertPanel.add(from1);
		convertPanel.add(countryListFrom);
		convertPanel.add(to1);
		convertPanel.add(countryListTo);
		convertPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		convertPanel.setPreferredSize(new Dimension(700, 30));
		convertPanel.setLayout(new GridLayout());
		convertPanel.setMaximumSize(new Dimension(900, 30));
		convertPanel.setBackground(new java.awt.Color(200, 200, 250));

		// buttonpanel to get the details
		buttonPanel.add(enterButton);
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setPreferredSize(new Dimension(700, 30));
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setMaximumSize(new Dimension(900, 30));
		buttonPanel.setBackground(new java.awt.Color(200, 200, 250));
		EnterButton enter = new EnterButton();
		enterButton.addActionListener(enter);

		// captionpanel for geting exchange rate functionality
		JPanel captionPanel2 = new JPanel();
		JLabel captionLabel2 = new JLabel(
				"Select Currency For the Conversion for the selected countries");
		captionPanel2.add(captionLabel2);
		captionPanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
		captionPanel2.setAlignmentY(Component.CENTER_ALIGNMENT);
		captionPanel2.setPreferredSize(new Dimension(900, 60));
		captionPanel2.setLayout(new FlowLayout());
		captionPanel2.setMaximumSize(new Dimension(900, 60));
		captionPanel2.setBackground(new java.awt.Color(150, 160, 250));

		JPanel displayResultPanel = new JPanel();
		displayResultPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		displayResultPanel.setPreferredSize(new Dimension(700, 30));
		displayResultPanel.setLayout(new GridLayout());
		displayResultPanel.setMaximumSize(new Dimension(700, 30));
		displayResultPanel.setBackground(new java.awt.Color(200, 200, 250));

		// currency panel to select between those countries currencies
		JPanel currencyConvertPanel = new JPanel();
		JLabel from = new JLabel("Source Country Curreny:");
		JLabel to = new JLabel("Destination Country Currency:");
		currencyListFrom.addItem("Select");
		currencyListTo.addItem("Select");
		currencyConvertPanel.add(from);
		currencyConvertPanel.add(currencyListFrom);
		currencyConvertPanel.add(to);
		currencyConvertPanel.add(currencyListTo);
		currencyConvertPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		currencyConvertPanel.setPreferredSize(new Dimension(700, 30));
		currencyConvertPanel.setLayout(new GridLayout());
		currencyConvertPanel.setMaximumSize(new Dimension(900, 30));
		currencyConvertPanel.setBackground(new java.awt.Color(200, 200, 250));

		// button panel to get current excahnge rate
		JPanel buttonPanelExchange = new JPanel();
		JButton exchangeButton = new JButton("Get Current Exchange Rate");
		buttonPanelExchange.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanelExchange.setPreferredSize(new Dimension(700, 30));
		buttonPanelExchange.setLayout(new FlowLayout());
		buttonPanelExchange.setMaximumSize(new Dimension(900, 30));
		buttonPanelExchange.add(exchangeButton);
		SubmitButton submit = new SubmitButton();
		exchangeButton.addActionListener(submit);
		buttonPanelExchange.setBackground(new java.awt.Color(200, 200, 250));

		// panel to show the current exchange rate
		JLabel exchangeRate = new JLabel();
		exchangeRate.setText("Exchange Rate Result: ");
		JPanel exchangeResultPanel = new JPanel();
		exchangeResultPanel.add(exchangeRate);
		exchangeResultPanel.add(exchangeText);
		exchangeResultPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		exchangeResultPanel.setPreferredSize(new Dimension(700, 30));
		exchangeResultPanel.setLayout(new GridLayout());
		exchangeResultPanel.setMaximumSize(new Dimension(900, 30));
		exchangeResultPanel.setBackground(new java.awt.Color(200, 200, 250));

		// caption panle to tarnslate the text
		JPanel captionPanel3 = new JPanel();
		JLabel captionLabel3 = new JLabel(
				"Write Text in the text Area and select one of the langauges of destination Country");
		captionPanel3.add(captionLabel3);
		captionPanel3.setAlignmentX(Component.CENTER_ALIGNMENT);
		captionPanel3.setAlignmentY(Component.CENTER_ALIGNMENT);
		captionPanel3.setPreferredSize(new Dimension(900, 60));
		captionPanel3.setLayout(new FlowLayout());
		captionPanel3.setMaximumSize(new Dimension(900, 60));
		captionPanel3.setBackground(new java.awt.Color(150, 160, 250));

		// panel to translate the text in the corresponding
		JPanel translatorPanel = new JPanel();
		//destText.setFont(Font.decode("utf-8"));
		JLabel sourcelanguage = new JLabel("Enter Text To Translate:");
		JLabel destlanguage = new JLabel("Translated Text is:");
		languages.addItem("Select");
		translatorPanel.add(sourcelanguage);
		translatorPanel.add(sourceText);
		translatorPanel.add(languages);
		translatorPanel.add(destlanguage);
		// destText.setEditable(false);
		translatorPanel.add(destText);
		translatorPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		translatorPanel.setPreferredSize(new Dimension(700, 70));
		translatorPanel.setLayout(new GridLayout());
		translatorPanel.setMaximumSize(new Dimension(900, 70));
		translatorPanel.setBackground(new java.awt.Color(200, 200, 250));

		// button for transalte event
		JPanel translateButtonPanel = new JPanel();
		JButton tranlateButton = new JButton("Translate");
		translateButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		translateButtonPanel.setPreferredSize(new Dimension(700, 70));
		translateButtonPanel.setLayout(new FlowLayout());
		translateButtonPanel.setMaximumSize(new Dimension(900, 70));
		TranslatorButton translate = new TranslatorButton();
		tranlateButton.addActionListener(translate);
		translateButtonPanel.add(tranlateButton);
		translateButtonPanel.setBackground(new java.awt.Color(200, 200, 250));

		// caption for search places functionality
		JPanel captionPanel4 = new JPanel();
		JLabel captionLabel4 = new JLabel(
				"Enter the Name of The place below and Select the type of place you are searching for");
		captionPanel4.add(captionLabel4);
		captionPanel4.setAlignmentX(Component.CENTER_ALIGNMENT);
		captionPanel4.setAlignmentY(Component.CENTER_ALIGNMENT);
		captionPanel4.setPreferredSize(new Dimension(900, 60));
		captionPanel4.setLayout(new FlowLayout());
		captionPanel4.setMaximumSize(new Dimension(900, 60));
		captionPanel4.setBackground(new java.awt.Color(150, 160, 250));

		// panel to search the nearby places
		JPanel placePanel = new JPanel();
		JButton searchButton = new JButton("Search Places");
		SearchButton search = new SearchButton();
		searchButton.addActionListener(search);
		JLabel placeText = new JLabel("Enter Place:");
		placeType.addItem("food");
		placeType.addItem("hospital");
		placeType.addItem("bank");
		placeType.addItem("Restaurant");
		placePanel.add(placeText);
		placePanel.add(placeQuery);
		placePanel.add(placeType);
		placePanel.add(searchButton);
		placePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		placePanel.setPreferredSize(new Dimension(700, 70));
		placePanel.setLayout(new GridLayout());
		placePanel.setMaximumSize(new Dimension(900, 70));
		placePanel.setBackground(new java.awt.Color(200, 200, 250));

		// panel to display the details about nearby places
		JPanel placeDetailsPanels = new JPanel();
		JScrollPane scroll = new JScrollPane(placeResult);
		placeDetailsPanels.add(scroll, BorderLayout.CENTER);
		// placeDetailsPanels.add(placeResult);
		placeDetailsPanels.setAlignmentX(Component.CENTER_ALIGNMENT);
		placeDetailsPanels.setPreferredSize(new Dimension(700, 500));
		placeDetailsPanels.setLayout(new GridLayout());
		placeDetailsPanels.setMaximumSize(new Dimension(900, 500));
		placeDetailsPanels.setBackground(new java.awt.Color(200, 200, 250));

		// add all the panel into a main panel
		mainPanel.add(captionPanel);
		mainPanel.add(convertPanel);
		mainPanel.add(buttonPanel);
		mainPanel.add(captionPanel2);
		mainPanel.add(displayResultPanel);
		mainPanel.add(currencyConvertPanel);
		mainPanel.add(buttonPanelExchange);
		mainPanel.add(exchangeResultPanel);
		mainPanel.add(captionPanel3);
		mainPanel.add(translatorPanel);
		mainPanel.add(translateButtonPanel);
		mainPanel.add(captionPanel4);
		mainPanel.add(placePanel);
		mainPanel.add(placeDetailsPanels);
		frame.add(mainPanel);

		// set properties of the frame
		frame.setTitle("Tourist Guide");
		frame.setSize(800, 900);
		frame.setMinimumSize(new Dimension(150, 150));
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

	/*
	 * function : to get the list of countries from the file
	 * 
	 * @param none .
	 */
	void getCountryList() throws IOException {
		/*
		 * File fin = new File("Countries.txt"); FileInputStream fis = new
		 * FileInputStream(fin);
		 * 
		 * BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		 */
		InputStream is = getClass().getResourceAsStream("countries.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line = null;
		while ((line = br.readLine()) != null) {
			countryListFrom.addItem(line);
			countryListTo.addItem(line);
		}
		br.close();
	}

	// Actionlister class for enter button
	private class EnterButton implements ActionListener {

		/*
		 * function : actionPerformed
		 * 
		 * @param ActionEvent type of action
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			/*
			 * Update the currencies and language in the dropdown based on the
			 * country selcted
			 */
			CallWebService call1 = new CallWebService();
			CallWebService call2 = new CallWebService();
			String fromCountry = (String) countryListFrom.getSelectedItem();
			String toCountry = (String) countryListTo.getSelectedItem();
			sourceCountry = call1.getCountryInfo(fromCountry);
			destCountry = call2.getCountryInfo(toCountry);

			currencyListFrom.removeAllItems();
			currencyListTo.removeAllItems();

			// update currencies list for source country
			ArrayList<String> fromCountryList = new ArrayList<String>();
			fromCountryList = (ArrayList<String>) sourceCountry.currencies;
			for (int i = 0; i < fromCountryList.size(); i++) {
				currencyListFrom.addItem(fromCountryList.get(i));

			}

			// update currency for destination country
			ArrayList<String> toCountryList = new ArrayList<String>();
			toCountryList = (ArrayList<String>) destCountry.currencies;
			for (int i = 0; i < toCountryList.size(); i++) {
				currencyListTo.addItem(toCountryList.get(i));
			}

			// update language for destination country in the drop down
			languages.removeAllItems();
			ArrayList<String> languagesList = new ArrayList<>();
			languagesList = (ArrayList<String>) destCountry.languages;
			for (int i = 0; i < languagesList.size(); i++) {
				languages.addItem(languagesList.get(i));
			}
			exchangeText.setText("");
		}
	}

	// Actionlistener for Submit button for exchange rate Button
	private class SubmitButton implements ActionListener {

		/*
		 * function : actionPerformed
		 * 
		 * @param ActionEvent type of action
		 */
		public void actionPerformed(ActionEvent e) {
			CallWebService call = new CallWebService();
			String sourceCurrency = (String) currencyListFrom.getSelectedItem();
			String destCurrency = (String) currencyListTo.getSelectedItem();
			// update the excahge rate for selected countries
			try {
				Double result = call.convertCurrency(sourceCurrency,
						destCurrency);
				if (result == 0.0) {
					exchangeText.setText("No exchange rate available");
				} else {
					exchangeText.setText(result.toString());
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Actionlistener for Translator Button
	private class TranslatorButton implements ActionListener {

		/*
		 * function : actionPerformed
		 * 
		 * @param ActionEvent type of action
		 */

		public void actionPerformed(ActionEvent e) {
			// translated the text enter into corresponding selected language of
			// the country
			String textFrom = sourceText.getText();
			String languageSelected = (String) languages.getSelectedItem();
			CallWebService call = new CallWebService();
			String returnText = call
					.translate(textFrom, "en", languageSelected);

				destText.setText(returnText);
			

		}
	}

	// Actionlistener for Search Button
	private class SearchButton implements ActionListener {

		CallWebService call = new CallWebService();

		/*
		 * function : actionPerformed
		 * 
		 * @param ActionEvent type of action
		 */
		public void actionPerformed(ActionEvent e) {
			// search the nearby places and display it the text area below
			String destCountry = (String) countryListTo.getSelectedItem();
			if (destCountry.equals("Select")) {
				destCountry = "";
			}
			ArrayList<Double> longiLati = new ArrayList<Double>();
			HashMap<String, String> locationAttributes = new HashMap<String, String>();
			String place = placeQuery.getText();
			String type = (String) placeType.getSelectedItem();
			try {
				longiLati = call.findLocation(place + " " + destCountry);
				locationAttributes = call.findNearByPlaces(longiLati, type);
				Iterator it = locationAttributes.entrySet().iterator();
				placeResult.setText("");
				while (it.hasNext()) {

					Map.Entry pair = (Map.Entry) it.next();
					String name = pair.getKey() + "\n";
					String mapLink = pair.getValue() + "\n";
					String detailsText = placeResult.getText() + "\n" + name
							+ mapLink;
					placeResult.setText(detailsText);
				}

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	/*
	 * function : main
	 * 
	 * @param array of string (none here)
	 */
	public static void main(String[] args) {
		try {
			TouristGuide tg = new TouristGuide();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
