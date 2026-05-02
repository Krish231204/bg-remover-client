package in.vipransh.bgremover.service.impl;

import in.vipransh.bgremover.service.BgRemovalService;
import java.io.BufferedReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

@Service
@Slf4j
public class BgRemovalServiceImpl implements BgRemovalService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    @Value("${app.python.bin:python3}")
    private String pythonBin;
    @Value("${app.python.script:rembg_processor.py}")
    private String pythonScript;
    @Value("${app.python.timeout.seconds:180}")
    private long pythonTimeoutSeconds;

    @Override
    public String removeBackground(MultipartFile image) throws IOException {
        // Save the original image
        String originalFileName = saveImageFile(image);
        String originalFilePath = uploadDir + "/" + originalFileName.substring(originalFileName.lastIndexOf("/") + 1);
        
        // Process with rembg
        String processedFileName = "processed_" + UUID.randomUUID() + ".png";
        String processedFilePath = uploadDir + "/" + processedFileName;
        
        try {
            // Call Python rembg script using configured runtime
            ProcessBuilder pb = new ProcessBuilder(
                pythonBin,
                pythonScript,
                new File(originalFilePath).getAbsolutePath(),
                new File(processedFilePath).getAbsolutePath()
            );
            pb.redirectErrorStream(true);
            
            Process process = pb.start();
            String processOutput = readProcessOutput(process);
            boolean finished = process.waitFor(pythonTimeoutSeconds, TimeUnit.SECONDS);
            if (!finished) {
                process.destroyForcibly();
                throw new IOException("Rembg timed out after " + pythonTimeoutSeconds + "s");
            }
            int exitCode = process.exitValue();
            
            if (exitCode == 0) {
                log.info("Background removed successfully: {}", processedFileName);
                return "/uploads/" + processedFileName;
            } else {
                log.error("Rembg processing failed with exit code {}. Output: {}", exitCode, processOutput);
                throw new IOException("Rembg failed (exit " + exitCode + "): " + processOutput);
            }
        } catch (InterruptedException e) {
            log.error("Rembg processing interrupted", e);
            Thread.currentThread().interrupt();
            throw new IOException("Rembg processing interrupted", e);
        } catch (Exception e) {
            log.error("Error calling rembg processor", e);
            throw new IOException("Error calling rembg processor: " + e.getMessage(), e);
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

    private String readProcessOutput(Process process) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append('\n');
            }
        }
        return output.toString().trim();
    }
}
