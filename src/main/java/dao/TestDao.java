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

    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // 列名を student_no と student_name に合わせて修正
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

            if (!list.isEmpty()) {
                test = list.get(0);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return test;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // 【修正】s.name を s.student_name に変更しました
            String sql = "select s.student_no as student_no, s.student_name as student_name, s.ent_year, " +
                         "t.subject_cd, t.no, t.point, s.class_num " +
                         "from student s " +
                         "left outer join test t on s.student_no = t.student_no " +
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

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

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

            Student student = new Student();
            student.setStudentNo(rSet.getString("student_no"));
            student.setStudentName(rSet.getString("student_name"));
            student.setEntYear(rSet.getInt("ent_year"));
            test.setStudent(student);

            list.add(test);
        }
        return list;
    }

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

    public boolean delete(Test test) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;
        try {
            String sql = "delete from test where student_no=? and subject_cd=? and no=? and school_cd=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSubjectCd());
            statement.setInt(3, test.getNo());
            statement.setString(4, test.getSchool().getSchoolCd());

            count = statement.executeUpdate();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
}