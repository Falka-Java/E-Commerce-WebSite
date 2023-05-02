package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "main-servlet", urlPatterns = "")
public class MainServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String page = request.getParameter("page");
            if(page == null)
                page = "default";
            switch(page) {
                case "default":
                    getHomePage(request,response);
                    break;

                default:
                    get404Page(request,response);

            }
        }catch(Exception ex) {
            get400Page(request, response);
        }
    }

    private void getHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- Homepage");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }



    private void get404Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- 404 Page not found");
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/system-pages/404.jsp");
        dispatcher.forward(request, response);
    }
    private void get400Page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title", "- 400 Bad request");
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/system-pages/400.jsp");
        dispatcher.forward(request, response);
    }



}
