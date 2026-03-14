package in.vipransh.bgremover.service;

import in.vipransh.bgremover.entity.ProcessedImage;
import java.util.List;

public interface ImageHistoryService {
    ProcessedImage saveProcessedImage(String clerkUserId, String originalFileName, String processedImagePath);
    List<ProcessedImage> getUserImages(String clerkUserId);
    void deleteImage(String clerkUserId, Long imageId);
}
