package com.ga.userapi.services;

import com.ga.userapi.model.User;
import com.ga.userapi.model.UserRole;
import com.ga.userapi.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleServiceTest {

    @InjectMocks
    UserRoleServiceImpl roleService;

    @Mock
    UserRoleRepository roleRepository;

    private User user;
    private UserRole ur;
    private List<UserRole> list;
    @Before
    public void initData() {
        list = new ArrayList<>();
        user = new User();
        user.setUserId(1L);
        user.setPassword("123456");
        user.setUsername("batman");
        user.setEmail("test@test.com");

        ur = new UserRole();
        ur.setName("ROLE_USER");
        ur.setRoleId(1);
        ur.addUser(user);
        user.addRole(ur);

        list.add(ur);
    }

    @Test
    public void createRole_UserRole_SUCCESS(){
        when(roleRepository.save(any())).thenReturn(ur);
        UserRole dummyRole = roleService.createRole(ur);
        assertEquals(dummyRole.getName(), ur.getName());
    }

    @Test
    public void getRoleList_List_SUCCESS(){
        when(roleRepository.findAll()).thenReturn(list);
        Iterable<UserRole> dummyList = roleService.getRoleList();
        assertNotNull(dummyList);
    }

//    @Override
//    public UserRole createRole(UserRole role) {
//        return roleRepository.save(role);
//    }
//
//    @Override
//    public Iterable<UserRole> getRoleList() {
//        return roleRepository.findAll();
//    }

}
