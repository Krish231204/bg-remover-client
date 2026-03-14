package in.vipransh.bgremover.service.impl;

import in.vipransh.bgremover.service.BgRemovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class BgRemovalServiceImpl implements BgRemovalService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public String removeBackground(MultipartFile image) throws IOException {
        // Save the original image
        String originalFileName = saveImageFile(image);
        String originalFilePath = uploadDir + "/" + originalFileName.substring(originalFileName.lastIndexOf("/") + 1);
        
        // Process with rembg
        String processedFileName = "processed_" + UUID.randomUUID() + ".png";
        String processedFilePath = uploadDir + "/" + processedFileName;
        
        try {
            // Call Python rembg script using virtual environment
            ProcessBuilder pb = new ProcessBuilder(
                "/Users/krish/Downloads/bg-remover-client/.venv/bin/python3",
                "rembg_processor.py",
                new File(originalFilePath).getAbsolutePath(),
                new File(processedFilePath).getAbsolutePath()
            );
            pb.directory(new File("/Users/krish/Downloads/bg-remover-client/backend"));
            pb.redirectErrorStream(true);
            
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                log.info("Background removed successfully: {}", processedFileName);
                return "/uploads/" + processedFileName;
            } else {
                log.error("Rembg processing failed with exit code: {}", exitCode);
                // Return original image if processing fails
                return originalFileName;
            }
        } catch (InterruptedException e) {
            log.error("Rembg processing interrupted", e);
            Thread.currentThread().interrupt();
            return originalFileName;
        } catch (Exception e) {
            log.error("Error calling rembg processor", e);
            return originalFileName;
        }
    }

    @Override
    public String saveImage(MultipartFile image) throws IOException {
        return saveImageFile(image);
    }

    private String saveImageFile(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename != null ? 
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(uniqueFilename);
        Files.write(filePath, file.getBytes());

        log.info("File saved successfully: {}", uniqueFilename);
        
        return "/uploads/" + uniqueFilename;
    }
}
