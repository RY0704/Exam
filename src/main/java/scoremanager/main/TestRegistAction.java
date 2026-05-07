
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

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 検索窓（f1）から入力された学籍番号を取得
        String studentNo = req.getParameter("f1");

        StudentDao sDao = new StudentDao();
        TestListStudentDao tlDao = new TestListStudentDao();

        if (studentNo != null && !studentNo.isEmpty()) {
            // 学籍番号を元に学生情報を取得
            Student student = sDao.get(studentNo);
            
            if (student != null) {
                // その学生の成績リストを取得（TestListStudentDaoを使用）
                List<TestListStudent> tests = tlDao.filter(student);
                
                // JSPにデータを渡す
                req.setAttribute("student", student);
                req.setAttribute("tests", tests);
            } else {
                // 見つからなかった場合のエラーメッセージ
                req.setAttribute("errors", "学生情報が見つかりませんでした");
            }
        }
        // JSPへフォワード
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}
