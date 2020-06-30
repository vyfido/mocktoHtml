package com.experimental.page;

import static j2html.TagCreator.a;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.each;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.hr;
import static j2html.TagCreator.html;
import static j2html.TagCreator.li;
import static j2html.TagCreator.link;
import static j2html.TagCreator.p;
import static j2html.TagCreator.rawHtml;
import static j2html.TagCreator.scriptWithInlineFile;
import static j2html.TagCreator.title;
import static j2html.TagCreator.ul;

import java.util.Arrays;
import java.util.Date;

import com.experimental.core.Export;
import com.experimental.util.DateUtil;
import com.experimental.util.ExtractInfo;

import j2html.tags.DomContent;

/**
 * Implement the generation the index page, for wiremocks
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 *
 */
public class HtmlIndexPage implements Export{

	/**root directory*/
	private String rootDir = null;
	
	/**output directory*/
	private String outDir = null;

	/**list the files for process*/
	public String[] content = {};

	/**
	 * Constructor
	 * @param content list the files
	 */
	public HtmlIndexPage(String[] content) {
		this.content = content;
	}

	/**
	 * define the root directory
	 * @param rootDir directory root
	 */
	public void setRootDirectory(String rootDir) {
		this.rootDir = rootDir;
	}
	
	/**
	 * define output directory
	 * @param outDir output directory
	 */
	public void setOutDirectory(String outDir) {
		this.outDir = outDir;
	}

	/**
	 * Generate the initial text for page
	 * @return DOM, for main block body
	 */
	private DomContent welcome() {
		return div(
				attrs("#welcome"),
				h3("Wiremock Index"),
				p("This are the list the current stub that are enabled for your test"),
				p(content.length+" wiremocks transfrom in this index.")
				);
	}

	/**
	 * Generate the footer page, with copyright notice
	 * @return DOM, for main block body
	 */
	private DomContent copyright(){
		return div().withClass("copyright").with(
				rawHtml("vyfido Inc&nbsp;&copy&nbsp; - <b>last updated</b>: "+DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss"))
				);
	}	

	/**
	 * (non-Javadoc)
	 * @see com.experimental.core.Export
	 */
	@Override
	public String toHtml() {
		return html(
				head(
						title("Mock dictionary"),
						link().withRel("stylesheet").withHref("../lib/css/core/bootstrap.min.css")
						),
				body(
						div(
								attrs(".container"),
								welcome(),
								hr(),
								div(
										attrs(".row"),
										ul(
												each(Arrays.asList(content), c -> li(
														a(ExtractInfo.removeProperties(c, rootDir, ".json"))
														.withHref("/docs/"+ExtractInfo.removePropertiesHtml(c, rootDir, ".json"))
														)
														)
												)
										),
								hr(),
								copyright()
								)
						),
				   scriptWithInlineFile(outDir+"/lib/js/core/bootstrap.min.js"),
				   scriptWithInlineFile(outDir+"/lib/js/core/jquery-3.3.1.min.js")
				).render();
	}

}
