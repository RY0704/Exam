package bean;

import java.io.IOException;

import dao.SubjectDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/scoremanager/main/SubjectDeleteExecute"})
public class SubjectDeleteExecuteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 削除するコードを取得
        String cd = request.getParameter("cd");

        SubjectDao sDao = new SubjectDao();
        try {
            // Subjectオブジェクトを組み立てる代わりにgetで取得して渡す（エラー回避）
            Subject subject = sDao.get(cd, teacher.getSchool());
            if (subject != null) {
                sDao.delete(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 完了画面へ
        request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
    }
}