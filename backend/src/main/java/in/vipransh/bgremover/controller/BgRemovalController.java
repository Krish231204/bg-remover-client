package in.vipransh.bgremover.controller;

import in.vipransh.bgremover.response.RemoveBgResponse;
import in.vipransh.bgremover.service.BgRemovalService;
import in.vipransh.bgremover.service.ImageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/bgremover")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class BgRemovalController {

    private final BgRemovalService bgRemovalService;
    private final ImageHistoryService imageHistoryService;

    @PostMapping(value = "/remove", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RemoveBgResponse removeBg(
            @RequestParam("image") MultipartFile image,
            @RequestParam(value = "userId", required = false) String clerkUserId) {
        try {
            if (image.isEmpty()) {
                return RemoveBgResponse.builder()
                        .success(false)
                        .data("Image file is empty")
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .build();
            }

            // Call service to process image
            String processedImageUrl = bgRemovalService.removeBackground(image);

            // Save to image history if user is logged in
            if (clerkUserId != null && !clerkUserId.isEmpty()) {
                imageHistoryService.saveProcessedImage(
                    clerkUserId,
                    image.getOriginalFilename(),
                    processedImageUrl
                );
            }

            return RemoveBgResponse.builder()
                    .success(true)
                    .data(processedImageUrl)
                    .statusCode(HttpStatus.OK)
                    .build();

        } catch (IOException e) {
            return RemoveBgResponse.builder()
                    .success(false)
                    .data("Error processing image: " + e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        } catch (Exception e) {
            return RemoveBgResponse.builder()
                    .success(false)
                    .data("Unexpected error: " + e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RemoveBgResponse uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            if (image.isEmpty()) {
                return RemoveBgResponse.builder()
                        .success(false)
                        .data("Image file is empty")
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .build();
            }

            // Save image temporarily and return file path
            String imagePath = bgRemovalService.saveImage(image);

            return RemoveBgResponse.builder()
                    .success(true)
                    .data(imagePath)
                    .statusCode(HttpStatus.OK)
                    .build();

        } catch (IOException e) {
            return RemoveBgResponse.builder()
                    .success(false)
                    .data("Error uploading image: " + e.getMessage())
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
