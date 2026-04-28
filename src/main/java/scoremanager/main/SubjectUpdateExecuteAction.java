package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. リクエストパラメータから更新データを受け取る
        // JSPの <input name="cd"> と <input name="name"> に対応します
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        // 2. 科目(Subject) Beanの作成と値のセット
        Subject subject = new Subject(); // Student ではなく Subject
        subject.setSubjectCd(cd);               // メソッド名は Bean の定義に合わせてください
        subject.setSubjectName(name);
        subject.setSchool(teacher.getSchool());

        // 3. DAOを使ってDBに保存（更新）
        SubjectDao subjectDao = new SubjectDao();
        subjectDao.save(subject); // subjectDao の save メソッドを呼び出す

        // 4. 完了画面へ遷移
        req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
    }
}