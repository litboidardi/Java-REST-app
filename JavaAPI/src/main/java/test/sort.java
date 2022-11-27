package test;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
public class sort {
	@Path("sort")
	@GET 
	@Produces(MediaType.TEXT_HTML) 
	public static void main(@QueryParam("dir") String dirName) {
		String dirPath = new String(System.getProperty("user.home")+"/Desktop/"+dirName);
		
		
        File folder = new File(dirPath);
       
        
        if(folder.isDirectory())
        {
            File[] fileList = folder.listFiles();
           
           // Arrays.sort(fileList);

            System.out.println("\nTotal number of items present in the directory: " + fileList.length );


            // Lists only files since we have applied file filter
           

            // Creating a filter to return only files.
            FileFilter fileFilter = new FileFilter()
            {
                @Override
                public boolean accept(File file) {
                    return !file.isDirectory();
                }
            };

            fileList = folder.listFiles(fileFilter);

            // Sort files by descending size 
            Comparator<File> sizeComparator = Comparator.comparing(File::length);
            Arrays.sort(fileList, sizeComparator.reversed());
            //Arrays.sort(fileList, Comparator.comparing(File::length)); 
            
            //Prints the files in file size descending order
            for(File file:fileList)
            {
            	long bytes = file.length();
                System.out.println(file.getName() + " " + bytes + " B");
            }

        }      
    }
}