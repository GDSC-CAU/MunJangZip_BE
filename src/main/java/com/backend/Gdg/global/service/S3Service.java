package com.backend.Gdg.global.service;

import com.backend.Gdg.global.aws.s3.AmazonS3Manager;
import com.backend.Gdg.global.repository.UuidRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class S3Service {
    private final UuidRepository uuidRepository;
    private final AmazonS3Manager s3Manager;

    public String newImage(MultipartFile file){
        String uuid = UUID.randomUUID().toString();
        //Uuid saveUuid = uuidRepository.save(Uuid.builder()
        //        .uuid(uuid).build());
        s3Manager.uploadFile(uuid, file);
        return "uploaded image";
    }

    public String noImage(String imageUrl){
        s3Manager.deleteFile(imageUrl);
        return "deleted image";
    }
}