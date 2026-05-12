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

        // リクエストパラメータを取得
        int entYear = Integer.parseInt(req.getParameter("f1"));
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        // DAOの準備
        SubjectDao sDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();
        TestListSubjectDao dao = new TestListSubjectDao();

        // 検索に必要な情報を取得
        Subject subject = sDao.get(subjectCd, teacher.getSchool());
        List<TestListSubject> tests = dao.filter(entYear, classNum, subject, teacher.getSchool());
        
        // 再検索（プルダウン作成）のために必要なリストを再取得してセット
        List<Integer> entYearSet = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }
        List<String> classNumSet = cDao.filter(teacher.getSchool());
        List<Subject> subjects = sDao.filter(teacher.getSchool());

        // JSPに値をセット（検索条件の保持用）
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("tests", tests);
        
        // プルダウン用のリスト
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subjects", subjects);
        
        if (subject != null) {
            // ここを getName() から getSubjectName() に修正しました
            // ※もしこれでもエラーが出るなら、Subjectクラスのメソッド名を確認してください
            req.setAttribute("selected_subject_name", subject.getSubjectName()); 
        }

        // フォワード（test_list_subject.jspへ）
        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}