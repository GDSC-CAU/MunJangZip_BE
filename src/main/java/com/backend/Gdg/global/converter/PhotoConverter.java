package com.backend.Gdg.global.converter;

import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.domain.entity.Photo;
import com.backend.Gdg.global.web.dto.PhotoEx;

public class PhotoConverter {
    public static Photo toPhoto(PhotoEx.PhotoDTO photoDTO, Member member) {
        return Photo.builder()

                .build();
    }
}
