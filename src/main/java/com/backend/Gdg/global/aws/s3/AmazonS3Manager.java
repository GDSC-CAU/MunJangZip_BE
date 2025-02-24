package com.backend.Gdg.global.aws.s3;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.backend.Gdg.global.config.S3Config;
import com.backend.Gdg.global.domain.entity.Uuid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

    private final AmazonS3 amazonS3;
    private final S3Config s3Config;

    public String uploadFile(String keyName, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        metadata.setContentDisposition("inline");

        try {
            amazonS3.putObject(new PutObjectRequest(s3Config.getBucket(), keyName, file.getInputStream(), metadata));
        } catch (IOException e) {
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(s3Config.getBucket(), keyName).toString();
    }

//    public void deleteFile(String fileUrl) {
//        try {
//            String keyName = fileUrl.replace(amazonS3.getUrl(s3Config.getBucket(), "").toString(), "");
//            log.info("Attempting to delete file: bucket={}, keyName={}", s3Config.getBucket(), keyName);
//
//            amazonS3.deleteObject(s3Config.getBucket(), keyName);
//        } catch (Exception e) {
//            log.error("Error deleting file from S3: {}", e.getMessage());
//        }
//    }
//public void deleteFile(String fileUrl) {
//    try {
//        // S3 Key 추출
//        String bucketUrl = amazonS3.getUrl(s3Config.getBucket(), "").toString();
//        if (!fileUrl.startsWith(bucketUrl)) {
//            throw new IllegalArgumentException("Invalid file URL: " + fileUrl);
//        }
//        String keyName = fileUrl.substring(bucketUrl.length());
//        log.info("Attempting to delete file: bucket={}, keyName={}", s3Config.getBucket(), keyName);
//
//        // S3 파일 삭제 요청
//        amazonS3.deleteObject(s3Config.getBucket(), keyName);
//        log.info("Successfully deleted file: bucket={}, keyName={}", s3Config.getBucket(), keyName);
//    } catch (AmazonServiceException e) {
//        log.error("S3 Service Exception: {}", e.getErrorMessage());
//        log.error("HTTP Status Code: {}", e.getStatusCode());
//        log.error("AWS Error Code: {}", e.getErrorCode());
//        log.error("Request ID: {}", e.getRequestId());
//        throw e;
//    } catch (Exception e) {
//        log.error("Error deleting file from S3: {}", e.getMessage());
//        throw e;
//    }
//}

    public void deleteFile(String fileUrl) {
        String[] segments = fileUrl.split("/");
        String keyName = s3Config.getFilesPath() + '/' + segments[segments.length - 1];

        try {
            amazonS3.deleteObject(s3Config.getBucket(), keyName);
        } catch (Exception e) {
            log.error("error at AmazonS3Manager deleteFile : {}", (Object) e.getStackTrace());
        }
    }


    public String generatePostName(Uuid uuid) {
        return s3Config.getFilesPath() + '/' + uuid.getUuid();
    }
}
