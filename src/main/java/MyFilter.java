import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = "/ans", servletNames = "AnsServlet", dispatcherTypes = DispatcherType.FORWARD)
public class MyFilter extends HttpFilter {


    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("I am filter");
        HttpSession session = request.getSession(false);
//        System.out.println("Session id " + request.ges);
        ServletContext context = getServletContext();
        if (((HashMap<String, User>) context.getAttribute("usersMap")).containsKey(session.getId())) {
            Map<String, User> users = (Map<String, User>) context.getAttribute("usersMap");
            System.out.println("MyFilter: SessionId " + request.getRequestedSessionId());
            System.out.println("MyFilter: map " + users.size());
            // достает из hashmap'ы на сервере объект, описывающий этого пользователя ???
            session.setAttribute("username", users.get(request.getRequestedSessionId()).getUsername());
            chain.doFilter(request, response);
        } else {
            System.out.println("Redirect...");
            HttpServletResponse httpResponse = response;
            httpResponse.sendRedirect("/index.jsp");
        }
    }

    public void destroy() {
    }

}
