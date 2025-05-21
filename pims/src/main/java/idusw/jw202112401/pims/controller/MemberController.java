package idusw.jw202112401.pims.controller;

import idusw.jw202112401.pims.model.Member;
import idusw.jw202112401.pims.repository.MemberDAO;
import idusw.jw202112401.pims.repository.MemberDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="memberController", urlPatterns =
        {"/members/login-form.do", "/members/post-form.do", "/members/post.do", "/members/login.do",
                "/members/detail.do", "/members/logout.do", "/members/list.do"})
public class MemberController extends HttpServlet {
    private String message;

    public void init() { }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // request.getRequestDispatcher("./member-login-form.jsp").forward(request, response);
        // "../members/member-login-form.jsp" 도 가능

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String action = uri.substring(uri.lastIndexOf('/') + 1);

        MemberDAO dao = new MemberDAOImpl();
        Member member = null;

        if(action.equals("login-form.do")) {
            request.getRequestDispatcher("./member-login-form.jsp").forward(request, response);
        } else if(action.equals("post-form.do")) {
            request.getRequestDispatcher("./member-post-form.jsp").forward(request, response);
        } else if (action.equals("login.do")) {
            member = new Member();
            member.setEmail(request.getParameter("email"));
            member.setPw(request.getParameter("pw"));

            Member retMember = dao.read(member);
            if(retMember != null) {
                session.setAttribute("logined", retMember);
                if(retMember.getAdmin() == 1) {
                    session.setAttribute("admin", retMember.getAdmin());
                }
                request.getRequestDispatcher("../main/index.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/error.jsp").forward(request, response);
            }
        }
        else if  (action.equals("post.do")) {
            member = new Member();
            member.setEmail(request.getParameter("email"));
            member.setPw(request.getParameter("pw"));
            member.setUsername(request.getParameter("username"));
            member.setPhone(request.getParameter("phone"));
            member.setAddress(request.getParameter("address"));
            member.setAdmin(Integer.parseInt(request.getParameter("admin")));

            if(dao.create(member) > 0) {
                request.setAttribute("work", member.getUsername());
                request.getRequestDispatcher("../status/message.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("../status/error.jsp").forward(request, response);
            }
        } else if(action.equals("detail.do")) {
            member = (Member) session.getAttribute("logined");
            Member retMember = dao.read(member);
            if(retMember != null) {
                request.setAttribute("member", retMember);
                request.getRequestDispatcher("../members/member-update-form.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "회원정보 상세보기 실패");
                request.getRequestDispatcher("../status/message.jsp").forward(request, response);
            }
        } else if(action.equals("logout.do")) {
            session.invalidate();
            request.getRequestDispatcher("../main/index.jsp").forward(request, response);
        } else if(action.equals("list.do")) {
            List<Member> memberList = dao.readList();
            if(memberList != null) {
                request.setAttribute("memberList", memberList);
                request.getRequestDispatcher("../members/member-list-view.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "회원정보 불러오기 실패");
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