package com.experimental.main;

import java.io.File;
import java.util.Date;

import com.experimental.page.HtmlIndexPage;
import com.experimental.page.HtmlPage;
import com.experimental.page.HtmlScenariesPage;
import com.experimental.util.DateUtil;
import com.experimental.util.ExtractInfo;
import com.experimental.util.FileUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implement the main class for transformer the wiremock(json) files to html resource for generation the
 * index 
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public class MainApp {
	
	/**Evaluator the json*/
	private static final ObjectMapper OM = new ObjectMapper();
	
	/**
	 * Evaluate if the information is the a scenarie or a request
	 * @param node information to evaluate
	 * @return true if is an scenarie or false if is a request
	 */
	private static boolean isScenario(final JsonNode node) {
		return (node.get("scenarioName") != null) && (node.get("mappings") != null)  ;
	}
	
	public static void main(String... args) {
		
		System.out.println("Wiremock transformer");
		System.out.println("Read the files wiremock json in directory mapping for generate the .html for enabled  index.");
		System.out.println("Started: "+DateUtil.toString(new Date(), "YYYY-MM-ss HH:mm:ss") );
		
		OM.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);	
		
		//args[0] 
		
		String rootPath = "/vyfido/applications/wiremock/test/";
		
		String requestPath = rootPath+"mappings";
		
		String outputPath = rootPath+"__files";
		
		FileUtil files = new FileUtil();
		String[] resources = files.searchFiles(requestPath, ".json");
		
		for(String resource : resources) {
			try {
				JsonNode node = OM.readValue(files.readFile(resource), JsonNode.class);
				if(!isScenario(node)) {
					HtmlPage page = new HtmlPage(node);
					page.setRootDirectory(rootPath);
					page.setName(resource);
					
					files.write(outputPath+"/docs/"+ExtractInfo.getName(resource).replace(".json", "")+".html", page.toHtml());
				}else {
					HtmlScenariesPage scenarie = new HtmlScenariesPage(node);		
					File dir = new File(outputPath+"/docs/"+ExtractInfo.getDirScenarie(ExtractInfo.getScenarie(resource, requestPath)));
					dir.mkdir();
					files.write(outputPath+"/docs/"+ExtractInfo.getScenarie(resource, requestPath).replace(".json", "")+".html", scenarie.toHtml());
				}
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
				
		HtmlIndexPage index = new HtmlIndexPage(resources);
		index.setRootDirectory(rootPath+"mappings/");
		index.setOutDirectory(outputPath);		
		files.write(outputPath+"/docs/index.html", index.toHtml()); 
		
		System.out.println("End: "+DateUtil.toString(new Date(), "YYYY-MM-ss HH:mm:ss") );
		System.out.println("See the public directory in wiremock");
	
	}

}
