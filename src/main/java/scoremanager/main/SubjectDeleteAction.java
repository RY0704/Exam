package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String cd = request.getParameter("cd");

        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(cd, teacher.getSchool());

        request.setAttribute("subject", subject);
        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}