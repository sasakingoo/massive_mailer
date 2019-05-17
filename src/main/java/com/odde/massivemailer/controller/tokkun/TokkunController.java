package com.odde.massivemailer.controller.tokkun;

import com.odde.massivemailer.controller.AppController;
import com.odde.massivemailer.model.onlinetest.OnlineTest;
import com.odde.massivemailer.model.tokkun.QuestionResponseForTokkun;
import org.javalite.activejdbc.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/tokkun/question")
public class TokkunController extends AppController {

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("selectedOption", new ArrayList(Arrays.asList("a")));
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OnlineTest onlineTest = new OnlineTest(1);
        HttpSession session = req.getSession(true);
        QuestionResponseForTokkun questionResponseForTokkun = new QuestionResponseForTokkun();
        LocalDateTime now = LocalDateTime.now();

        if (questionResponseForTokkun.find("answered_at < ? AND counter = 0", now.minusHours(22).toString()).size() > 0) {
            session.setAttribute("question", onlineTest.getCurrentQuestion());
            resp.sendRedirect ("/tokkun/tokkun_question.jsp");
        } else {
            resp.sendRedirect("/tokkun/tokkun_top.jsp");
        }
    }

}
