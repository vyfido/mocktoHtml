package com.experimental.util;

/**
 * Implement trivial, process the string for completed data in generation the path
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ExtractInfo {

	/**
	 * Constructor
	 */
	private ExtractInfo() {

	}

	/**
	 * Get a text for remove the additional information
	 * @param text text to process
	 * @param remove text to remove
	 * @param prefix text to remove
	 * @return text result the removed 
	 */
	public static String removeProperties(String text, String remove, String prefix) {
		if(text == null || remove == null || prefix == null) {
			return text;
		}else {
			return text.replace(remove, "").replace(prefix,"");
		}
	}

	/**
	 * Get a text for remove the additional information, for include the extension html
	 * @param text text to process
	 * @param remove text to remove
	 * @param prefix text to remove
	 * @return text result the removed 
	 */	
	public static String removePropertiesHtml(String text, String remove, String prefix) {
		return text.replace(remove, "").replace(prefix,".html");
	}

	/**
	 * Get the name the a file
	 * @param text text to process
	 * @return name the file
	 */
	public static String getName(String text) {
		if(text != null && text.length() > 0) {
		return text.substring(text.lastIndexOf("/")+1, text.length());
		}else {
			return text;
		}
	}

	/**
	 * Get the directory for scenario defined
	 * @param dir directory to use
	 * @return path relative for scenario
	 */
	public static String getDirScenarie(String dir) {
		if(dir != null && dir.length() > 0) {
		return dir.substring(0,dir.lastIndexOf("/"));
		}else {
			return dir;
		}
	}

	/**
	 * Get a text for remove the additional information
	 * @param text text to process
	 * @param remove text to remove
	 * @return text result the removed 
	 */
	public static String getScenarie(String text, String remove) {
		return text.replace(remove, "");
	}

}
