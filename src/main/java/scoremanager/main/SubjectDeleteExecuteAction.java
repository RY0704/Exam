package scoremanager.main;



import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;



public class SubjectDeleteExecuteAction extends Action {



    @Override

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッションから教員情報を取得

        HttpSession session = req.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");



        // 2. subject_delete.jsp の hidden 項目から科目コードを取得

        String subjectCd = req.getParameter("subject_cd");



        // 3. 削除用の Subject Bean を作成して値をセット

        Subject subject = new Subject();

        subject.setSubjectCd(subjectCd);

        subject.setSchool(teacher.getSchool());



        // 4. DAO を使って削除を実行

        SubjectDao sDao = new SubjectDao();

        sDao.delete(subject); // ここで実際に DB から消えます



        // 5. 削除完了画面 (subject_delete_done.jsp) へフォワード

        req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);

    }

}