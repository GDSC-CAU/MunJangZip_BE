package com.backend.Gdg.global.web.controller;

import com.backend.Gdg.global.service.S3Service;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "S3", description = "이미지 업로드/삭제 테스트용 API")
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final S3Service s3;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public String imageUpload(@RequestPart("ingredientPhoto") MultipartFile ingredientPhoto, @RequestParam(name = "title") String title){
        return s3.newImage(ingredientPhoto);
    }

    @DeleteMapping("/delete")
    public String imageDelete(@RequestParam(name = "imageUrl") String url){
        return s3.noImage(url);
    }
}
