package com.tibame201020.backend.repo;

import com.tibame201020.backend.model.RequestRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * AutoConfigureTestDatabase.Replace.NONE => with use real database connection
 */
@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RequestRecordRepoTest {
    private final RequestRecordRepo requestRecordRepo;

    @Autowired
    RequestRecordRepoTest(RequestRecordRepo requestRecordRepo) {
        this.requestRecordRepo = requestRecordRepo;
    }

    @Test
    public void save() {
        RequestRecord data = RequestRecord.builder().build();
        RequestRecord result = requestRecordRepo.save(data);
        RequestRecord expect = RequestRecord.builder().recordId(1L).build();

        assertThat(result)
                .isNotNull()
                .isEqualTo(expect);
    }
}