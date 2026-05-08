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

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 1. 検索条件（科目、回数、クラス）をリクエストから取得
        // ※ JSPのform内に <input type="hidden"> で持たせておく必要があります
        String subjectCd = req.getParameter("f3"); // 科目コード
        int num = Integer.parseInt(req.getParameter("f4")); // 回数
        String classNum = req.getParameter("f2"); // クラス番号

        // 2. 画面から送られてくる「学籍番号のリスト」と「点数のリスト」を取得
        // JSP側で name="student_no_set[]" および name="point_set[]" としている前提
        String[] studentNoSet = req.getParameterValues("student_no_set[]");
        String[] pointSet = req.getParameterValues("point_set[]");

        List<Test> testList = new ArrayList<>();
        TestDao tDao = new TestDao();

        if (studentNoSet != null && pointSet != null) {
            // 学生の数だけループしてTestインスタンスを作成
            for (int i = 0; i < studentNoSet.length; i++) {
                Test test = new Test();
                
                // カラム名に対応するフィールドへセット
                test.setStudentNo(studentNoSet[i]); // student_no
                test.setSubjectCd(subjectCd);       // subject_cd
                test.setNo(num);                    // no (回数)
                test.setSchool(school);             // school_cd用
                test.setClassNum(classNum);         // class_num
                
                // 点数のパース処理
                int point = 0;
                if (pointSet[i] != null && !pointSet[i].isEmpty()) {
                    point = Integer.parseInt(pointSet[i]);
                }
                test.setPoint(point);               // point

                testList.add(test);
            }
        }

        // 3. DAOを使って一括保存
        try {
            // TestDaoの save(List<Test>) メソッドを呼び出し
            tDao.save(testList);
            
            // 登録完了後は完了画面へ
            req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            // エラー時はエラーページへ（error.jspがない場合は作成するか、適切なページへ）
            req.getRequestDispatcher("/scoremanager/main/error.jsp").forward(req, res);
        }
    }
}