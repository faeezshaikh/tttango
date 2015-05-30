package com.faeez.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Util {

	public static void main(String... args) throws IOException {
		/*C:\Users\i334143\Downloads\1500_USA_MULTY\Normal*/
//		File folder = new File("C:\\Users\\i334143\\Downloads\\1500_USA_MULTY\\Normal\\uploaded");
		
		File folder = new File(	"C:\\Users\\FSHAI\\Desktop\\Profs\\Males - Morocco");
	
		File[] listOfFiles = folder.listFiles();
		List<String> fNames = new ArrayList<String>();

		    for (int i = 0; i < listOfFiles.length; i++) {
		      
		      
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName();
			      String[] split = fileName.split("_");
			      String fNameToSearch = split[0];
			      readFile(fNameToSearch);
		      /*  System.out.println("File " + fileName);
		        fNames.add(fileName);*/
		      } /*else if (listOfFiles[i].isDirectory()) {
		        System.out.println("Directory " + fileName);
		      }*/
		    }
		    return;
	}
	
	public static void readFile(String fileNametoSearch) throws IOException {
		// Open the file
		FileInputStream fstream = new FileInputStream("C:\\Users\\FSHAI\\Downloads\\tango\\tttango\\src\\main\\java\\com\\faeez\\service\\Morocco.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
		  // Print the content on the console
			if(strLine.contains(fileNametoSearch)) {
				System.out.println (strLine);
			}
//		  System.out.println (strLine);
		}

		//Close the input stream
		br.close();
	}


}
