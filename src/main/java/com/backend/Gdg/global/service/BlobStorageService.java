package com.backend.Gdg.global.service;

import com.backend.Gdg.global.azure.blob.AzureBlobManager;
import com.backend.Gdg.global.domain.entity.Uuid;
import com.backend.Gdg.global.repository.UuidRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class BlobStorageService {

    private final UuidRepository uuidRepository;
    private final AzureBlobManager blobManager;

    /** application.yml 에 설정된 경로 (예: "books") */
    @Value("${azure.storage.path.file}")
    private String filesPath;

    public String newImage(MultipartFile file) {
        // UUID 발급
        String uuid = UUID.randomUUID().toString();
        uuidRepository.save(Uuid.builder().uuid(uuid).build());

        // 실제 blob 이름 생성
        String blobName = blobManager.generateBlobName(filesPath, uuid);

        // 업로드
        String url = blobManager.uploadFile(blobName, file);
        return url;
    }

    public String deleteImage(String uuid) {
        String blobName = filesPath + "/" + uuid;
        blobManager.deleteFile(blobName);
        return "deleted " + uuid;
    }
}