package com.uniclock.backend.domain.base.code.repository;

import com.uniclock.backend.domain.base.code.entity.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeDetailRepository extends JpaRepository<CodeDetail, String> {
}
