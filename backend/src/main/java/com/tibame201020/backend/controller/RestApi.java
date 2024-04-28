package com.tibame201020.backend.controller;

import com.tibame201020.backend.constant.SystemProps;
import com.tibame201020.backend.dto.CustomException;
import com.tibame201020.backend.util.SecurityContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SystemProps.API_PREFIX)
@Slf4j
public class RestApi {

    /**
     * open resource, anyone can access
     */
    @RequestMapping("/publishApi")
    public String test() {
        return "publish resource";
    }

    /**
     * need be authentication to access
     */
    @RequestMapping("/needAuthApi")
    public String needHide() {
        log.info("get user = {}", SecurityContextUtil.getUserInfo());
        return "need auth api";
    }

    /**
     * need publisher role to access
     */
    @RequestMapping("/publisher/role")
    public String needPublisherRole() {
        log.info("get user = {}", SecurityContextUtil.getUserInfo());
        return "needPublisherRole";
    }

    /**
     * need writer role to access
     */
    @RequestMapping("/writer/role")
    public String needWriterRole() {
        log.info("get user = {}", SecurityContextUtil.getUserInfo());
        return "needWriterRole";
    }

    /**
     * need reader role to access
     */
    @RequestMapping("/reader/role")
    public String needReaderRole() {
        log.info("get user = {}", SecurityContextUtil.getUserInfo());
        return "needReaderRole";
    }

    /**
     * need writer/publisher role to access
     */
    @RequestMapping("/writerPublisher/role")
    public String needWriterPublisherRole() {
        log.info("get user = {}", SecurityContextUtil.getUserInfo());

        return "needWriterRole or needPublisherRole";
    }

    /**
     * mock backend throw exception
     */
    @RequestMapping("/mockException")
    public String mockException() {
        throw CustomException.builder()
                .code(512)
                .message("mock exception happened")
                .build();
    }
}
