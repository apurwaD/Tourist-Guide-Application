/*
 * Country.java
 *
 * assignment : 1
 * Classical  : Country classs
 *
 * @version   : Country.java, v 1.1 2016/23/02 21:12:20
 *
 * @author    : Apurwa Dandekar  Id$ ard5145
 *              
 *
 */
import java.util.ArrayList;
import java.util.List;

public class Country {

	List<String> currencies = new ArrayList<>();
	List<String> timeZones = new ArrayList<>();
	List<String> languages = new ArrayList<>();
	String region;
	String capital;
	Double area;

	/*
	 * function : method
	 * 
	 * @param Snone .
	 * 
	 * @return List
	 */
	public List<String> getCurrencies() {
		return currencies;
	}

	/*
	 * function : method
	 * 
	 * @param List .
	 * 
	 * @return none
	 */
	public void setCurrencies(List<String> currencies) {
		this.currencies = currencies;
	}

	/*
	 * function : method
	 * 
	 * @param none .
	 * 
	 * @return List
	 */
	public List<String> getTimeZones() {
		return timeZones;
	}

	/*
	 * function : method
	 * 
	 * @param List.
	 * 
	 * @return none
	 */
	public void setTimeZones(List<String> timeZones) {
		this.timeZones = timeZones;
	}

	/*
	 * function : method
	 * 
	 * @param none.
	 * 
	 * @return List
	 */
	public List<String> getLanguages() {
		return languages;
	}

	/*
	 * function : method
	 * 
	 * @param List
	 * 
	 * @return none
	 */
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	
}
