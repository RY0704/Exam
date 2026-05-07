package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからユーザー（教員）データを取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 一覧画面から送られてきた科目コードを取得
        String cd = request.getParameter("cd");

        SubjectDao sDao = new SubjectDao();
        // 学校コードと科目コードに合致する詳細データを取得（図の「科目の詳細データを取得」）
        Subject subject = sDao.get(cd, teacher.getSchool());

        // 取得した科目データをリクエスト属性にセットしてJSPへ
        request.setAttribute("subject", subject);
        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}