package frontend;

import templater.PageGenerator;
import db.AccountManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FrontendFroSession extends HttpServlet {

    private AtomicLong userIdGenerator = new AtomicLong();
    private AccountManager accountManager = new AccountManager();

    public static String getTime() {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("HH.mm.ss");
        return formatter.format(date);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        switch (request.getPathInfo()) {
            case Pages.TIMER_PAGE:
                getTimerPage(request, response);
                break;

            case Pages.REG_PAGE:
                getRegPage(request, response);
                break;

            case Pages.ENTER_PAGE:
                getEnterPage(request, response);
                break;
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getSession().invalidate();
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        switch (request.getPathInfo()) {
            case Pages.REG_PAGE:
                postRegPage(request, response, login, pass);
                break;

            case Pages.ENTER_PAGE:
                postEnterPage(request, response, login, pass);
                break;
        }
    }

    public void getTimerPage(HttpServletRequest request,
                     HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(Pages.MAIN_PEG);
        } else {
            pageVariables.put("refreshPeriod", "1000");
            pageVariables.put("serverTime", getTime());
            pageVariables.put("userId", userId);
            response.getWriter().println(PageGenerator.getPage("timer.tml", pageVariables));
        }
    }

    public void getRegPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("errorMessage","");
        response.getWriter().println(PageGenerator.getPage("registration.tml", pageVariables));
    }

    public void getEnterPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("errorMessage","");
        response.getWriter().println(PageGenerator.getPage("enter.tml", pageVariables));
    }

    public void postEnterPage(HttpServletRequest request,
                              HttpServletResponse response, String login, String pass) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        try {
            if (accountManager.logUser(login, pass) == 0) {
                request.getSession(true).setAttribute("userId", userIdGenerator.getAndIncrement());
                response.sendRedirect(Pages.TIMER_PAGE);
            } else {
                pageVariables.put("errorMessage", "Неправильная пара логин-пароль");
                response.getWriter().println(PageGenerator.getPage("enter.tml", pageVariables));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            pageVariables.put("errorMessage", "Что-то пошло не так");
            response.getWriter().println(PageGenerator.getPage("enter.tml", pageVariables));
        }
    }

    public void postRegPage(HttpServletRequest request,
                            HttpServletResponse response, String login, String pass) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        try {
            if (accountManager.regUser(login, pass) == 0) {
                request.getSession(true).setAttribute("userId", userIdGenerator.getAndIncrement());
                response.sendRedirect(Pages.TIMER_PAGE);
            } else {
                pageVariables.put("errorMessage", "Пользователь "+ login +" уже существует!");
                response.getWriter().println(PageGenerator.getPage("registration.tml", pageVariables));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            pageVariables.put("errorMessage", "Что-то пошло не так");
            response.getWriter().println(PageGenerator.getPage("registration.tml", pageVariables));
        }
    }
}