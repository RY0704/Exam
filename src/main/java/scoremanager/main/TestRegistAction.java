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

        // プルダウン用のデータを取得
        List<String> class_list = cDao.filter(teacher.getSchool());
        List<Subject> subject_list = sDao.filter(teacher.getSchool());
        
        // 入学年度リスト（過去10年分）
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            ent_year_list.add(i);
        }

     // 検索ボタン(⑩)が押された時の処理
        if (entYear != 0 && classNum != null && subjectCd != null && num != 0) {
            // 1. まず、科目コード(subjectCd)を元にSubjectオブジェクトを取得する
            Subject subject = sDao.get(subjectCd, teacher.getSchool());

            // 2. TestDaoのfilterに合わせる（年度, クラス, 科目オブジェクト, 回数, 学校）
            // エラー文によるとこの順番で渡す必要があります
            List<Test> tests = tDao.filter(entYear, classNum, subject, num, teacher.getSchool());
            
            req.setAttribute("tests", tests);
        }

        // JSPにデータを渡す
        req.setAttribute("ent_year_set", ent_year_list);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        
        // 選択状態を保持するための値を送る
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", num);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}