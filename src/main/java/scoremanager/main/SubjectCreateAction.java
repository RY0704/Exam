package scoremanager.main;

import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. ああセッションからログインユーザー情報を取得（学校コード特定のため）
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("/scoremanager/login.jsp");
            return;
        }
        // 3. JSPへフォワード
        req.getRequestDispatcher("subject_create.jsp").forward(req, res);
    }
}