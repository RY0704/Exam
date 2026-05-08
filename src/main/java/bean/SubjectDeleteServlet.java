package bean;

import java.io.IOException;

import dao.SubjectDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// URLを登録します（JSPのリンク先と合わせてください）
@WebServlet(urlPatterns = {"/scoremanager/main/SubjectDelete"})
public class SubjectDeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 一覧から送られてきたコードを取得
        String cd = request.getParameter("cd");

        SubjectDao sDao = new SubjectDao();
        Subject subject = null;
        try {
            // DAOを使ってデータ取得
            subject = sDao.get(cd, teacher.getSchool());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // リクエストにセットしてJSPへ
        request.setAttribute("subject", subject);
        request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
    }
}