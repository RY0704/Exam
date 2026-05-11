package scoremanager.main;

import java.util.List;

import bean.Teacher;
import bean.TestListSubject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        int entYear = Integer.parseInt(req.getParameter("f1"));
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

     
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> tests = dao.filter(entYear, classNum, subjectCd, teacher.getSchool());


       
        req.setAttribute("tests", tests);
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);

        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}