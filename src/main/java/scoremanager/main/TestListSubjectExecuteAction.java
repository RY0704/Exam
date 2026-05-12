package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        int entYear = Integer.parseInt(req.getParameter("f1"));
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, teacher.getSchool());

        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> tests = dao.filter(entYear, classNum, subject, teacher.getSchool());

        // JSPに値をセット
        req.setAttribute("tests", tests);
        req.setAttribute("tests", tests); 
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("selected_subject_name", subject.getSubjectName()); // 科目名をJSPで表示するために追加

        // 【修正】正しいJSPファイル名へフォワード
        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}