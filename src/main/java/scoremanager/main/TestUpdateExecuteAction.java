package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // --- 1. パラメータの取得 (JSPのname属性と完全に一致させる) ---
        String studentNo = req.getParameter("student_no");
        String pointStr = req.getParameter("point");
        String classNum = req.getParameter("class_num");
        
     

        // 検索保持用のパラメータ (f1, f2)
        String f1 = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        String f4 = req.getParameter("f4");
        // --- 2. 数値への変換 ---
        int num = 0;
        int point = 0;
        try {
            if (f4 != null) num = Integer.parseInt(f4);
            if (pointStr != null) point = Integer.parseInt(pointStr);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "数値を正しく入力してください");
            req.getRequestDispatcher("test_update.jsp").forward(req, res);
            return;
        }

        // --- 3. Beanの作成 ---
        Test test = new Test();
        test.setStudentNo(studentNo);
        test.setSubjectCd(f3); // ここで取得した値を入れる (nullにならない)
        test.setNo(num);      
        test.setPoint(point);
        test.setClassNum(classNum);
        test.setSchool(school);

        // --- 4. DB保存 ---
        TestDao tDao = new TestDao();
        List<Test> testList = new ArrayList<>();
        testList.add(test);
        tDao.save(testList);

        // --- 5. 完了画面へのバトンタッチ ---
        // 完了画面のリンクで使うために、あえて名前を f3, f4 に詰め替えてセット
        req.setAttribute("f1", f1);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3); 
        req.setAttribute("f4", f4);
        req.setAttribute("message", "成績の更新が完了しました。");
        
        req.getRequestDispatcher("test_update_done.jsp").forward(req, res);
    }
}