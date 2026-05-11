package  scoremanager.main;

import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. パラメータの取得
        String studentCd = request.getParameter("student_cd");
        String subjectCd = request.getParameter("subject_cd");
        String noStr = request.getParameter("no");
        
        int no = 0;
        if (noStr != null) {
            no = Integer.parseInt(noStr);
        }

        // 2. 削除用オブジェクト(Test)の組み立て
        // 以前のエラーの原因だった Student/Subject オブジェクトの作成を省き、
        // Test.java のフィールドに直接文字列をセットする形にします。
        Test test = new Test();
        test.setStudentNo(studentCd);
        test.setSubjectCd(subjectCd);
        test.setNo(no);

        // 3. 削除実行
        // TestDao側が public boolean delete(Test test) という形ならこれでエラーは消えます
        TestDao tDao = new TestDao();
        tDao.delete(test);

        // 4. 完了画面へ遷移
        request.getRequestDispatcher("test_delete_done.jsp").forward(request, response);
    }
}