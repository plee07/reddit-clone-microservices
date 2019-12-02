//package com.example.apigateway.security;
//
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(MockitoJUnitRunner.class)
//@ContextConfiguration
//@WebAppConfiguration
//public class SecurityConfigTest extends AbstractTestNGSpringContextTests {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mockMvc;
//    @Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .build();
//    }
//
//    @Test
//    public void No_AuthRequired() throws Exception {
//
//        mockMvc.perform(get("/posts/list"))
//                .andExpect(status().isOk());
//    }
//
//
//}
