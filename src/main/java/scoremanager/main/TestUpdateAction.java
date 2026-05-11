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
 
        // すべて f1 ～ f4 の名前に統一して取得

        String entYearStr = req.getParameter("f1"); // 入学年度

        String classNum = req.getParameter("f2");   // クラス

        String subjectCd = req.getParameter("f3");  // 科目コード

        String numStr = req.getParameter("f4");     // 回数

        String studentNo = req.getParameter("stNum"); // 学生番号（遷移元から stNum で来る場合）
 
        int num = 0;

        if (numStr != null) {

            num = Integer.parseInt(numStr);

        }
 
        Student student = new Student();

        student.setStudentNo(studentNo);
 
        Subject subject = new Subject();

        subject.setSubjectCd(subjectCd);
 
        TestDao tDao = new TestDao();

        // DAOで1件取得

        Test test = tDao.get(student, subject, school, num);

        // JSPへ渡す

        req.setAttribute("test", test);

        req.setAttribute("f1", entYearStr);

        req.setAttribute("f2", classNum);

        req.setAttribute("f3", subjectCd);

        req.setAttribute("f4", numStr);       
 
        req.getRequestDispatcher("test_update.jsp").forward(req, res);

    }

}
 