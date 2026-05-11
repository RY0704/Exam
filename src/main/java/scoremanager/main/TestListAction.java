package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
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
        String studentNo = req.getParameter("f4");  // 学生番号

        int entYear = 0;
        if (entYearStr != null && !entYearStr.equals("0") && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        }

        // DAOの準備
        TestDao tDao = new TestDao();
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao sDao = new SubjectDao();
        StudentDao stDao = new StudentDao();

        // --- 2. 検索処理の分岐 ---
        List<Test> tests = null;

        // A. 学生番号が入力されている場合（学生別検索を優先）
        if (studentNo != null && !studentNo.isEmpty()) {
            Student student = stDao.get(studentNo);
            if (student != null) {
                tests = tDao.postFilter(student);
            } else {
                req.setAttribute("errors", "学生情報が見つかりませんでした。");
            }

        } 
        // B. 科目情報が選択されている場合（科目別検索）
        // 入学年度(f1)、クラス(f2)、科目(f3)の3つの条件で検索
        else if (entYear != 0 && classNum != null && !classNum.equals("0") && subjectCd != null && !subjectCd.equals("0")) {
            Subject subject = sDao.get(subjectCd, teacher.getSchool());
            
            // 【修正】回数(num)を渡さず、3つの引数で検索を実行
            tests = tDao.filter(entYear, classNum, subject, teacher.getSchool());
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
        req.setAttribute("tests", tests); 
        req.setAttribute("ent_year_set", ent_year_list);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        
        // 入力値を保持させる
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);

        // JSPへ転送
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}