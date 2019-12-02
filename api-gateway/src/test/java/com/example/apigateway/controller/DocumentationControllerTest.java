package com.example.apigateway.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import springfox.documentation.swagger.web.SwaggerResource;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DocumentationControllerTest {
    @InjectMocks
    private DocumentationController doc;

    @Test
    public void doc_success(){
        List<SwaggerResource> test = doc.get();
        assertEquals("user-api", test.get(0).getName());
        assertEquals("/users/v2/api-docs", test.get(0).getUrl());
    }
}
