package in.vipransh.bgremover.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BgRemovalService {
    String removeBackground(MultipartFile image) throws IOException;
    String saveImage(MultipartFile image) throws IOException;
}
