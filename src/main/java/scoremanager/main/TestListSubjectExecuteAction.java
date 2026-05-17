package scoremanager.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
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

        // 1. まずは文字列として取得（未入力時のエラーを防ぐ）
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        // DAOの準備
        SubjectDao sDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();
        TestListSubjectDao dao = new TestListSubjectDao();

        // 2. バリデーション（未入力チェック）
        if (entYearStr == null || entYearStr.equals("0") || 
            classNum == null || classNum.equals("0") || 
            subjectCd == null || subjectCd.equals("0")) {
            
            // 【変更】未入力エラーは "subject_errors" という別の名前でJSPに渡す
            req.setAttribute("subject_errors", "入学年度とクラスと科目を選択してください");
            
        } else {
            // すべて選択されている時だけ数値に変換して検索
            int entYear = Integer.parseInt(entYearStr);
            Subject subject = sDao.get(subjectCd, teacher.getSchool());
            List<TestListSubject> tests = dao.filter(entYear, classNum, subject, teacher.getSchool());
            
            // 検索結果が0件（またはnull）の場合の判定を追加
            if (tests == null || tests.isEmpty()) {
                // こちらは画面下部に出したいので "errors" のまま
                req.setAttribute("errors", "学生情報が存在しませんでした");
            } else {
                req.setAttribute("tests", tests);
            }
            
            if (subject != null) {
                req.setAttribute("selected_subject_name", subject.getSubjectName()); 
            }
        }

        // プルダウン作成用のリスト取得（エラー時でも必要）
        List<Integer> entYearSet = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }
        List<String> classNumSet = cDao.filter(teacher.getSchool());
        List<Subject> subjects = sDao.filter(teacher.getSchool());

        // JSPに値をセット
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subjects", subjects);

        // 3. フォワード（ファイル名を test_list_subject.jsp に固定）
        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}