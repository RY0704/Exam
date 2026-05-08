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
     * クラス図に基づいたベースSQL
     * studentテーブルをJOINし、名前や入学年度も取得できるようにしています
     */
    private final String baseSql = 
        "select t.*, s.student_name, s.ent_year " +
        "from test t " +
        "join student s on t.student_no = s.student_no " +
        "and t.school_cd = s.school_cd ";

    /**
     * 指定された条件で1件の成績情報を取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // ベースSQLに条件を追加（テーブル名 t. を明示）
            String sql = baseSql + "where t.student_no=? and t.subject_cd=? and t.school_cd=? and t.no=?";
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

    /**
     * 検索条件（入学年度、クラス、科目、回数）に合致する成績一覧を取得する
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQL文：IS_ATTENDフラグも含めてチェック
            String sql = baseSql + 
                "where s.ent_year = ? and s.class_num = ? and t.subject_cd = ? " +
                "and t.no = ? and t.school_cd = ? and s.is_attend = true " +
                "order by t.student_no";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getSubjectCd());
            statement.setInt(4, num);
            statement.setString(5, school.getSchoolCd());

            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    /**
     * ResultSetから得られた情報をTestオブジェクトに詰め替える（Student情報も紐付け）
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

            // 学生(Student) Beanを作成し、Test Beanにセットする（JSP表示用）
            Student student = new Student();
            student.setStudentNo(rSet.getString("student_no"));
            student.setStudentName(rSet.getString("student_name")); // DBのカラム名に準拠
            student.setEntYear(rSet.getInt("ent_year"));
            student.setClassNum(rSet.getString("class_num"));
            
            // 重要：Test.javaに作成した setStudent メソッドを呼び出す
            test.setStudent(student);

            list.add(test);
        }
        return list;
    }

    /**
     * 成績情報のリストを一括保存（登録または更新）する
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
            // MERGE文：既存データがあればUPDATE、なければINSERTを行う
            String sql = "merge into test key(student_no, subject_cd, school_cd, no) " +
                         "values(?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSubjectCd());
            statement.setString(3, test.getSchool().getSchoolCd());
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