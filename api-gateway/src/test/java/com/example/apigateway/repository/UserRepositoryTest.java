package com.example.apigateway.repository;

import com.example.apigateway.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    @Mock
    private JdbcTemplate jdbc;

    @Mock
    private ResultSet rs;

    @Captor
    private ArgumentCaptor<RowMapper<User>> rowMapperCaptor;

    private User user;
    @Before
    public void test()throws SQLException {
        user = new User(1L, "batman", "pass123");
        when(rs.getLong(any())).thenReturn(user.getUserId());
        when(rs.getString(any())).thenReturn(user.getUsername(), user.getPassword());
    }
    @Test
    public void getUserByUserName_SUCCESS() throws SQLException {
        User testUser = userRepository.getUserByUsername("batman");
        verify(jdbc).queryForObject(anyString(), any(), rowMapperCaptor.capture());
        RowMapper<User> rm = rowMapperCaptor.getValue();
        User test = rm.mapRow(rs, 1);
        assertEquals(test.getUsername(), user.getUsername());
        assertEquals(test.getPassword(), user.getPassword());
        assertEquals(test.getUserId(), user.getUserId());
    }
}
