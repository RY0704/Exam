package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションからログインユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. リクエストパラメータの取得
        // f4: 学生番号, f: 検索モード("st"が送られてくる)
        String studentNo = req.getParameter("f4");
        String mode = req.getParameter("f");

        // --- DAOの準備 ---
        StudentDao sDao = new StudentDao();
        TestListStudentDao tlStudentDao = new TestListStudentDao();
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao subDao = new SubjectDao();

        // 3. プルダウン用データの再セット（画面を維持するために必須）
        // これを行わないと、検索ボタンを押した瞬間に上のセレクトボックスが消えてしまいます
        List<String> class_list = cDao.filter(teacher.getSchool());
        List<Subject> subject_list = subDao.filter(teacher.getSchool());
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            ent_year_list.add(i);
        }
        
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        req.setAttribute("ent_year_set", ent_year_list);

        // 4. 学生検索ロジックの実行
        // modeが "st" かつ 学生番号が入力されているかチェック
        if ("st".equals(mode) && studentNo != null && !studentNo.isEmpty()) {
            
            // 学生番号から学生オブジェクトを取得
            Student student = sDao.get(studentNo);

            if (student != null) {
                // 作成済みのList<TestListStudent>を取得するDAOメソッドを呼び出し
                List<TestListStudent> tests = tlStudentDao.filter(student);

                // JSPの <c:forEach var="t" items="${tests}"> で使う名前でセット
                req.setAttribute("tests", tests);
                
                // JSPのテキストボックスに入力値を残す
                req.setAttribute("f4", studentNo);
            } else {
                // 学生が見つからなかった場合
                req.setAttribute("errors", "成績情報が存在しません");
            }
        }

        // 5. 共通の検索画面(test_list.jsp)へフォワード
        // 設計図上、表示するベースとなるJSPは一つなので、ここに送ります
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}