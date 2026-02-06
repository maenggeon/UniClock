package com.uniclock.backend.domain.base.code.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "CODE_DETAIL")
@Builder
public class CodeDetail {

    @Id
    @Column(name = "code_id", length = 20)
    private String codeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code", nullable = false)
    private CodeGroup codeGroup;

    @Column(name = "code_name", length = 50, nullable = false)
    private String code_name;

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "is_used")
    @Builder.Default
    private Boolean isUsed = true;

}