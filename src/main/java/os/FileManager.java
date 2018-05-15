package os;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class FileManager {

    private static final String CUSTOMER_PATH_IMAGES = "C:\\Users\\Public\\Pictures";

    public static String saveImage(Long customerId, MultipartFile file) {
        String path = null;
        try {
            BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));
            path = CUSTOMER_PATH_IMAGES + "\\" +String.valueOf(customerId)+"_"+file.getOriginalFilename();
            File destination = new File(path);
            ImageIO.write(src, "png", destination);
        } catch (IOException ignored) {
        }
        return path;
    }
}
