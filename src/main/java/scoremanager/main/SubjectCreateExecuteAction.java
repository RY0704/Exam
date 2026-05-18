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

        // ローカル変数の指定
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

        // --------------------------------------------------
        // ビジネスロジック（バリデーションチェック）
        // --------------------------------------------------
        
        // 【チェック①】文字数チェック（未入力、または3文字でない場合）
        if (cd == null || cd.trim().isEmpty() || cd.trim().length() != 3) {
            errors.put("subjectCd", "科目コードは3文字で入力してください");
        } 
        // 【チェック②】重複チェック（3文字正しく入力されている場合のみ実施）
        else if (subjectDao.get(cd, teacher.getSchool()) != null) { 
            errors.put("subjectCd", "科目コードが重複しています");
        }

        // --------------------------------------------------
        // 登録判定と画面遷移の制御
        // --------------------------------------------------
        if (errors.isEmpty()) { 
            // エラーがない場合：データベースに登録して完了画面へ
            subject.setSubjectCd(cd);
            subject.setSubjectName(name);
            subject.setSchool(teacher.getSchool());
            
            // saveメソッドで情報を登録
            subjectDao.save(subject);
            
            // レスポンス値をセット（完了画面用）
            req.setAttribute("cd", cd);
            req.setAttribute("name", name);
            
            // 登録完了画面へフォワード
            req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
            
        } else { 
            // エラーがある場合：入力された値とエラー内容を持って「入力画面」に戻る
            req.setAttribute("errors", errors);
            req.setAttribute("cd", cd);     // 入力したコードを保持
            req.setAttribute("name", name); // 入力した科目名を保持
            
            // 登録画面のJSPへ直接フォワードする（ここがポイントです）
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
        }
    }
}