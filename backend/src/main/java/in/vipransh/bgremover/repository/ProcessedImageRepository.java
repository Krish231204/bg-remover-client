package in.vipransh.bgremover.repository;

import in.vipransh.bgremover.entity.ProcessedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProcessedImageRepository extends JpaRepository<ProcessedImage, Long> {
    List<ProcessedImage> findByClerkUserIdOrderByCreatedAtDesc(String clerkUserId);
}
