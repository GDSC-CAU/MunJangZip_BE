package com.backend.Gdg.global.web.controller;

import com.backend.Gdg.global.service.IsbnService;
import com.backend.Gdg.global.web.dto.IsbnResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class IsbnController {  // 클라이언트 요청을 받아 ISBN 값을 서비스로 전달하고, 결과를 반환

    private final IsbnService isbnService;

    // 예: GET /isbn?isbn=9783161484100
    @GetMapping("/isbn")
    public IsbnResponseDto getIsbnInfo(@RequestParam("isbn") String isbn) {
        return isbnService.fetchIsbnInfo(isbn);
    }
}