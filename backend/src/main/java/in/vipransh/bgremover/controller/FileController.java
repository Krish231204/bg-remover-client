package in.vipransh.bgremover.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/uploads")
@CrossOrigin(originPatterns = {"https://*.up.railway.app", "https://*.vercel.app", "http://localhost:*", "http://127.0.0.1:*"})
public class FileController {

    private static final String UPLOADS_DIR = "/Users/krish/Downloads/bg-remover-client/backend/uploads/";

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            // Prevent directory traversal attacks
            if (filename.contains("..")) {
                return ResponseEntity.badRequest().build();
            }

            Path filePath = Paths.get(UPLOADS_DIR).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
