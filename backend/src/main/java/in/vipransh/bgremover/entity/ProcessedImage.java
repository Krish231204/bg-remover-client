package in.vipransh.bgremover.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "processed_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String clerkUserId;
    
    @Column(nullable = false)
    private String originalFileName;
    
    @Column(nullable = false)
    private String processedImagePath;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column
    private String fileSize; // in bytes
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
