package com.experimental.core;

/**
 * Define the basic operation for export html page generated
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public interface Export {

	/**
	 * Generate the page html result the compilation
	 * @return string with html page
	 */	
	String toHtml();

}
