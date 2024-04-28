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

/**
 * back office controller
 */
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

    /**
     * get all custom user with role list
     */
    @RequestMapping("/fetchAllUser")
    public List<CustomUserDTO> fetchAllUser() {
        return userDetailService.fetchAllUser();
    }

    /**
     * get all request-records
     */
    @RequestMapping("/fetchAllRecords")
    public List<RequestRecord> fetchAllRecord() {
        return requestRecordService.fetchRequestRecords();
    }

}
