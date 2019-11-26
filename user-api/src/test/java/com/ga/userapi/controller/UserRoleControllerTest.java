package com.ga.userapi.controller;

import com.ga.userapi.model.User;
import com.ga.userapi.model.UserRole;
import com.ga.userapi.services.UserRoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleControllerTest {

    private MockMvc mockMvc;
    private UserRole ur;
    private User user;
    private List<UserRole> list;

    @InjectMocks
    UserRoleController userRoleController;

    @Mock
    UserRoleService userRoleService;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRoleController).build();
    }

    @Before
    public void initializeUserProfile() {
        list = new ArrayList<>();
        ur = new UserRole();
        user = new User();
        user.setUsername("test");
        ur.setName("ROLE_USER");
        ur.setRoleId(1);
        ur.addUser(user);
        list.add(ur);
    }

    @Test
    public void createRole_UserRole_Success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRoleInJson("ROLE_ADMIN"));

        when(userRoleService.createRole(any())).thenReturn(ur);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getRoleList_List_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/role/list")
                .accept(MediaType.APPLICATION_JSON);

        when(userRoleService.getRoleList()).thenReturn(list);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    private static String createRoleInJson(String name) {
        return "{" +
                "\"name\":\"" + name + "\"" +
                "}";
    }
}
