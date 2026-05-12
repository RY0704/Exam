package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        Subject subject = new Subject(); 
        subject.setSubjectCd(cd);               
        subject.setSubjectName(name);
        subject.setSchool(teacher.getSchool());

        SubjectDao subjectDao = new SubjectDao();
        subjectDao.save(subject); 

        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}