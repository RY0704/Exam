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

        String subjectCd = req.getParameter("f3");
        String numStr = req.getParameter("f4");
        String classNum = req.getParameter("f2");
        String entYearStr = req.getParameter("f1");

        int num = 0;
        if (numStr != null) {
            num = Integer.parseInt(numStr);
        }

        TestDao tDao = new TestDao();
        List<Test> testList = new ArrayList<>();
        // ★ エラーがあった学籍番号を格納するリスト
        List<String> errorStudentNos = new ArrayList<>();
        
        java.util.Enumeration<String> names = req.getParameterNames();//getParameterNames()は既存メソッドでnameラベルのものを全取得
        while (names.hasMoreElements()) {
            String name = names.nextElement();

            if (name.startsWith("point_")) {
                String studentNo = name.replace("point_", "");
                String pointStr = req.getParameter(name);
                int point = 0;
                
                if (pointStr != null && !pointStr.isEmpty()) {
                    point = Integer.parseInt(pointStr);
                }

                // ★ バリデーション：範囲外ならエラーリストに追加
                if (point < 0 || point > 100) {
                    errorStudentNos.add(studentNo);
                }

                Test test = new Test();
                test.setStudentNo(studentNo);
                test.setSubjectCd(subjectCd);
                test.setNo(num);
                test.setPoint(point);
                test.setClassNum(classNum);
                test.setSchool(school);

                testList.add(test);
            }
        }

        // ★ 一人でもエラーがいれば保存せずに戻る
        if (!errorStudentNos.isEmpty()) {
            req.setAttribute("errorStudentNos", errorStudentNos);
            
            req.setAttribute("f1", entYearStr);
            req.setAttribute("f2", classNum);
            req.setAttribute("f3", subjectCd);
            req.setAttribute("f4", num);
            
            req.getRequestDispatcher("TestRegist.action").forward(req, res);
            return;
        }

        if (!testList.isEmpty()) {
            tDao.save(testList);
        }

        req.setAttribute("message", "登録が完了しました。");
        req.setAttribute("f1", entYearStr);//検索条件の保持
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", num);
        
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}