package com.tibame201020.backend.service;

import com.tibame201020.backend.model.RequestRecord;

import java.util.List;

public interface RequestRecordService {

    /**
     * create request record
     */
    RequestRecord loggingRequestRecord(RequestRecord requestRecord);

    /**
     * get RequestRecord list
     */
    List<RequestRecord> fetchRequestRecords();
}
