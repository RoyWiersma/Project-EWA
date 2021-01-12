package nl.infosupport2.zonneveld.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadService {

    /**
     * Saves a file on the server at the given location. An IOException is thrown
     * when a error occurs.
     *
     * @param dir directory where the file is saved
     * @param fileName the name of the file
     * @param file the data of the file
     * @throws IOException file could not be saved
     */
    public static void saveFile(String dir, String fileName, MultipartFile file) throws IOException {
        Path path = Paths.get(dir);
        if (!Files.exists(path))
            Files.createDirectories(path);

        try (InputStream stream = file.getInputStream()) {
            Path filePath = path.resolve(fileName);
            Files.copy(stream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException(String.format("Could not save file: %s", fileName), e);
        }
    }

    /**
     * Gets the download url for a image
     *
     * @param dir the path of the directory
     * @param fileName name of the file
     * @return a url for the download
     * @throws FileNotFoundException if the file was not found
     */
    public static String getFileUrl(String dir, String fileName) throws FileNotFoundException, MalformedURLException {
        Path path = Paths.get(dir).resolve(fileName);
        Resource resource = new UrlResource(path.toUri());
        if (!resource.exists())
            throw new FileNotFoundException(String.format("File %s not found", fileName));

        return MvcUriComponentsBuilder.fromMethodName(Dossiercontroller.class, "downloadImage", resource.getFilename())
                .build().toString();
    }

    /**
     * Gets the content of the file from the server
     *
     * @param dir the path of the directory
     * @param fileName name of the file
     * @return the content of the file
     * @throws IOException if an I/O error occurs
     */
    public static Resource loadFile(String dir, String fileName) throws IOException {
        Path path = Paths.get(dir).resolve(fileName);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists())
            throw new FileNotFoundException(String.format("File %s was not found in %s", fileName, dir));

        return resource;
    }

    /**
     * Deletes a file from the server
     *
     * @param dir the path of the directory
     * @param fileName name of the file
     * @return {@code true} if the file is deleted, {@code false} if the file was not deleted
     * @throws IOException if an I/O error occurs
     */
    public static boolean deleteFile(String dir, String fileName) throws IOException {
        Path path = Paths.get(dir).resolve(fileName);

        return Files.deleteIfExists(path);
    }
}
