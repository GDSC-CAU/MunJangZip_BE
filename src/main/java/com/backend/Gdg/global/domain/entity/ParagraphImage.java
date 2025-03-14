package com.backend.Gdg.global.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ParagraphImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Long imageId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paragraph_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "FK_paragraph_image", foreignKeyDefinition = "FOREIGN KEY (paragraph_id) REFERENCES paragraph(paragraph_id) ON DELETE CASCADE"))
    private Paragraph paragraph;

    @Column(length = 255, nullable = false)
    private String imageUrl;
}