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
 * 联系人接口测试
 * Created by zhangbin on 16/8/2.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(name = "parent",locations = {"classpath*:springContext-core.xml","classpath*:springContext-dao.xml"}),
        @ContextConfiguration(name = "child", locations = "classpath*:springContext-mvc.xml")
})
public class ContactControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private String deviceId="my_device_id";
    private String clientType="IOS";
    private String version="1.1.1";
    private String sid="KM4VgBDmOx1lRtwL8nG5+9wcvAny8agueu+63eU/Muc=";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getFigureListTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/figure/list")
                        .header("X-XN-CLIENT", clientType)
                        .header("X-XN-CLIENT-V",version)
                        .header("X-XN-DEVICEID",deviceId)
                        .header("X-XN-SID",sid)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
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
    public void getFigureProListTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));
        paramMap.put("figureId", "1");

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/figure/pro/list")
                        .header("X-XN-CLIENT", clientType)
                        .header("X-XN-CLIENT-V",version)
                        .header("X-XN-DEVICEID",deviceId)
                        .header("X-XN-SID",sid)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
                        .param("figureId", "1")
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
    public void contactTypeListTest() throws Exception {
        long timestamp=System.currentTimeMillis();
        String nonce="123456";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("nonce", nonce);
        paramMap.put("timestamp", String.valueOf(timestamp));

        MockHttpServletRequestBuilder request =
                MockMvcRequestBuilders.get("/contact/type/list/byuser")
                        .header("X-XN-CLIENT", clientType)
                        .header("X-XN-CLIENT-V",version)
                        .header("X-XN-DEVICEID",deviceId)
                        .header("X-XN-SID",sid)
                        .param("nonce", nonce)
                        .param("timestamp", String.valueOf(timestamp))
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