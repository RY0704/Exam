package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

	/**
	 * 指定された科目コードと学校の商品インスタンスを取得する
	 * @param cd 科目コード
	 * @param school 学校
	 * @return 科目インスタンス（存在しない場合はnull）
	 * @throws Exception
	 */
	public Subject get(String cd, School school) throws Exception {
		Subject subject = null;
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			// クラス図の get(cd:String, school:School):Subject に対応
			statement = connection.prepareStatement("select * from subject where subject_cd = ? and school_cd = ?");
			statement.setString(1, cd);
			statement.setString(2, school.getSchoolCd());
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				subject = new Subject();
				subject.setSubjectCd(resultSet.getString("subject_cd"));
				subject.setSubjectName(resultSet.getString("subject_name"));
				subject.setSchool(school);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
			if (connection != null) {
				try { connection.close(); } catch (SQLException sqle) { throw sqle; }
			}
		}
		return subject;
	}

	/**
	 * 指定された学校に属する科目一覧を取得する
	 * @param school 学校
	 * @return 科目のリスト
	 * @throws Exception
	 */
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			// クラス図の filter(school:School):List<Subject> に対応
			statement = connection.prepareStatement("select * from subject where school_cd = ? order by subject_cd asc");
			statement.setString(1, school.getSchoolCd());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Subject subject = new Subject();
				subject.setSubjectCd(resultSet.getString("subject_cd"));
				subject.setSubjectName(resultSet.getString("subject_name"));
				subject.setSchool(school);
				list.add(subject);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
			if (connection != null) {
				try { connection.close(); } catch (SQLException sqle) { throw sqle; }
			}
		}
		return list;
	}

	/**
	 * 科目インスタンスをデータベースに保存する
	 * @param subject 科目
	 * @return 成功した場合はtrue
	 * @throws Exception
	 */
	public boolean save(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			// 既存のデータがあるか確認
			Subject old = get(subject.getSubjectCd(), subject.getSchool());
			if (old == null) {
				// 新規登録
				statement = connection.prepareStatement("insert into subject(subject_cd, subject_name, school_cd) values(?, ?, ?)");
				statement.setString(1, subject.getSubjectCd());
				statement.setString(2, subject.getSubjectName());
				statement.setString(3, subject.getSchool().getSchoolCd());
			} else {
				// 更新
				statement = connection.prepareStatement("update subject set subject_name = ? where subject_cd = ? and school_cd = ?");
				statement.setString(1, subject.getSubjectName());
				statement.setString(2, subject.getSubjectCd());
				statement.setString(3, subject.getSchool().getSchoolCd());
			}
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
			if (connection != null) {
				try { connection.close(); } catch (SQLException sqle) { throw sqle; }
			}
		}
		return count > 0;
	}

	/**
	 * 科目インスタンスをデータベースから削除する
	 * @param subject 科目
	 * @return 成功した場合はtrue
	 * @throws Exception
	 */
	public boolean delete(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			// クラス図の delete(subject:Subject):boolean に対応
			statement = connection.prepareStatement("delete from subject where subject_cd = ? and school_cd = ?");
			statement.setString(1, subject.getSubjectCd());
			statement.setString(2, subject.getSchool().getSchoolCd());
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
			if (connection != null) {
				try { connection.close(); } catch (SQLException sqle) { throw sqle; }
			}
		}
		return count > 0;
	}
}