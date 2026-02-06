package com.uniclock.backend.domain.base.code.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "CODE_GROUP")
@Builder
public class CodeGroup {

    @Id
    @Column(name = "group_code", length = 20)
    private String groupCode;

    @Column(name = "group_name", length = 50, nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "codeGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CodeDetail> details = new ArrayList<>();
}
