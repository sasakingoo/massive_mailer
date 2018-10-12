package com.odde.massivemailer.controller;

import com.odde.massivemailer.model.Quiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/advice")
public class AdviceController extends AppController{

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Quiz quiz = (Quiz)session.getAttribute("quiz");
        String url = "end_of_test.jsp";
        if(quiz.hasNextQuestion()){
            url = "question.jsp";
        }
        response.sendRedirect(url);
    }

}
