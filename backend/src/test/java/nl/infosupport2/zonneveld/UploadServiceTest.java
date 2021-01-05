package nl.infosupport2.zonneveld;

import nl.infosupport2.zonneveld.services.UploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureWebMvc
public class UploadServiceTest {

    private final String IMAGE_URL = "https://images.unsplash.com/photo-1532630571098-79a3d222b00d?ixlib=rb-1.2.1&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=640";

    @Value("${app.medical-media-directory}")
    private String uploadDir;

    @BeforeEach
    void setup() {
        try {
            InputStream stream = new URL(IMAGE_URL).openStream();
            MultipartFile file = new MockMultipartFile("testImage.jpg", stream);

            UploadService.saveFile(uploadDir, "testImage.jpg", file);
        } catch (IOException e) {
            System.err.printf("Error fetching test image. Reason: %s\n", e.getMessage());
            System.exit(1);
        }
    }

    @Test
    void canGetImageUrl() throws FileNotFoundException, MalformedURLException {
        String url = String.format("http://localhost%s", "/dossier/medical-media/download/testImage.jpg");

        assertEquals(url, UploadService.getFileUrl(uploadDir, "testImage.jpg"));
    }

    @Test
    void throwsExceptionWhenFileNotFound() {
        Exception exception = assertThrows(FileNotFoundException.class, () -> UploadService.getFileUrl(uploadDir, "abc.jpg"));
        String expectedMessage = "File abc.jpg not found";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void throwsExceptionWhenFileCouldNotBeDownloaded() {
        Exception exception = assertThrows(FileNotFoundException.class, () -> UploadService.loadFile(uploadDir, "abc.jpg"));
        String expectedMessage = "File abc.jpg was not found in uploads/medical-media";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void canDeleteImageIfItExists() throws IOException {
        assertTrue(UploadService.deleteFile(uploadDir, "testImage.jpg"));
        assertFalse(UploadService.deleteFile(uploadDir, "abc.jpg"));
    }
}
