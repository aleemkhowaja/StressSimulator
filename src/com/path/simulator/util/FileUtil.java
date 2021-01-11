package com.path.simulator.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * @author MohammadAliMezzawi
 *
 */
public class FileUtil {

	/**
	 * @param path
	 * @param extension
	 * @return
	 */
	public static ArrayList<String> getAllFiles(String path, String extension) {
		
		ArrayList<String> fileNames = new ArrayList<String>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for(int i = 0; i < listOfFiles.length; i++){
			String filename = listOfFiles[i].getName();
			if(filename.endsWith(extension)||filename.endsWith(extension))
				fileNames.add(filename);
		}
		
		return fileNames;
	}
	
	
	/**
	 * @param path
	 * @return
	 */
	public static String[] getAllDirectories(String path){
		
		File file = new File(path);
		String[] directories = file.list(new FilenameFilter() {
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		return directories;
	}

}
