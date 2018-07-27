package com.odde.massivemailer.controller;

import com.odde.TestWithDB;
import com.odde.massivemailer.factory.CourseFactory;
import com.odde.massivemailer.model.ContactPerson;
import com.odde.massivemailer.model.Participant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

@RunWith(TestWithDB.class)
public class EnrollParticipantControllerTest {

    private EnrollParticipantController controller;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    CourseFactory dataMother = new CourseFactory();

    @Before
    public void setUpMockService() {
        controller = new EnrollParticipantController();

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void saveParticipantInCourse() throws Throwable {
        request.setParameter("courseId", "123");
        request.setParameter("participants", "tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore");
        controller.doPost(request, response);

        List<Participant> participants = Participant.whereHasCourseId("123");
        ContactPerson contactByEmail = ContactPerson.getContactByEmail("tom@example.com");

        assertEquals("course_detail.jsp?id=123&errors=", response.getRedirectedUrl());
        assertEquals(1, participants.size());
        assertEquals("tom@example.com", contactByEmail.getEmail());
    }

    @Test
    public void saveParticipantsInCourse() throws Throwable {
        request.setParameter("courseId", "123");
        String inputTsvLines = Stream.of(
                "tom@example.com\tTom\tSmith\tCS\tSingapore\tSingapore",
                "carry@example.com\tCarry\tTrek\tCS\tSingapore\tSingapore"
        ).collect(Collectors.joining("\n"));
        request.setParameter("participants", inputTsvLines);
        controller.doPost(request, response);

        List<Participant> participants = Participant.whereHasCourseId("123");
        ContactPerson tom = ContactPerson.getContactByEmail("tom@example.com");
        ContactPerson carry = ContactPerson.getContactByEmail("carry@example.com");

        assertEquals("course_detail.jsp?id=123&errors=", response.getRedirectedUrl());
        assertEquals(2, participants.size());
        assertEquals("tom@example.com", tom.getEmail());
        assertEquals("carry@example.com", carry.getEmail());
    }

    @Test
    public void saveParticipantInCourseWithError() throws Throwable {
        String takamiyaEmail = "takemiya@\tKeisuke\tSmith\tCS\tSingapore\tSingapore";
        String odaEmail = "odashota.com\tKeisuke\tSmith\tCS\tSingapore\tSingapore";
        request.setParameter("courseId", "123");
        String testLine = takamiyaEmail + "\n" + odaEmail;
        request.setParameter("participants", testLine);
        controller.doPost(request, response);

        assertEquals("course_detail.jsp?id=123&errors=" + URLEncoder.encode(testLine, "UTF-8"), response.getRedirectedUrl());
    }
}