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

        // 1. どの科目の何回目か、保存に必要な情報を取得
        // ※JSPの検索パラメータ(f3, f4)が hidden などで送られている前提
        String subjectCd = req.getParameter("f3");
        int num = Integer.parseInt(req.getParameter("f4"));
        String classNum = req.getParameter("f2");

        TestDao tDao = new TestDao();
        List<Test> testList = new ArrayList<>();

        // 2. 画面から送られてきた全てのパラメータを確認し、点数を抽出
        java.util.Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();

            // "point_" で始まるパラメータ（点数入力欄）があれば処理
            if (name.startsWith("point_")) {
                // 学籍番号を抽出
                String studentNo = name.replace("point_", "");
                // 入力された点数を取得
                int point = Integer.parseInt(req.getParameter(name));

                // 3. Testオブジェクトを組み立てる（クラス図準拠）
                Test test = new Test();
                test.setStudentNo(studentNo); // 実際の実装に合わせてstudentオブジェクトにする場合は要調整
                test.setSubjectCd(subjectCd);
                test.setNo(num);
                test.setPoint(point);
                test.setClassNum(classNum);
                test.setSchool(school);

                testList.add(test);
            }
        }

        // 4. DAOを使って一括保存
        if (!testList.isEmpty()) {
            tDao.save(testList);
        }

        // 5. 完了メッセージを添えて元の画面へ
        req.setAttribute("message", "登録が完了しました。");
        // 検索状態を維持するためにパラメータを戻す
        req.setAttribute("f1", req.getParameter("f1"));
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", num);
        
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}