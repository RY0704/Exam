package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String studentNo = req.getParameter("stNum");
        String subjectCd = req.getParameter("f3");
        String numStr = req.getParameter("f4");
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
       int num = Integer.parseInt(numStr);

        Student student = new Student();
        student.setStudentNo(studentNo);

        Subject subject = new Subject();
        subject.setSubjectCd(subjectCd);

        TestDao tDao = new TestDao();
        Test test = tDao.get(student, subject, school, num);
        
        req.setAttribute("test", test);
        
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", num);       

        req.getRequestDispatcher("test_update.jsp").forward(req, res);
    }
}