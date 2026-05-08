package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Test;

public class TestDao extends Dao {

    public Test get(String studentNo, String subjectCd, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "select * from test where student_no=? and subject_cd=? and school_cd=? and no=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setString(3, school.getSchoolCd());
            statement.setInt(4, no);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                test = new Test();
                test.setStudentNo(resultSet.getString("student_no"));
                test.setSubjectCd(resultSet.getString("subject_cd"));
                test.setClassNum(resultSet.getString("class_num"));
                test.setNo(resultSet.getInt("no"));
                test.setPoint(resultSet.getInt("point"));
                test.setSchool(school);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return test;
    }

    private List<Test> postFilter(ResultSet resultSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        while (resultSet.next()) {
            Test test = new Test();
            test.setStudentNo(resultSet.getString("student_no"));
            test.setSubjectCd(resultSet.getString("subject_cd"));
            test.setNo(resultSet.getInt("no"));
            test.setPoint(resultSet.getInt("point"));
            test.setClassNum(resultSet.getString("class_num"));
            test.setSchool(school);
            list.add(test);
        }
        return list;
    }

    public List<Test> filter(int entYear, String classNum, String subjectCd, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // 【修正点】s.is_attend = true の条件を削除しました
            String sql =
            	    "select t.* " +
            	    "from test t " +
            	    "join student s on t.student_no = s.student_no " +
            	    "and t.school_cd = s.school_cd " +
            	    "where s.ent_year = ? " +
            	    "and s.class_num = ? " +
            	    "and t.subject_cd = ? " +
            	    "and t.no = ? ";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subjectCd);
            statement.setInt(4, num);
            statement.setString(5, school.getSchoolCd());

            resultSet = statement.executeQuery();
            list = postFilter(resultSet, school);

        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    public boolean save(List<Test> list) throws Exception {
        boolean result = true;
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            for (Test test : list) {
                if (!save(test, connection)) {
                    result = false;
                    break;
                }
            }
            if (result) connection.commit();
            else connection.rollback();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (connection != null) connection.close();
        }
        return result;
    }

    public boolean save(Test test, Connection connection) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String checkSql = "select count(*) from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";
            statement = connection.prepareStatement(checkSql);
            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSubjectCd());
            statement.setString(3, test.getSchool().getSchoolCd());
            statement.setInt(4, test.getNo());
            resultSet = statement.executeQuery();
            resultSet.next();
            boolean exists = resultSet.getInt(1) > 0;
            statement.close();

            if (exists) {
                String updateSql = "update test set point = ?, class_num = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";
                statement = connection.prepareStatement(updateSql);
                statement.setInt(1, test.getPoint());
                statement.setString(2, test.getClassNum());
                statement.setString(3, test.getStudentNo());
                statement.setString(4, test.getSubjectCd());
                statement.setString(5, test.getSchool().getSchoolCd());
                statement.setInt(6, test.getNo());
            } else {
                String insertSql = "insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?,?,?,?,?,?)";
                statement = connection.prepareStatement(insertSql);
                statement.setString(1, test.getStudentNo());
                statement.setString(2, test.getSubjectCd());
                statement.setString(3, test.getSchool().getSchoolCd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClassNum());
            }
            statement.executeUpdate();
            return true;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        }
    }
}