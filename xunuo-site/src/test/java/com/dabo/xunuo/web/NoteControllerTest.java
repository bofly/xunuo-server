package com.dabo.xunuo.web;

import com.dabo.xunuo.common.Constants;
import com.dabo.xunuo.entity.DataResponse;
import com.dabo.xunuo.util.JsonUtils;
import com.dabo.xunuo.util.SignUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;


/**
 * 小记接口测试
 * Created by zhangbin on 16/8/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent",locations = {"classpath*:springContext-core.xml","classpath*:springContext-dao.xml"}),
        @ContextConfiguration(name = "child", locations = "classpath*:springContext-mvc.xml")
})
public class NoteControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String deviceId="my_device_id";
    private String clientType="IOS";
    private String version="1.1.1";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void publishNoteTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("title", "title");
        paramMap.put("content", "content");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/note/publish")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("title", "title")
                        .param("content", "content")
                        .param("app_key", Constants.APP_KEY)
                .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }

    @Test
    public void getNoteListTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("device_id", deviceId);
        paramMap.put("client_type", clientType+"_"+version);
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("page", "1");
        paramMap.put("limit", "5");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/note/list/byuser")
                        .param("client_type", clientType+"_"+version)
                        .param("device_id", deviceId)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("page", "1")
                        .param("limit", "5")
                        .param("app_key", Constants.APP_KEY)
                        .param("sign", SignUtils.generateSign(paramMap, Constants.APP_KEY, Constants.APP_SECRET));

        MvcResult result = mockMvc.perform(request)
                .andReturn();
        String resultContent=result.getResponse().getContentAsString();
        DataResponse dataResponse = JsonUtils.toObject(resultContent, DataResponse.class);
        System.out.println(JsonUtils.fromObject(dataResponse));
        Assert.assertEquals(dataResponse.getErrorCode(), Constants.DEFAULT_CODE_SUCCESS);
    }
}