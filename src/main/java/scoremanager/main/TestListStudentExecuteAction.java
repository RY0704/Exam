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
        // 1. セッションからログインユーザー情報を取得（学校コード特定のため）
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. リクエストパラメータの取得
        String studentNo = req.getParameter("f4"); // 学生番号
        String mode = req.getParameter("f");       // 検索モード ("st")

        // 3. DAOの準備
        StudentDao sDao = new StudentDao();
        TestListStudentDao tlStudentDao = new TestListStudentDao();

        // 4. 学生検索ロジック
        if ("st".equals(mode) && studentNo != null && !studentNo.isEmpty()) {
            
            // 学生番号が存在するかチェック
            Student student = sDao.get(studentNo);

            if (student != null) {
                // 【重要】DAOを使って、特定の学生の成績一覧を取得
                // 内部で testテーブルを検索し、List<TestListStudent> を作成して戻す
                List<TestListStudent> tests = tlStudentDao.filter(student);

                // JSPの <c:forEach items="${tests}"> に渡すデータをセット
                req.setAttribute("tests", tests);
                
                // 検索した学生番号をJSPの入力欄に戻すためにセット
                req.setAttribute("f4", studentNo);
            } else {
                // 学生が見つからない場合のエラー処理
                req.setAttribute("errors", "学生情報が見つかりませんでした。");
            }
        }

        // 5. 表示用データの再セット（入学年度やクラスなどのプルダウンを維持する場合）
        // ※ここでは省略していますが、必要に応じて科目リストなどを再度セットしてください

        // 6. 結果表示用JSP（test_list_student.jsp）へフォワード
        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}