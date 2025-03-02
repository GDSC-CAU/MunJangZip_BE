package com.backend.Gdg.global.service.CategoryService;

import com.backend.Gdg.global.converter.CategoryConverter;
import com.backend.Gdg.global.domain.entity.Category;
import com.backend.Gdg.global.domain.entity.Member;
import com.backend.Gdg.global.repository.CategoryRepository;
import com.backend.Gdg.global.repository.MemberRepository;
import com.backend.Gdg.global.web.dto.Category.CategoryRequestDTO;
import com.backend.Gdg.global.web.dto.Category.CategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public CategoryResponseDTO addCategory(Long memberId, CategoryRequestDTO request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        Category category = CategoryConverter.toCategory(request, member);
        Category savedCategory = categoryRepository.save(category);

        return CategoryConverter.toCategoryResponseDTO(savedCategory);
    }
}