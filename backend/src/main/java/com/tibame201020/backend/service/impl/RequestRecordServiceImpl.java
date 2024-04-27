package com.tibame201020.backend.service.impl;

import com.tibame201020.backend.model.RequestRecord;
import com.tibame201020.backend.repo.RequestRecordRepo;
import com.tibame201020.backend.service.RequestRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestRecordServiceImpl implements RequestRecordService {
    private final RequestRecordRepo requestRecordRepo;

    @Override
    public RequestRecord loggingRequestRecord(RequestRecord requestRecord) {
        return requestRecordRepo.saveAndFlush(requestRecord);
    }

    @Override
    public List<RequestRecord> fetchRequestRecords() {
        return requestRecordRepo.findAll(Sort.by("recordId").descending());
    }
}
