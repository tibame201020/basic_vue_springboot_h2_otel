package com.tibame201020.backend.repo;

import com.tibame201020.backend.model.RequestRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRecordRepo extends JpaRepository<RequestRecord, Long> {
}
