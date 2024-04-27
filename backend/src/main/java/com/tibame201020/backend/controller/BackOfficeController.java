package com.tibame201020.backend.controller;

import com.tibame201020.backend.constant.SystemProps;
import com.tibame201020.backend.dto.CustomUserDTO;
import com.tibame201020.backend.model.RequestRecord;
import com.tibame201020.backend.service.RequestRecordService;
import com.tibame201020.backend.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(SystemProps.API_PREFIX + "/backoffice")
public class BackOfficeController {
    private final UserDetailService userDetailService;
    private final RequestRecordService requestRecordService;

    @RequestMapping("/test")
    public String test() {
        return "office test";
    }

    @RequestMapping("/fetchAllUser")
    public List<CustomUserDTO> fetchAllUser() {
        return userDetailService.fetchAllUser();
    }

    @RequestMapping("/fetchAllRecords")
    public List<RequestRecord> fetchAllRecord() {
        return requestRecordService.fetchRequestRecords();
    }

}
