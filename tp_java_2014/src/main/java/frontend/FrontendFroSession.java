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
        Map<String, Object> pageVariables = new HashMap<>();
        if (request.getPathInfo().equals(Pages.TIMER_PAGE)) {
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

        if (request.getPathInfo().equals(Pages.REG_PAGE)) {
            response.getWriter().println(PageGenerator.getPage("registration.tml", pageVariables));
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getSession().invalidate();
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        if (request.getPathInfo().equals(Pages.REG_PAGE)) {
            try {
                accountManager.regUser(login, pass);
                request.getSession(true).setAttribute("userId", userIdGenerator.getAndIncrement());
                response.sendRedirect(Pages.TIMER_PAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect(Pages.REG_PAGE);
            }
        }
        if (request.getPathInfo().equals(Pages.MAIN_PEG)) {
            try {
                accountManager.logUser(login, pass);
                request.getSession(true).setAttribute("userId", userIdGenerator.getAndIncrement());
                response.sendRedirect(Pages.TIMER_PAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect(Pages.MAIN_PEG);
            }
        }
    }
}