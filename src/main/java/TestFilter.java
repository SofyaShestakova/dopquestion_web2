import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/*@WebFilter(
        filterName="test",
        urlPatterns="/three"
)*/



public class TestFilter extends HttpFilter {
  @Override
  protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

    HttpSession httpSession = req.getSession(false);


    if (httpSession != null) {
      HashMap <String,String> hashmap = new HashMap<>();
      hashmap.put(req.getParameter("user"),"username");
      req.setAttribute("user",hashmap);
      super.doFilter(req, res, chain);
    } else {
      res.sendRedirect("index.jsp");
    }


  }


  


}