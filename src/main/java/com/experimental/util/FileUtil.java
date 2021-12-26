package com.experimental.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Implement routines by help in the global functions the application in management the files
 * @author Wilson Garay
 * @version 1.0.0
 * @since 1.0.0
 */
public final class FileUtil {

	/**
	 * Constructor
	 */
	public FileUtil(){

	}

	/**
	 * Store the information in a file
	 * @param fullpath absolute path and name file to store
	 * @param message text to store in the message
	 * @return true if completed operation or false in error case
	 */
	public boolean write(String fullpath,String message){
		try {
			Files.write(Paths.get(fullpath), message.getBytes(StandardCharsets.UTF_8.name()));
			return true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public boolean write(String fullpath, byte[] message){
		try {
			Files.write(Paths.get(fullpath), message);
			return true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		} catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	/**
	 * Read a file defined by parameter and return the content the those file
	 * @param file file to read
	 * @return String with the content of file or blank string
	 */
	public String readFile(String file){
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(file), StandardCharsets.UTF_8.name());
			StringBuilder sb = new StringBuilder();
			while(scanner.hasNext()){
				sb.append(scanner.nextLine());
				sb.append('\n');
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return "";
		} catch(Exception e){
			System.err.println(e.getMessage());
			return "";
		}	
	}



	public String[] searchFiles(String path, String ext){
		String[] files = new String[0];
		File directory = new File(path);
		if(directory.isDirectory()){
			ArrayList<String> list = new ArrayList<String>();
			try{
				for(File file : directory.listFiles()){
					if(file.isFile()){					
						if(ext != null){
							if(file.getCanonicalPath().lastIndexOf(ext) != -1){
								list.add(file.getCanonicalPath());
							}
						}else{
							list.add(file.getCanonicalPath());
						}
					}else {
						list.addAll(Arrays.asList(searchFiles(file.getCanonicalPath(), ext)));
					}
				}		    	
			}catch(NullPointerException e) {
				System.err.println(e.getMessage());
				list.clear();
			}catch(Exception e){
				System.err.println(e.getMessage());
				list.clear();
			}
		
			files = list.toArray(new String[list.size()]);
		}
		return files;
	}


}
