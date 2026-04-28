package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッションからユーザー情報を取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// DAOを初期化
		SubjectDao subjectDao = new SubjectDao();

		// DBから科目一覧を取得（DAOのfilterメソッドを使用）
		List<Subject> subjects = subjectDao.filter(teacher.getSchool());

		// JSPへ渡す値をセット
		req.setAttribute("subjects", subjects);

		// JSPへフォワード
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}