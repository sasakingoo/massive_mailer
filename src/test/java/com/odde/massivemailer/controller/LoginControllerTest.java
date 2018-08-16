package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

@RunWith(TestWithDB.class)
public class LoginControllerTest {
    private LoginController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    @Before
    public void setUpMockService() {
        controller = new LoginController();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void redirectLoginPageWhenIncorrectMailAndPassword() throws Exception {
        createUser();

        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "incorrectpass");

        controller.doPost(request, response);
        assertThat(response.getRedirectedUrl(), containsString("login.jsp?status=fail"));
    }

    @Test
    public void redirectCourseListPageWhenCorrectMailAndPassword() throws Exception {
        createUser();

        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "abcd1234");
        controller.doPost(request, response);

        assertThat(response.getRedirectedUrl(), containsString("course_list.jsp"));
    }

    private void createUser() {
        User user = new User("mary@example.com");
        user.setPassword("abcd1234");
        user.saveIt();
    }

    @Test
    public void setCookieWhenLoginSuccess() throws Exception {
        createUser();
        request.setParameter("email", "mary@example.com");
        request.setParameter("password", "abcd1234");
        controller.doPost(request, response);

        Cookie cookie = response.getCookie("session_id");
        assertTrue(cookie.getSecure());
        assertTrue(cookie.isHttpOnly());
        assertNotNull(cookie);
        assertEquals("mary@example.com", cookie.getValue());
    }
}
