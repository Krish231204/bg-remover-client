package in.vipransh.bgremover.controller;

import in.vipransh.bgremover.entity.ProcessedImage;
import in.vipransh.bgremover.service.ImageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = {"https://*.up.railway.app", "https://*.vercel.app", "http://localhost:*", "http://127.0.0.1:*"})
public class ImageHistoryController {
    
    private final ImageHistoryService imageHistoryService;
    
    @GetMapping("/history/{clerkUserId}")
    public ResponseEntity<List<ProcessedImage>> getUserImages(@PathVariable String clerkUserId) {
        List<ProcessedImage> images = imageHistoryService.getUserImages(clerkUserId);
        return ResponseEntity.ok(images);
    }
    
    @DeleteMapping("/{imageId}/{clerkUserId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long imageId,
            @PathVariable String clerkUserId) {
        imageHistoryService.deleteImage(clerkUserId, imageId);
        return ResponseEntity.ok().build();
    }
}
