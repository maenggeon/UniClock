package com.uniclock.backend.domain.base.code.repository;

import com.uniclock.backend.domain.base.code.entity.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeDetailRepository extends JpaRepository<CodeDetail, String> {

    /**
     * 특정 그룹의 활성 코드 목록 (기본 조회)
     */
    /*
    @Query("""
           select cd
           from CodeDetail cd
           where cd.codeGroup.groupCode = :groupCode
             and cd.isUsed = true
           order by cd.sortOrder asc
           """)
    List<CodeDetail> findActiveByGroupCode(@Param("groupCode") String groupCode);
    */
}
