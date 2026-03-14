package in.vipransh.bgremover.service.impl;

import in.vipransh.bgremover.entity.ProcessedImage;
import in.vipransh.bgremover.repository.ProcessedImageRepository;
import in.vipransh.bgremover.service.ImageHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageHistoryServiceImpl implements ImageHistoryService {
    
    private final ProcessedImageRepository processedImageRepository;
    
    @Override
    public ProcessedImage saveProcessedImage(String clerkUserId, String originalFileName, String processedImagePath) {
        ProcessedImage image = ProcessedImage.builder()
                .clerkUserId(clerkUserId)
                .originalFileName(originalFileName)
                .processedImagePath(processedImagePath)
                .build();
        
        ProcessedImage saved = processedImageRepository.save(image);
        log.info("Saved processed image for user {}: {}", clerkUserId, processedImagePath);
        return saved;
    }
    
    @Override
    public List<ProcessedImage> getUserImages(String clerkUserId) {
        return processedImageRepository.findByClerkUserIdOrderByCreatedAtDesc(clerkUserId);
    }
    
    @Override
    public void deleteImage(String clerkUserId, Long imageId) {
        processedImageRepository.deleteById(imageId);
        log.info("Deleted image {} for user {}", imageId, clerkUserId);
    }
}
