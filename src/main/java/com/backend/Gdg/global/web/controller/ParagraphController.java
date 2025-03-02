package com.backend.Gdg.global.web.controller;


import com.backend.Gdg.global.apiPayload.ApiResponse;
import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
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
@RequestMapping("/paragraph")
@Tag(name = "필사 API", description = "필사 관련 API입니다.")
public class ParagraphController {

    @PostMapping("/create")
    @Operation(summary = "필사 추가 API", description = "사용자가 필사한 내용을 저장하는 API")
    public ApiResponse<?> createParagraph() {
        return ApiResponse.onSuccess(SuccessStatus.PARAGRAPH_OK, null);
    }

    @PatchMapping("/update/{paragraph_id}")
    @Operation(summary = "필사 수정 API", description = "사용자가 필사한 내용을 수정하는 API")
    public ApiResponse<?> updateParagraph(@PathVariable Long paragraph_id) {
        return ApiResponse.onSuccess(SuccessStatus.PARAGRAPH_OK, null);
    }

    @DeleteMapping("/delete/{paragraph_id}")
    @Operation(summary = "필사 삭제 API", description = "사용자가 필사한 내용을 삭제하는 API")
    public ApiResponse<?> deleteParagraph(@PathVariable Long paragraph_id) {
        return ApiResponse.onSuccess(SuccessStatus.PARAGRAPH_OK, null);
    }
}