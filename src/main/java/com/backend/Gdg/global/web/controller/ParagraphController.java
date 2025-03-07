package com.backend.Gdg.global.web.controller;


import com.backend.Gdg.global.apiPayload.ApiResponse;
import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.security.handler.annotation.AuthUser;
import com.backend.Gdg.global.service.ParagraphService.ParagraphService;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphRequestDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin
@Slf4j
@RequestMapping("/paragraph")
@Tag(name = "필사 API", description = "필사 관련 API입니다.")
public class ParagraphController {

    private final ParagraphService paragraphService;

    @PostMapping("/create")
    @Operation(summary = "필사 추가 API", description = "사용자가 선택한 책에 필사를 추가합니다.")
    public ApiResponse<ParagraphResponseDTO> createParagraph(@RequestBody ParagraphRequestDTO request,
                                                             @Parameter(hidden = true) @AuthUser Member member) {
        try {
            ParagraphResponseDTO response = paragraphService.addParagraph(request, member.getMemberId());
            return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure("401", "인증되지 않은 사용자입니다.", null);
        } catch (Exception e) {
            return ApiResponse.onFailure("500", "서버 내부 오류가 발생했습니다.", null);
        }
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