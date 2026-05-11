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
        // セッションから教師情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // リクエストパラメータを取得
        int entYear = Integer.parseInt(req.getParameter("f1"));
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        // SubjectDaoを使って、検索条件に指定された科目オブジェクトを取得
        SubjectDao sDao = new SubjectDao();
        Subject subject = sDao.get(subjectCd, teacher.getSchool());

        // 専用のDAOを使用して科目別成績一覧を取得
        TestListSubjectDao dao = new TestListSubjectDao();
        List<TestListSubject> tests = dao.filter(entYear, classNum, subject, teacher.getSchool());

        // JSPに値をセット
        req.setAttribute("tests", tests); // 1回目・2回目が統合されたリスト
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);

        // 結果を表示するJSPへフォワード
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}