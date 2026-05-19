package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 画面のプルダウン(⑥〜⑨)から値を取得
        String entYearStr = req.getParameter("f1"); // 入学年度
        String classNum = req.getParameter("f2");   // クラス
        String subjectCd = req.getParameter("f3");  // 科目
        String numStr = req.getParameter("f4");      // 回数

        int entYear = 0;
        if (entYearStr != null && !entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }

        int num = 0;
        if (numStr != null && !numStr.equals("0")) {
            num = Integer.parseInt(numStr);
        }

        // DAOの準備
        TestDao tDao = new TestDao();
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao sDao = new SubjectDao();

        // プルダウン用のデータを取得（エラー時も正常時も画面に表示するために常に取得します）
        List<String> class_list = cDao.filter(teacher.getSchool());
        List<Subject> subject_list = sDao.filter(teacher.getSchool());

        // 入学年度リスト（過去10年分）
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            ent_year_list.add(i);
        }

        // ★ 検索ボタンが押されたかどうかの判定（初回アクセス時は entYearStr が null になる）
        if (entYearStr != null) {
            
            // ★ バリデーション：検索条件が1つでも欠けている（未選択 "0" または null）場合
            if (entYearStr.equals("0") || classNum == null || classNum.equals("0") ||
                subjectCd == null || subjectCd.equals("0") || 
                numStr == null || numStr.equals("0")) {
                
                // エラーメッセージをセット（DBの検索処理は行わない）
                req.setAttribute("errors", "入学年度とクラスと科目と回数を選択してください");
                
            } else {

                Subject subject = sDao.get(subjectCd, teacher.getSchool());
                // 2. TestDaoのfilterに合わせる
                List<Test> tests = tDao.filter(entYear, classNum, subject, num, teacher.getSchool());
                
                req.setAttribute("tests", tests);
            }
        }

        req.setAttribute("ent_year_set", ent_year_list);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", numStr);

        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
    }
}