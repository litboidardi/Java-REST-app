package test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.File;
import java.io.IOException;

@Path("/")
public class directoryOperations {
	 String response;
	 
	 @Path("createDir")
	@GET
	@Produces(MediaType.TEXT_HTML) 
	 public void createDir(@QueryParam("dir") String dirName) throws IOException { // creates a new directory
		 File directory = new File(System.getProperty("user.home")+"/Desktop/"+dirName);
		 
		 if(dirName==null) {
	        	System.out.println("You forgot to input the name of the directory!");
	     } else
	     {
	       boolean dirCreated = directory.mkdir();
	       System.out.println(directory.getAbsolutePath());
	       if(dirCreated) {
	           System.out.println("Directory " + dirName + " created succesfully!");
	           response = "directory was created succesfully!";
	       }
	       else {
	           System.out.println("Directory was not created succesfully!");
	           response = "directory was not created succesfully!";
	       }
	       /* return "<html>" + "<title>" + "Create directory" + "</title>"
			+"<body><h1>" + response + "</h1></body>"+"</html>";
	 */
	 }
	}
	
	@Path("deleteDir")
	@GET 
	@Produces(MediaType.TEXT_HTML) 
	 public void deleteDir(@QueryParam("dir") String dirName) throws IOException { // deletes an existing directory
		 File directory = new File(System.getProperty("user.home")+"/Desktop/"+dirName);
	        boolean dirRemoved = directory.delete();
	        System.out.println(directory.getAbsolutePath());
	        if(dirRemoved) {
	            System.out.println("Directory " + dirName + " removed succesfully!");
	        	response = "directory was removed succesfully!";
	        }
	        	else {
	            System.out.println("Directory was not removed succesfully!");
	            response = "directory was not removed succesfully!";
	            /* return "<html>" + "<title>" + "Create directory" + "</title>"
			+"<body><h1>" + response + "</h1></body>"+"</html>";
	 */
	 }
	}
	
	
	
}
