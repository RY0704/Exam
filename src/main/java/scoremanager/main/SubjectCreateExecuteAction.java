package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		String cd = "";
		String name = "";
		Subject subject = new Subject();
		SubjectDao subjectDao = new SubjectDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメーターの取得
		cd = req.getParameter("cd");
		name = req.getParameter("name");

		// ビジネスロジック
		if (subjectDao.get(cd,  teacher.getSchool()) != null) { // 科目コードが重複している場合
			errors.put("1", "科目コードが重複しています");
			// リクエストにエラーメッセージをセット
			req.setAttribute("errors", errors);
		} else {
			// subjectに科目情報をセット
			subject.setSubjectCd(cd);
			subject.setSubjectName(name);
			subject.setSchool(teacher.getSchool());
			
			// saveメソッドで情報を登録
			subjectDao.save(subject);
		}

		// レスポンス値をセット
		req.setAttribute("cd", cd);
		req.setAttribute("name", name);

		// JSPへフォワード
		if (errors.isEmpty()) { // エラーメッセージがない場合
			req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
		} else { // エラーメッセージがある場合
			req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
		}
	}
}