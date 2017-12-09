package com.GemCrmTickets.uploadFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller

public class UploadController {

	
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C:/Users/Mostafa ACHRAF/workspace-old/GemCrmTickets/src/main/resources/static/images/"; 

   
    
    
    
    
 
	@PostMapping("/upload/file") // //new annotation since 4.3
  
    @ResponseBody
    
    public String singleFileUpload(@RequestParam("file") MultipartFile file) {
    	
        if (file.isEmpty()) {
        	
            //redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            
        
            return "please select a file to upload !";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            
            Files.write(path, bytes);

            //redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + image.getOriginalFilename() + "'");

        } catch (IOException e) {
        	
            e.printStackTrace();
            
        }

        return file.getOriginalFilename();
        
    }


	
	
    
    
    
}
