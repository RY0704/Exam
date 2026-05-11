package scoremanager.main;
 
import bean.Teacher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
 
public class TestRegistAction extends Action {
 
    @Override
 // --- executeメソッドの開始 ---
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータの取得
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String numStr = req.getParameter("f4");

        // 【重要】ここで変数をあらかじめ宣言しておく（初期値0）
        int entYear = 0;
        int num = 0;

        // 数値への変換処理
        if (entYearStr != null && !entYearStr.isEmpty() && !entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }
        if (numStr != null && !numStr.isEmpty() && !numStr.equals("0")) {
            num = Integer.parseInt(numStr);
        }

        // --- 中略 (DAOの準備やリスト取得など) ---

        // 5. 選択状態を保持するための値を送る
        // ここでエラーが出ていたのは、上の変換処理が if の中だけで完結していたためです
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", num); // これでエラーが消えるはずです

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
        
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}