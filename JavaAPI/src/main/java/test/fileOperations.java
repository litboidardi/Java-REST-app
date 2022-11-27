package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class fileOperations {
	String response;
	 
	@Path("createFile")
	@GET 
	@Produces(MediaType.TEXT_HTML) 
	 public void createFile(@QueryParam("dir") String dirName, @QueryParam("file") String fileName) throws IOException { // creates a new file
	        File file = new File(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
	        if(fileName==null) {
	        	System.out.println("You forgot to input the name of the file!");
	        } else
	        {
	        boolean fileCreated = file.createNewFile();
	        System.out.println(file.getAbsolutePath());
	        if(fileCreated) {
	            System.out.println("File " + fileName + " created succesfully!");
	            response = "file was created succesfully!";
	        }
	            else {
	            System.out.println("File was not created succesfully!");
	            response = "file was not created succesfully!";
	            }
	            /* return "<html>" + "<title>" + "Create directory" + "</title>"
			+"<body><h1>" + response + "</h1></body>"+"</html>";
	 */
	 }
	}
	
	@Path("deleteFile")
	@GET 
	@Produces(MediaType.TEXT_HTML) 
	 public void deleteFile(@QueryParam("dir") String dirName, @QueryParam("file") String fileName) throws IOException { // deletes an existing file
	        File file = new File(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
	        if(fileName==null) {
	        	System.out.println("You forgot to input the name of the file!");
	        } else
	        {
	        boolean fileDeleted = file.delete();
	        System.out.println(file.getAbsolutePath());
	        if(fileDeleted) {
	            System.out.println("File " + fileName + " removed succesfully!");
	            response = "file " +fileName+ " was removed succesfully!";
	        }
	            else {
	            System.out.println("File was not removed succesfully!");
	            response = "file was not removed succesfully!";
	            }
	            /* return "<html>" + "<title>" + "Create directory" + "</title>"
			+"<body><h1>" + response + "</h1></body>"+"</html>";
	 */
	 }
	}
	
	@Path("copyFile")
	@GET 
	@Produces(MediaType.TEXT_HTML) 
	 public void copyFile(@QueryParam("dir") String dirName, @QueryParam("file") String fileName, @QueryParam("dirto") String dirNameTo) throws IOException { // copies a file from directory to another directory
		java.nio.file.Path sourceDir = Paths.get(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
		java.nio.file.Path targetDir = Paths.get(System.getProperty("user.home")+"/Desktop/"+dirNameTo+"/"+fileName);
		
		if(fileName==null) {
        	System.out.println("you forgot to input the name of the file!");
        } else
        {
        	
		boolean fileCopied = Files.copy(sourceDir, targetDir) != null;
		if(fileCopied) {
            System.out.println("File " + fileName + " copied from: "+sourceDir);
        	System.out.println("File " + fileName + " copied to: "+targetDir);
            response = "file was copied succesfully!";
        }
            else {
            System.out.println("File was not copied succesfully!");
            response = "file was not copied succesfully!";
            }	
        }
	}
	
	@Path("getContent")
	@GET 
	@Produces(MediaType.TEXT_HTML) 
	 public void getContent(@QueryParam("dir") String dirName, @QueryParam("file") String fileName) throws IOException { // prints the content of a file
		java.nio.file.Path sourceDir = Paths.get(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
		System.out.println("Content of the file called "+fileName+ ": " + Files.readAllLines(sourceDir));	
	}
	
	@Path("moveFile")
	@GET 
	@Produces(MediaType.TEXT_HTML) 
	 public void moveFile(@QueryParam("dir") String dirName, @QueryParam("file") String fileName, @QueryParam("dirto") String dirNameTo) throws IOException { // moves a file from directory to another directory
		java.nio.file.Path sourceDir = Paths.get(System.getProperty("user.home")+"/Desktop/"+dirName+"/"+fileName);
		java.nio.file.Path targetDir = Paths.get(System.getProperty("user.home")+"/Desktop/"+dirNameTo+"/"+fileName);
		
		if(fileName==null) {
        	System.out.println("You forgot to input the name of the file!");
        } else
        { 	
		boolean fileCopied = Files.move(sourceDir, targetDir) != null;
			if(fileCopied) {
				System.out.println("File " + fileName + " moved from: "+sourceDir);
				System.out.println("File " + fileName + " moved to: "+targetDir);
				response = "file was moved succesfully!";
        }
            else {
            	System.out.println("File was not moved succesfully!");
            	response = "file was not copied succesfully!";
            }
        }
	}
	
	@Path("find")
	@GET 
	@Produces(MediaType.TEXT_HTML) 
	 public void findPattern(@QueryParam("dir") String dirName, @QueryParam("pattern") String pattern) throws IOException { // finds a pattern in file	 
		 try (Stream<java.nio.file.Path> filePathStream=Files.walk(Paths.get(System.getProperty("user.home")+"/Desktop/"+dirName))) {
			 filePathStream.forEach(filePath -> {
				 if (Files.isRegularFile(filePath)) {
					 List<String> list = new ArrayList<>();
					 	try (BufferedReader br = Files.newBufferedReader(filePath)) { //br returns as stream and convert it into a List
					 		list = br.lines().collect(Collectors.toList());
			    			if(list.contains(pattern)) {
			    				try {
			    					try (Scanner scanner = new Scanner(filePath)) { // read the file line by line...
			    						int lineNum = 0;
			    						while (scanner.hasNextLine()) {
			    							lineNum++;
			    							String line = scanner.nextLine();
								     
			    							if(line.contains(pattern)) // if the pattern occurs in the file
			    								System.out.println("Pattern: "+pattern+" - found on line " +lineNum+" in file: "+filePath );  
								 }
							}
			       		 } catch(Exception e) { 
			       		     //handle this
			       		 }
			        	}
			        	} catch (IOException e) {
			    			e.printStackTrace();
			    		}  
			        }
			    });
		 }
	}	
}

