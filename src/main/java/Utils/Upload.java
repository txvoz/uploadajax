/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import spark.Request;
import spark.Response;

/**
 *
 * @author USER
 */
public class Upload {
    private final String UPLOAD_DIR = "src\\main\\resources\\public\\web\\upload";
    //private final String UPLOAD_DIR = "upload";
    private File uploadDir = null;

    public Upload(){
        prepare();
    }
    
    private void prepare() {
        uploadDir = new File(UPLOAD_DIR);
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
    }

    public String uploadFile(Request rq, Response rs) throws IOException {
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

        rq.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        try (InputStream input = rq.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (ServletException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tempFile.getFileName()+"";
    }
    
     private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
