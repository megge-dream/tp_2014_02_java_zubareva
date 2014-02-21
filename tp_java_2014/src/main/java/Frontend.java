import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Frontend extends HttpServlet {

    private AtomicLong userIdGenerator = new AtomicLong();
    private Map<String, String> loginAndPassword = new HashMap<String, String>();

    public Frontend() {
        loginAndPassword.put("megge", "123");
        loginAndPassword.put("root", "root");
    }

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

        if (request.getPathInfo().equals("/timer")) {
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                response.sendRedirect("/index.html");
            }
            pageVariables.put("refreshPeriod", "1000");
            pageVariables.put("serverTime", getTime());
            pageVariables.put("userId", userId);
            response.getWriter().println(PageGenerator.getPage("timer.tml", pageVariables));
            return;
        }
        else {
            response.sendRedirect("/index.html");
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        final String login = request.getParameter("login");
        final String pass = request.getParameter("pass");
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        if (loginAndPassword.containsKey(login) && pass.equals(loginAndPassword.get(login))) {
            HttpSession session = request.getSession();
            if( !session.isNew() ) {
                session.invalidate();
                session = request.getSession();
            }
            Long userId = (Long) userIdGenerator.getAndIncrement();
            session.setAttribute("userId", userId);
            response.sendRedirect("/timer");
        } else {
            response.sendRedirect("/index.html");
        }
    }
}
