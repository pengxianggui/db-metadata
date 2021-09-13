package com.github.md.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * <p> @Date : 2021/9/7 </p>
 * <p> @Project : db-metadata-server-springboot</p>
 *
 * <p> @author konbluesky </p>
 */
@Slf4j
class ParameterHelperTest {

    MockMvc mockMvc;

    @Test
    void getPara() {
        MockHttpServletRequest request = new MockHttpServletRequest("get", "/component/hi-3-41");
        request.addParameter("username","tom");
        ParameterHelper parameterHelper = new ParameterHelper(request);
        log.info("parameterHelper.getPara(): {}",parameterHelper.getPara());
        log.info("parameterHelper.getPara(1): {}",parameterHelper.getPara(1));
        log.info("parameterHelper.getPara('username'): {}",parameterHelper.getPara("username"));
    }

    @Test
    void testGetPara() {
    }
}