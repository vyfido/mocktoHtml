package com.experimental.page;

import static j2html.TagCreator.a;
import static j2html.TagCreator.attrs;
import static j2html.TagCreator.body;
import static j2html.TagCreator.br;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import j2html.tags.DomContent;

/**
 * Implement the generation the HTML page, for only scenario request
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public class HtmlScenariesPage implements Export{
	
	private ObjectMapper om = new ObjectMapper();
	
	private JsonNode node = null;
	
	private JsonNode mappings = null;
	
	public HtmlScenariesPage(JsonNode node) {
		this.node = node;
		
		try {
			mappings = om.readValue(node.get("mappings").toString(), JsonNode.class);	
		}catch(Exception e) {
			e.printStackTrace();
		}
				
	}
	
	private String internalDivs(JsonNode requests) {
		StringBuilder sb = new StringBuilder("<br/>");
		if(requests.isArray()) {
			String name = null;
			int cont = 1;
			for(JsonNode request : requests) {
				sb.append("<table class='table table-hover table").append(cont).append("'  >");
				sb.append("<tr>");
				name = request.at("/request/name").textValue();
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
				cont++;
			}
			
		}
		return sb.toString();
	}
	
	private DomContent requestDivs(JsonNode mappings) {
		return div(
				  attrs(".request"),
				  rawHtml(internalDivs(mappings))
				);
	}
	
	private DomContent detailPage() {
		return div(attrs(".row"),
				  h3("Dictionary mocks"),
				  p("Documentation generate for number/factor.json"),hr(),
					 a("Back").withHref("/docs/index.html"),hr()
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
	public String toHtml() {
		return html(
				head(
				 title(node.get("scenarioName").textValue()),
			   link().withRel("stylesheet").withHref("../../lib/css/core/bootstrap.min.css")			   
			   ),body(
			  		 div(attrs(".container "),
			  				div(attrs(".row"),
			  						detailPage()
			  				),
			  				div(attrs(".row"),
			  						requestDivs(this.mappings)
			  				),
			  				copyright()
			  		 )
			   ),br()
				
				).render();
	}

}
