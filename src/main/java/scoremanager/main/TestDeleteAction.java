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

        // 削除に必要なパラメータを受け取る
        String studentNo = request.getParameter("student_no");
        String subjectCd = request.getParameter("subject_cd");
        String noStr = request.getParameter("no");
        int no = (noStr != null) ? Integer.parseInt(noStr) : 0;
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
            request.getRequestDispatcher("test_delete_done.jsp").forward(request, response);
        } else {
            // 確認画面表示のためのデータ取得
            TestDao tDao = new TestDao();
            Student student = new Student();
            student.setStudentNo(studentNo);
            Subject subject = new Subject();
            subject.setSubjectCd(subjectCd);
            
            Test test = tDao.get(student, subject, school, no);

            request.setAttribute("test", test);
            request.getRequestDispatcher("test_delete.jsp").forward(request, response);
        }
    }
}