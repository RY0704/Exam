package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションからユーザー（教員）情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. リクエストパラメータから科目コード（cd）を取得
        // 一覧画面の「変更」リンク等から送られてくる値です
        String cd = req.getParameter("cd");

        // 3. DAOを使って科目情報を取得
        SubjectDao subjectDao = new SubjectDao();
        // 所属校の情報を考慮して、自校の科目のみ取得するようにします
        Subject subject = subjectDao.get(cd, teacher.getSchool());
        
        req.setAttribute("subject", subject);
        // 更新画面（JSP）へフォワード
        req.getRequestDispatcher("subject_update.jsp").forward(req, res);
    
    }
}