package init;

import Utils.Upload;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.servlet.MultipartConfigElement;
import spark.Spark;
import static spark.Spark.post;

/**
 *
 * @author USER
 */
public class Main {

    public static void main(String[] args) {
        //Ruta de archivos estaticos
        Spark.staticFiles.location("/public");
        //*************
        Spark.port(88);
        //*************
        Spark.init();
        //Publicacion de Apis/Servicios
        post("/upload", (req, res) -> {
            Upload up = new Upload();
            up.uploadFile(req, res);
            return "Alert";
        });
    }

}
