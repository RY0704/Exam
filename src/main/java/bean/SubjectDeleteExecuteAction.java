package bean; // あなたのEclipseでエラーが出ないパッケージ名

import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータから科目コードを取得
        String cd = request.getParameter("cd");

        SubjectDao sDao = new SubjectDao();

        // 2. DAOの get メソッドを使って、DBから既存の Subject オブジェクトを取得
        // これにより、自分で setCd を呼ぶ必要がなくなります
        Subject subject = sDao.get(cd, teacher.getSchool());

        // 3. 取得したオブジェクトを削除メソッドに渡す
        if (subject != null) {
            sDao.delete(subject); 
        }

        // 4. 完了画面へ遷移
        request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
    }
}