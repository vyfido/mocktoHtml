package com.experimental.page;

import static j2html.TagCreator.a;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.body;
import static j2html.TagCreator.div;
import static j2html.TagCreator.h3;
import static j2html.TagCreator.head;
import static j2html.TagCreator.hr;
import static j2html.TagCreator.html;
import static j2html.TagCreator.link;
import static j2html.TagCreator.p;
import static j2html.TagCreator.rawHtml;
import static j2html.TagCreator.title;

import java.util.Date;

import com.experimental.core.Export;
import com.experimental.util.DateUtil;
import com.experimental.util.ExtractInfo;
import com.fasterxml.jackson.databind.JsonNode;

import j2html.tags.DomContent;

/**
 * Implement the generation the HTML page, for only wiremock request
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public class HtmlPage implements Export{
		
	private JsonNode node = null;
	
	private String rootDir = null;
	
	private String resource = null;
	
	public HtmlPage(JsonNode node) {
		this.node = node;
	}

	public void setRootDirectory(String rootDir) {
		this.rootDir = rootDir;
	}
	
	public void setName(String resource) {
		this.resource = resource;
	}
	
	public String getRootDir() {
		return rootDir;
	}
	
	public String toString() {
		return this.rootDir != null ? this.rootDir : "";
	}
	
	private String internalRequest(JsonNode request) {
		StringBuilder sb = new StringBuilder("<br/>");
		sb.append("<table class='table table-hover table1").append("'  >");
		sb.append("<tr>");
		String name = request.at("/request/name").textValue();
		name = name == null ? "Without details" : name;
		sb.append("<th >").append("Details").append("</th>");
		sb.append(" <td>").append(name).append("</td>");
		sb.append("</tr><tr>");
		sb.append("<th>").append("Verb").append("</th>");
		sb.append(" <td>").append(request.at("/request/method").textValue()).append("</td>");
		sb.append("</tr><tr>");
		sb.append("<th>").append("End-point").append("</th>");
		sb.append(" <td>").append(request.at("/request/url").textValue()).append("</td>");
		sb.append("</tr>");
		sb.append("</tr><tr>");
		sb.append("<th>").append("Response Status").append("</th>");
		sb.append("<td>").append(request.at("/response/status").textValue());
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tr><tr>");
		sb.append("<th>").append("Response Content-Type");
		sb.append("</th>");
		sb.append("<td>").append(request.at("/response/headers/Content-Type").textValue());
		sb.append("</td>");
		sb.append("</tr>");
		
		sb.append("<tr><td colspan=2>&nbsp;</td></tr>");
		sb.append("</table>");
		
		return sb.toString();
	}
	
	private DomContent requestDivs(JsonNode request) {
		return div(
				  attrs(".request"),
				  rawHtml(internalRequest(request))
				);
	}

	/**
	 * Generate the initial text for page
	 * @return DOM, for main block body
	 */
	private DomContent welcome() {
		return div(
				attrs("#welcome"),
				h3("Details for mock:"),
				  p(
						"This are the list the current stub for: "+ExtractInfo.getName(resource)
					),hr(),
				 a("Back").withHref("/docs/index.html")
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
								div(
										attrs(".row"),
								div( attrs("#req col-lg-12"),
										requestDivs(node)
								),
								copyright()
								)
						)
				 )
				).render();
	}

}
