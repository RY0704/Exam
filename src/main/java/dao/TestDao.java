package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	 
	public Test get(String studentNo, String subjectCd, School school, int no) throws Exception {
		
		Test test = null;
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			String sql = "select * from test where student_no=? and subject_cd=? and school_cd=? and no=?";
		    statement = connection.prepareStatement(sql);
			statement.setString(1, studentNo);
			statement.setString(2, subjectCd);
			statement.setString(3, school.getSchoolCd());
			statement.setInt(4, no);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				
				test = new Test();
				test.setStudentNo(resultSet.getString("student_no"));
			    test.setSubjectCd(resultSet.getString("subject_cd"));
			    test.setClassNum(resultSet.getString("class_num"));
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				test.setSchool(school);
			} else {
				//データがなければnullを返す
				test = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

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

	    List<Test> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        String sql =
	            "select t.* " +
	            "from test t " +
	            "join student s on t.student_no = s.student_no " +
	            "and t.school_cd = s.school_cd " +
	            "where s.ent_year = ? " +
	            "and s.class_num = ? " +
	            "and t.subject_cd = ? " +
	            "and t.no = ? " +
	            "and t.school_cd = ? " +
	            "and s.is_attend = true " +
	            "order by t.student_no";

	        statement = connection.prepareStatement(sql);
	        statement.setInt(1, entYear);
	        statement.setString(2, classNum);
	        statement.setString(3, subject.getSubjectCd());
	        statement.setInt(4, num);
	        statement.setString(5, school.getSchoolCd());

	        resultSet = statement.executeQuery();

	        // ResultSet → List<Test>
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

	        if (result) {
	            connection.commit();
	        } else {
	            connection.rollback();
	        }

	    } catch (Exception e) {
	        if (connection != null) {
	            connection.rollback();
	        }
	        throw e;
	    } finally {
	        if (connection != null) {
	            connection.close();
	        }
	    }

	    return result;
	}
	public boolean save(Test test, Connection connection) throws Exception {

	    PreparedStatement statement = null;
	    ResultSet resultSet = null;

	    try {
	        // ① 既存データ確認
	        String checkSql =
	            "select count(*) from test " +
	            "where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";

	        statement = connection.prepareStatement(checkSql);
	        statement.setString(1, test.getStudentNo());
	        statement.setString(2, test.getSubjectCd());
	        statement.setString(3, test.getSchool().getSchoolCd());
	        statement.setInt(4, test.getNo());

	        resultSet = statement.executeQuery();
	        resultSet.next();

	        boolean exists = resultSet.getInt(1) > 0;

	        statement.close();

	        // ② UPDATE または INSERT
	        if (exists) {
	            String updateSql =
	                "update test set point = ?, class_num = ? " +
	                "where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";

	            statement = connection.prepareStatement(updateSql);
	            statement.setInt(1, test.getPoint());
	            statement.setString(2, test.getClassNum());
	            statement.setString(3, test.getStudentNo());
	            statement.setString(4, test.getSubjectCd());
	            statement.setString(5, test.getSchool().getSchoolCd());
	            statement.setInt(6, test.getNo());

	        } else {
	            String insertSql =
	                "insert into test(student_no, subject_cd, school_cd, no, point, class_num) " +
	                "values(?,?,?,?,?,?)";

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