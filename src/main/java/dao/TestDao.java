package dao;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
 
public class TestDao extends Dao {
<<<<<<< HEAD
 
    /**
     * クラス図に基づいたベースSQL
     * studentテーブルをJOINし、名前や入学年度も取得できるようにしています
     */
    private final String baseSql =
        "select t.*, s.student_name, s.ent_year " +
        "from test t " +
        "join student s on t.student_no = s.student_no " +
        "and t.school_cd = s.school_cd ";
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
    /**
     * 指定された条件で1件の成績情報を取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
        try {
            // SELECT文も順番を明示
            String sql = "select t.*, s.student_name, s.ent_year from test t " +
                         "join student s on t.student_no = s.student_no and t.school_cd = s.school_cd " +
                         "where t.student_no=? and t.subject_cd=? and t.school_cd=? and t.no=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, student.getStudentNo());
            statement.setString(2, subject.getSubjectCd());
            statement.setString(3, school.getSchoolCd());
            statement.setInt(4, no);
            
            ResultSet rSet = statement.executeQuery();
            List<Test> list = postFilter(rSet, school);
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
            if (!list.isEmpty()) {
                test = list.get(0);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return test;
    }
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
    /**
     * 検索条件に合致する成績一覧を取得する
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
        try {
<<<<<<< HEAD
            // SQL文：IS_ATTENDフラグも含めてチェック
            String sql = baseSql +
                "where s.ent_year = ? and s.class_num = ? and t.subject_cd = ? " +
                "and t.no = ? and t.school_cd = ? and s.is_attend = true " +
                "order by t.student_no";
 
=======
            String sql = "select t.*, s.student_name, s.ent_year from test t " +
                         "join student s on t.student_no = s.student_no and t.school_cd = s.school_cd " +
                         "where s.ent_year = ? and s.class_num = ? and t.subject_cd = ? " +
                         "and t.no = ? and t.school_cd = ? and s.is_attend = true " +
                         "order by t.student_no";

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getSubjectCd());
            statement.setInt(4, num);
            statement.setString(5, school.getSchoolCd());
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
    /**
     * ResultSetからTestオブジェクトに詰め替える
     */
    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rSet.next()) {
            Test test = new Test();
            test.setStudentNo(rSet.getString("student_no"));
            test.setSubjectCd(rSet.getString("subject_cd"));
            test.setNo(rSet.getInt("no"));
            test.setPoint(rSet.getInt("point"));
            test.setClassNum(rSet.getString("class_num"));
            test.setSchool(school);
<<<<<<< HEAD
 
            // 学生(Student) Beanを作成し、Test Beanにセットする（JSP表示用）
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
            Student student = new Student();
            student.setStudentNo(rSet.getString("student_no"));
            student.setStudentName(rSet.getString("student_name"));
            student.setEntYear(rSet.getInt("ent_year"));
            test.setStudent(student);
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
            list.add(test);
        }
        return list;
    }
<<<<<<< HEAD
 
=======

>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
    /**
     * 成績情報のリストを一括保存する
     */
    public boolean save(List<Test> list) throws Exception {
        Connection connection = getConnection();
        boolean result = true;
        try {
            connection.setAutoCommit(false);
            for (Test test : list) {
                if (!save(test, connection)) {
                    result = false;
                    break;
                }
            }
            if (result) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (connection != null) connection.close();
        }
        return result;
    }
 
    /**
     * 1件の成績情報を保存する
     */
    private boolean save(Test test, Connection connection) throws Exception {
        PreparedStatement statement = null;
        try {
            // 【重要】 values の順番と、下の setString の番号を完全に一致させます
            // 順番: 1:student_no, 2:school_cd, 3:subject_cd, 4:no, 5:point, 6:class_num
            String sql = "merge into test (student_no, school_cd, subject_cd, no, point, class_num) " +
                         "key(student_no, school_cd, subject_cd, no) " +
                         "values(?, ?, ?, ?, ?, ?)";
            
            statement = connection.prepareStatement(sql);
            
            // 1番目: 学籍番号
            statement.setString(1, test.getStudentNo());
            // 2番目: 学校コード (画像の "tes" の位置)
            statement.setString(2, test.getSchool().getSchoolCd());
            // 3番目: 科目コード (画像の "001" の位置)
            statement.setString(3, test.getSubjectCd());
            // 4番目: 回数
            statement.setInt(4, test.getNo());
            // 5番目: 得点
            statement.setInt(5, test.getPoint());
            // 6番目: クラス番号
            statement.setString(6, test.getClassNum());
 
            statement.executeUpdate();
            return true;
        } finally {
            if (statement != null) statement.close();
        }
    }
}