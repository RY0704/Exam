package  scoremanager.main;

import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String studentCd = request.getParameter("student_cd");
        String subjectCd = request.getParameter("subject_cd");
        String noStr = request.getParameter("no");
        
        int no = 0;
        if (noStr != null) {
            no = Integer.parseInt(noStr);
        }

        Test test = new Test();
        test.setStudentNo(studentCd);
        test.setSubjectCd(subjectCd);
        test.setNo(no);

        TestDao tDao = new TestDao();
        tDao.delete(test);

        request.getRequestDispatcher("test_delete_done.jsp").forward(request, response);
    }
}