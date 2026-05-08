package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
<<<<<<< HEAD
=======
import bean.Student;
import bean.Subject;
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
import bean.Test;

public class TestDao extends Dao {

<<<<<<< HEAD
    public Test get(String studentNo, String subjectCd, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
=======
    /**
     * クラス図に基づいたベースSQL
     * studentテーブルをJOINし、名前や入学年度も取得できるようにしています
     */
    private final String baseSql = 
        "select t.*, s.student_name, s.ent_year " +
        "from test t " +
        "join student s on t.student_no = s.student_no " +
        "and t.school_cd = s.school_cd ";
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
        try {
            String sql = "select * from test where student_no=? and subject_cd=? and school_cd=? and no=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, studentNo);
            statement.setString(2, subjectCd);
            statement.setString(3, school.getSchoolCd());
            statement.setInt(4, no);
            ResultSet resultSet = statement.executeQuery();
=======
    /**
     * 指定された条件で1件の成績情報を取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
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
=======
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
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
<<<<<<< HEAD
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
=======
		return test;
	}
	private List<Test> postFilter(ResultSet resultSet, School school) throws Exception {
	    List<Test> list = new ArrayList<>();
	    try {
	        while (resultSet.next()) {
	            Test test = new Test();
	            // Test Beanのフィールドに合わせてセット
	            test.setStudentNo(resultSet.getString("student_no"));
	            test.setSubjectCd(resultSet.getString("subject_cd"));
	            test.setNo(resultSet.getInt("no"));
	            test.setPoint(resultSet.getInt("point"));
	            test.setClassNum(resultSet.getString("class_num"));
	            test.setSchool(school);
	            
	            list.add(test);
=======
            if (!list.isEmpty()) {
                test = list.get(0);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return test;
    }
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	    return list;
	}
	public List<Test> filter(
	        int entYear,
	        String classNum,
	        Subject subject,
	        int num,
	        School school
	) throws Exception {
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
=======
    /**
     * 検索条件（入学年度、クラス、科目、回数）に合致する成績一覧を取得する
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
    public List<Test> filter(int entYear, String classNum, String subjectCd, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
=======
        try {
            // SQL文：IS_ATTENDフラグも含めてチェック
            String sql = baseSql + 
                "where s.ent_year = ? and s.class_num = ? and t.subject_cd = ? " +
                "and t.no = ? and t.school_cd = ? and s.is_attend = true " +
                "order by t.student_no";
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
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
=======
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getSubjectCd());
            statement.setInt(4, num);
            statement.setString(5, school.getSchoolCd());
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subjectCd);
            statement.setInt(4, num);
            statement.setString(5, school.getSchoolCd());
=======
            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
            resultSet = statement.executeQuery();
            list = postFilter(resultSet, school);
=======
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }
=======
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
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
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
=======
            // 学生(Student) Beanを作成し、Test Beanにセットする（JSP表示用）
            Student student = new Student();
            student.setStudentNo(rSet.getString("student_no"));
            student.setStudentName(rSet.getString("student_name")); // DBのカラム名に準拠
            student.setEntYear(rSet.getInt("ent_year"));
            student.setClassNum(rSet.getString("class_num"));
            
            // 重要：Test.javaに作成した setStudent メソッドを呼び出す
            test.setStudent(student);
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
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
=======
            list.add(test);
        }
        return list;
    }
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git

<<<<<<< HEAD
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
=======
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
>>>>>>> branch 'master' of https://github.com/RY0704/Exam.git
            if (statement != null) statement.close();
        }
    }
}