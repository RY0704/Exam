package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
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

        // パラメータ取得
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String studentNo = req.getParameter("f4");
        String mode = req.getParameter("f"); // ボタン判定用

        // DAOのインスタンス化
        StudentDao sDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();
        TestListStudentDao tlStudentDao = new TestListStudentDao();
        TestDao tDao = new TestDao();

        // 遷移先の初期値
        String done = "test_list_student.jsp";

        // --- 検索処理の振り分け ---
        if ("st".equals(mode) && studentNo != null && !studentNo.isEmpty()) {
            // 【学生番号検索】
            Student student = sDao.get(studentNo);
            if (student != null) {
                List<TestListStudent> tests = tlStudentDao.filter(student);
                req.setAttribute("student", student);
                req.setAttribute("tests", tests);
            } else {
                req.setAttribute("errors", "学生情報が存在しませんでした");
            }
            done = "test_list_student.jsp";

        } else if ("sj".equals(mode)) {
            // 【科目情報検索】
            
            // ★ 学生情報の保持をやめる（クリア処理）
            studentNo = ""; 
            req.removeAttribute("student");
            req.removeAttribute("tests");

            if (entYearStr != null && !entYearStr.equals("0") && 
                classNum != null && !classNum.equals("0") && 
                subjectCd != null && !subjectCd.equals("0")) {
                
                int entYear = Integer.parseInt(entYearStr);
                // DAOの定義に合わせて引数を渡す
                Subject subject = subDao.get(subjectCd, teacher.getSchool());
                
                // テスト一覧を取得（List<Test>で受け取る）
                List<Test> results = tDao.filter(entYear, classNum, subject, 0, teacher.getSchool());
                
                req.setAttribute("tests_subject", results);
                req.setAttribute("selected_subject", subject);
                
                // ★ 科目別のJSPへ遷移
                done = "test_list_subject.jsp"; 
            } else {
                req.setAttribute("errors", "入学年度、クラス、科目を選択してください");
                done = "test_list_student.jsp";
            }
        }

        // --- 共通データの準備（プルダウン用） ---
        List<String> class_list = cDao.filter(teacher.getSchool());
        List<Subject> subject_list = subDao.filter(teacher.getSchool());
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            ent_year_list.add(i);
        }

        // JSPへ渡す属性をセット
        req.setAttribute("ent_year_set", ent_year_list);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo); // 科目検索時は空文字列が入る

        // フォワード実行
        req.getRequestDispatcher(done).forward(req, res);
    }
}