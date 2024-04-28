package com.tibame201020.backend.service;

import com.tibame201020.backend.BasicMockitoExtensionTest;
import com.tibame201020.backend.model.RequestRecord;
import com.tibame201020.backend.repo.RequestRecordRepo;
import com.tibame201020.backend.service.impl.RequestRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.when;

class RequestRecordServiceTest implements BasicMockitoExtensionTest {
    @InjectMocks
    private RequestRecordServiceImpl requestRecordService;
    @Mock
    private RequestRecordRepo requestRecordRepo;

    @BeforeEach
    public void init() {
        when(requestRecordRepo.saveAndFlush(Mockito.any())).then(returnsFirstArg());
    }

    @Test
    void loggingRequestRecord() {
        RequestRecord expect = RequestRecord.builder().build();

        RequestRecord result = requestRecordService.loggingRequestRecord(
                RequestRecord.builder().build()
        );

        assertThat(result)
                .isNotNull()
                .isEqualTo(expect);
    }
}