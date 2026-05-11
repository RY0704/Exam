package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import dao.TestDeleteDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // --- JSPのリンクの引数名に合わせて取得 ---
        String studentNo = request.getParameter("stNum"); // stNum を受け取る
        String subjectCd = request.getParameter("cd");    // cd を受け取る
        String noStr = request.getParameter("no");
        int no = (noStr != null) ? Integer.parseInt(noStr) : 0;
        
        // 検索条件の維持用 (f1〜f4)
        String f1 = request.getParameter("f1");
        String f2 = request.getParameter("f2");
        String f3 = request.getParameter("f3");
        String f4 = request.getParameter("f4");

        String execute = request.getParameter("execute");

        TestDeleteDao tdDao = new TestDeleteDao();

        if (execute != null) {
            // 削除実行
            Test test = new Test();
            test.setStudentNo(studentNo);
            test.setSubjectCd(subjectCd);
            test.setNo(no);
            test.setSchool(school);

            tdDao.delete(test);
            
            // 完了画面でも「戻る」ボタンで検索条件を使えるようにセット
            request.setAttribute("f1", f1);
            request.setAttribute("f2", f2);
            request.setAttribute("f3", f3);
            request.setAttribute("f4", f4);
            
            request.getRequestDispatcher("test_delete_done.jsp").forward(request, response);
        } else {
            // 確認画面表示のためのデータ取得
            TestDao tDao = new TestDao();
            Student student = new Student();
            student.setStudentNo(studentNo);
            Subject subject = new Subject();
            subject.setSubjectCd(subjectCd);
            
            Test test = tDao.get(student, subject, school, no);

            // JSPへデータを渡す
            request.setAttribute("test", test);
            // 検索条件も確認画面の「戻る」や「実行」ボタンのために渡す
            request.setAttribute("f1", f1);
            request.setAttribute("f2", f2);
            request.setAttribute("f3", f3);
            request.setAttribute("f4", f4);

            request.getRequestDispatcher("test_delete.jsp").forward(request, response);
        }
    }
}