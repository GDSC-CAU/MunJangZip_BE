package com.backend.Gdg.global.azure.blob;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AzureBlobManager {

    private final BlobContainerClient container;

    /**
     * @param keyName 컨테이너 내에 저장할 blob 이름 (예: "books/{uuid}.png")
     * @param file    업로드할 파일
     * @return 실제 접근 가능한 Blob URL
     */
    public String uploadFile(String keyName, MultipartFile file) {
        try {
            BlobClient blob = container.getBlobClient(keyName);
            blob.upload(file.getInputStream(), file.getSize(), true);
            return blob.getBlobUrl();
        } catch (IOException e) {
            log.error("AzureBlobManager.uploadFile error", e);
            throw new RuntimeException("파일 업로드 실패", e);
        }
    }

    /**
     * @param keyName 삭제할 blob 이름 (컨테이너 경로 포함)
     */
    public void deleteFile(String keyName) {
        try {
            BlobClient blob = container.getBlobClient(keyName);
            if (blob.exists()) {
                blob.delete();
            }
        } catch (Exception e) {
            log.error("AzureBlobManager.deleteFile error", e);
            throw new RuntimeException("파일 삭제 실패", e);
        }
    }

    /**
     * 예시로 UUID 에 기반한 blob 경로 생성기
     */
    public String generateBlobName(String filesPath, String uuid) {
        // filesPath: application.yml 에서 설정한 예) "books"
        return filesPath + "/" + uuid;
    }
}