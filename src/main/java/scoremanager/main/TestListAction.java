package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 1. パラメータの取得（初期表示時は基本 null または空）
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String studentNo = req.getParameter("f4");

        // 2. DAOの準備（検索はしないので ClassNum と Subject のみ）
        ClassNumDao cDao = new ClassNumDao();
        SubjectDao sDao = new SubjectDao();

        // 3. プルダウン用データの取得
        List<String> class_list = cDao.filter(teacher.getSchool());
        List<Subject> subject_list = sDao.filter(teacher.getSchool());
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            ent_year_list.add(i);
        }

        // 4. JSPへのデータ受け渡し
        // 検索結果 tests は「まだ存在しない」状態として null を送るか、セットしない
        req.setAttribute("tests", null); 
        
        req.setAttribute("ent_year_set", ent_year_list);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        
        // 入力値の保持
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);

        // JSPへ転送
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}