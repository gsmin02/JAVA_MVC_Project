package idusw.jw202112401.pims.controller;

import idusw.jw202112401.pims.model.Blog;
import idusw.jw202112401.pims.model.Member;
import idusw.jw202112401.pims.repository.BlogDAO;
import idusw.jw202112401.pims.repository.BlogDAOImpl;
import idusw.jw202112401.pims.repository.MemberDAO;
import idusw.jw202112401.pims.repository.MemberDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="blogController", urlPatterns = {"/blogs/post-form.do", "/blogs/post.do", "/blogs/list.do"})
public class BlogController extends HttpServlet {
    private String message;

    public void init() { }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf('/') + 1);

        MemberDAO memberdao = new MemberDAOImpl();
        Member member = null;

        BlogDAO blogdao = new BlogDAOImpl();
        Blog blog = null;

        if(action.equals("post-form.do")) {
            member = new Member();
            member = (Member) session.getAttribute("logined");
            request.setAttribute("loginedName", member.getUsername());
            request.setAttribute("loginedEmail", member.getEmail());

            request.getRequestDispatcher("./blog-post-form.jsp").forward(request, response);
        } else if(action.equals("post.do")) {
            blog = new Blog();
            blog.setUsername(request.getParameter("username"));
            blog.setEmail(request.getParameter("email"));
            blog.setTitle(request.getParameter("title"));
            blog.setContent(request.getParameter("content"));

            if(blogdao.create(blog) > 0) {
                request.setAttribute("work", blog.getUsername());
                request.getRequestDispatcher("../status/message.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/error.jsp").forward(request, response);
            }
        } else if(action.equals("list.do")) {
            List<Blog> blogList = blogdao.readList();
            if(blogList != null) {
                request.setAttribute("blogList", blogList);
                request.getRequestDispatcher("../blogs/blog-list-view.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "블로그 목록 불러오기 실패");
                request.getRequestDispatcher("../status/message.jsp").forward(request, response);
            }

        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    public void destroy() {
    }
}
