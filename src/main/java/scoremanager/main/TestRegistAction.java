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

        // リクエストパラメータの取得
        String entYearStr = req.getParameter("f1"); // 入学年度
        String classNum = req.getParameter("f2");   // クラス
        String subjectCd = req.getParameter("f3");  // 科目
<<<<<<< HEAD
        String numStr = req.getParameter("f4");     // 回数
=======
        String numStr = req.getParameter("f4");      // 回数
     // 27行目（numStrの取得）の直後あたりに追加
        System.out.println("--- 検索パラメータの確認 ---");
        System.out.println("入学年度(f1): " + entYearStr);
        System.out.println("クラス(f2): " + classNum);
        System.out.println("科目コード(f3): " + subjectCd);
        System.out.println("回数(f4): " + numStr);
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

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

        // プルダウン用データの取得
        List<String> class_list = cDao.filter(teacher.getSchool());
        List<Subject> subject_list = sDao.filter(teacher.getSchool());
        
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            ent_year_list.add(i);
        }

        // 【修正点】検索ボタンが押された時の処理を整理
        if (entYear != 0 && classNum != null && !classNum.equals("0") && 
            subjectCd != null && !subjectCd.equals("0") && num != 0) {
            
            System.out.println("DEBUG: 検索条件一致。検索を開始します。");
            
            // TestDaoの引数（int, String, String, int, School）に合わせて呼び出し
            List<Test> tests = tDao.filter(entYear, classNum, subjectCd, num, teacher.getSchool());
            
            System.out.println("DEBUG: 取得結果 = " + (tests != null ? tests.size() : 0) + "件");
            
            req.setAttribute("tests", tests);
        }

        // 画面表示用データのセット
        req.setAttribute("ent_year_set", ent_year_list);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        
        // 選択状態の保持
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", num);

<<<<<<< HEAD
        req.getRequestDispatcher("/scoremanager/main/test_list.jsp").forward(req, res);
=======
        req.getRequestDispatcher("test_regist.jsp").forward(req, res);
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
    }
}