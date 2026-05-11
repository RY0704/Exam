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

    /**
     * 指定された条件で1件の成績情報を取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "select s.student_no, s.student_name, s.ent_year, s.class_num as student_class_num, " +
                         "t.point, t.no, t.subject_cd, t.class_num as test_class_num " +
                         "from student s " +
                         "left join test t on s.student_no = t.student_no " +
                         "and t.subject_cd = ? and t.no = ? and t.school_cd = ? " +
                         "where s.student_no = ? and s.school_cd = ?";
            
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getSubjectCd());
            statement.setInt(2, no);
            statement.setString(3, school.getSchoolCd());
            statement.setString(4, student.getStudentNo());
            statement.setString(5, school.getSchoolCd());
            
            ResultSet rSet = statement.executeQuery();
            List<Test> list = postFilter(rSet, school);

            if (!list.isEmpty()) {
                test = list.get(0);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return test;
    }

    /**
     * 【成績参照用：引数4つ】
     * 検索条件に合致する成績一覧を取得する（回数指定なし）
     * TestListAction で使用します。
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "select s.student_no, s.student_name, s.ent_year, s.class_num as student_class_num, " +
                         "t.point, t.no, t.subject_cd, t.class_num as test_class_num " +
                         "from student s " +
                         "left join test t on s.student_no = t.student_no " +
                         "and t.subject_cd = ? and t.school_cd = ? " +
                         "where s.ent_year = ? and s.class_num = ? and s.school_cd = ? and s.is_attend = true " +
                         "order by s.student_no asc, t.no asc";

            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getSubjectCd());
            statement.setString(2, school.getSchoolCd());
            statement.setInt(3, entYear);
            statement.setString(4, classNum);
            statement.setString(5, school.getSchoolCd());

            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);

            for (Test t : list) {
                t.setSubjectCd(subject.getSubjectCd());
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    /**
     * 【成績登録用：引数5つ】
     * 検索条件に合致する成績一覧を取得する（回数指定あり）
     * TestRegistAction で使用します。
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "select s.student_no, s.student_name, s.ent_year, s.class_num as student_class_num, " +
                         "t.point, t.no, t.subject_cd, t.class_num as test_class_num " +
                         "from student s " +
                         "left join test t on s.student_no = t.student_no " +
                         "and t.subject_cd = ? and t.no = ? and t.school_cd = ? " +
                         "where s.ent_year = ? and s.class_num = ? and s.school_cd = ? and s.is_attend = true " +
                         "order by s.student_no asc";

            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getSubjectCd());
            statement.setInt(2, num);
            statement.setString(3, school.getSchoolCd());
            statement.setInt(4, entYear);
            statement.setString(5, classNum);
            statement.setString(6, school.getSchoolCd());

            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);

            for (Test t : list) {
                t.setSubjectCd(subject.getSubjectCd());
                t.setNo(num);
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    /**
     * 特定の学生の成績一覧を取得する（学生別検索用）
     */
    public List<Test> postFilter(Student student) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "select s.student_no, s.student_name, s.ent_year, s.class_num as student_class_num, " +
                         "t.point, t.no, t.subject_cd, t.class_num as test_class_num " +
                         "from student s " +
                         "inner join test t on s.student_no = t.student_no " +
                         "where s.student_no = ? and s.school_cd = ? " +
                         "order by t.subject_cd asc, t.no asc";

            statement = connection.prepareStatement(sql);
            statement.setString(1, student.getStudentNo());
            statement.setString(2, student.getSchool().getSchoolCd());

            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, student.getSchool());

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    /**
     * ResultSetからTestオブジェクトに詰め替える（内部共通処理）
     */
    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rSet.next()) {
            Test test = new Test();
            test.setStudentNo(rSet.getString("student_no"));
            test.setPoint(rSet.getInt("point"));
            
            try {
                test.setSubjectCd(rSet.getString("subject_cd"));
                test.setNo(rSet.getInt("no"));
            } catch (Exception e) {}
            
            String classNum = rSet.getString("test_class_num");
            if (classNum == null) {
                classNum = rSet.getString("student_class_num");
            }
            test.setClassNum(classNum);
            test.setSchool(school);

            Student student = new Student();
            student.setStudentNo(rSet.getString("student_no"));
            student.setStudentName(rSet.getString("student_name"));
            student.setEntYear(rSet.getInt("ent_year"));
            test.setStudent(student);

            list.add(test);
        }
        return list;
    }

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
            String sql = "merge into test (student_no, school_cd, subject_cd, no, point, class_num) " +
                         "key(student_no, school_cd, subject_cd, no) " +
                         "values(?, ?, ?, ?, ?, ?)";
            
            statement = connection.prepareStatement(sql);
            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSchool().getSchoolCd());
            statement.setString(3, test.getSubjectCd());
            statement.setInt(4, test.getNo());
            statement.setInt(5, test.getPoint());
            statement.setString(6, test.getClassNum());

            statement.executeUpdate();
            return true;
        } finally {
            if (statement != null) statement.close();
        }
    }
}