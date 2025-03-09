package com.backend.Gdg.global.web.controller;


import com.backend.Gdg.global.apiPayload.ApiResponse;
import com.backend.Gdg.global.apiPayload.code.status.SuccessStatus;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.security.handler.annotation.AuthUser;
import com.backend.Gdg.global.service.ParagraphService.ParagraphService;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphRequestDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphResponseDTO;
import com.backend.Gdg.global.web.dto.Paragraph.ParagraphUpdateRequestDTO;
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

    // 필사 수정
    @PatchMapping("/update/{paragraph_id}")
    @Operation(summary = "필사 수정 API", description = "필사의 내용을 수정합니다. 수정은 필사를 등록한 사용자만 가능합니다.")
    public ApiResponse<ParagraphResponseDTO> updateParagraph(
            @PathVariable("paragraph_id") Long paragraphId,
            @RequestBody ParagraphUpdateRequestDTO request,
            @Parameter(hidden = true) @AuthUser Member member) {
        try {
            // 토큰에서 주입된 Member 객체를 통해 수정 권한을 확인함
            ParagraphResponseDTO response = paragraphService.updateParagraph(paragraphId, member.getMemberId(), request);
            return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure("401", "인증되지 않거나 수정 권한이 없는 사용자입니다.", null);
        } catch (Exception e) {
            return ApiResponse.onFailure("500", "서버 내부 오류가 발생했습니다.", null);
        }
    }


    // 필사 삭제 API – 오직 필사를 추가한 사용자만 삭제 가능
    @DeleteMapping("/delete/{paragraph_id}")
    @Operation(summary = "필사 삭제 API", description = "필사를 추가한 사용자만 해당 필사를 삭제할 수 있습니다.")
    public ApiResponse<ParagraphResponseDTO> deleteParagraph(
            @PathVariable("paragraph_id") Long paragraphId,
            @Parameter(hidden = true) @AuthUser Member member) {
        try {
            ParagraphResponseDTO response = paragraphService.deleteParagraph(paragraphId, member.getMemberId());
            return ApiResponse.onSuccess(SuccessStatus.BOOK_OK, response);
        } catch (IllegalArgumentException e) {
            return ApiResponse.onFailure("401", e.getMessage(), null);
        } catch (Exception e) {
            return ApiResponse.onFailure("500", "서버 내부 오류가 발생했습니다.", null);
        }
    }
}