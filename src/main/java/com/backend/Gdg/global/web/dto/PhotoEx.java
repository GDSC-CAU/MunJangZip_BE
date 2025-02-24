package com.backend.Gdg.global.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class PhotoEx {

    @Getter
    @RequiredArgsConstructor
    @Setter
    public static class PhotoDTO {
        MultipartFile Photo;
    }
}
