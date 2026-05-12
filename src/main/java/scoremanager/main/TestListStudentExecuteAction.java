package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String studentNo = req.getParameter("f4"); // 学生番号
        String mode = req.getParameter("f");       // 検索モード

        StudentDao sDao = new StudentDao();
        TestListStudentDao tlStudentDao = new TestListStudentDao();

        // 学生検索（modeが"st"の場合）
        if ("st".equals(mode) && studentNo != null && !studentNo.isEmpty()) {
            // 1. まず学生情報を取得
            Student student = sDao.get(studentNo);

            if (student != null) {
                // 2. その学生の成績リストを取得
                List<TestListStudent> tests = tlStudentDao.filter(student);

                // 3. JSPへデータを渡す
                req.setAttribute("student", student);  // 名前表示用
                req.setAttribute("tests", tests);      // 成績一覧（空リストでも渡す）
                req.setAttribute("f4", studentNo);     // 入力値保持
            } else {
                // 学生そのものが見つからない場合のみ、errorsにセットする
                req.setAttribute("errors", "学生情報が存在しませんでした");
            }
        }

        // JSPへフォワード
        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}