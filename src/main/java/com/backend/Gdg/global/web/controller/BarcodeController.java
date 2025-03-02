package com.backend.Gdg.global.web.controller;

import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
import com.backend.Gdg.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/barcode")
@Tag(name = "바코드 API", description = "바코드 관련 API입니다.")
public class BarcodeController {

    @GetMapping("/book/{ISBN}")
    @Operation(summary = "책 정보 조회 API", description = "바코드를 이용하여 책 정보를 조회하는 API")
    public ApiResponse<?> getBookByBarcode(@PathVariable String ISBN) {
        return ApiResponse.onSuccess(SuccessStatus.BARCODE_OK, null);
    }
}