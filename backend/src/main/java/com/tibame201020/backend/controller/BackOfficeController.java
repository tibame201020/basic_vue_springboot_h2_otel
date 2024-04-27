package com.tibame201020.backend.controller;

import com.tibame201020.backend.constant.SystemProps;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(SystemProps.API_PREFIX + "/backoffice")
public class BackOfficeController {

    @RequestMapping("/test")
    public String test() {
        return "office test";
    }
}
