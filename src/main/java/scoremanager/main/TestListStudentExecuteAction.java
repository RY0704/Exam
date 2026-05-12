package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String studentNo = req.getParameter("f4");
        String mode = req.getParameter("f");

        StudentDao sDao = new StudentDao();
        SubjectDao subDao = new SubjectDao();
        ClassNumDao cDao = new ClassNumDao();
        TestListStudentDao tlStudentDao = new TestListStudentDao();
        TestDao tDao = new TestDao();

        String done = "test_list_student.jsp";

        if ("st".equals(mode) && studentNo != null && !studentNo.isEmpty()) {
            Student student = sDao.get(studentNo);
            if (student != null) {
                List<TestListStudent> tests = tlStudentDao.filter(student);
                req.setAttribute("student", student);
                req.setAttribute("tests", tests);
            } else {
                req.setAttribute("errors", "学生情報が存在しませんでした");
            }
        } else if ("sj".equals(mode)) {
            if (entYearStr != null && !entYearStr.equals("0") && 
                classNum != null && !classNum.equals("0") && 
                subjectCd != null && !subjectCd.equals("0")) {
                
                int entYear = Integer.parseInt(entYearStr);
                Subject subject = subDao.get(subjectCd, teacher.getSchool());
                
                // ★エラー回避：戻り値が List<Test> の場合は、受け取り側もそれに合わせます
                List<Test> results = tDao.filter(entYear, classNum, subject, 0, teacher.getSchool());
                
                req.setAttribute("tests_subject", results);
                req.setAttribute("selected_subject", subject);
                done = "test_list_subject.jsp"; 
            } else {
                req.setAttribute("errors", "入学年度、クラス、科目を選択してください");
            }
        }

        // 共通データ準備
        List<String> class_list = cDao.filter(teacher.getSchool());
        List<Subject> subject_list = subDao.filter(teacher.getSchool());
        List<Integer> ent_year_list = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            ent_year_list.add(i);
        }

        req.setAttribute("ent_year_set", ent_year_list);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("subjects", subject_list);
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", studentNo);

        req.getRequestDispatcher(done).forward(req, res);
    }
}