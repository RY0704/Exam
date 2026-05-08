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

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // --- 1. パラメータの取得 ---
        String entYearStr = req.getParameter("f1"); // 入学年度
        String classNum = req.getParameter("f2");   // クラス
        String subjectCd = req.getParameter("f3");  // 科目
        String studentNo = req.getParameter("f4");  // 学生番号 (JSPではここが学生番号)

        int entYear = 0;
        if (entYearStr != null && !entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }

        // DAOの準備
        TestDao tDao = new TestDao();
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao sDao = new SubjectDao();

        // --- 2. 検索処理の分岐 ---
        List<Test> tests = null;

        // A. 科目情報が選択されている場合（入学年度、クラス、科目が0以外）
        if (entYear != 0 && classNum != null && !classNum.equals("0") && subjectCd != null && !subjectCd.equals("0")) {
            Subject subject = sDao.get(subjectCd, teacher.getSchool());
            
            // 【重要】TestDao.filterの引数に「回数」が必要な場合、JSPに「回数」の項目がないため、
            // ここでは暫定的に「1」を入れるか、DAO側に回数なしのメソッドを作る必要があります。
            int num = 1; 
            tests = tDao.filter(entYear, classNum, subject, num, teacher.getSchool());
            
        } 
        // B. 学生番号が入力されている場合（科目情報が不完全でも学生番号があれば優先）
        else if (studentNo != null && !studentNo.isEmpty()) {
            // TestDaoに学生番号で検索するメソッドがある前提です
            // tests = tDao.filterByStudent(studentNo, teacher.getSchool());
            
            // もしメソッドがない場合は、学生番号でTestのリストを返す処理をDAOに追加してください
        }

        // --- 3. プルダウン用データの取得 ---
        List<String> class_list = cDao.filter(teacher.getSchool());
        List<Subject> subject_list = sDao.filter(teacher.getSchool());
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            ent_year_list.add(i);
        }

        // --- 4. JSPへのデータ受け渡し ---
        req.setAttribute("tests", tests); // 検索結果
        req.setAttribute("ent_year_set", ent_year_list);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        
        // 入力値を保持させる
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);

        // JSPへ転送 (ファイル名は適宜合わせてください)
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}