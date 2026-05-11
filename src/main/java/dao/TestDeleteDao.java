package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import bean.Test;

public class TestDeleteDao extends Dao {

    /**
     * 指定された成績情報をデータベースから削除する
     */
    public boolean delete(Test test) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // 成績を特定する4つの主キーを条件に指定
            String sql = "delete from test where student_no=? and subject_cd=? and school_cd=? and no=?";
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSubjectCd());
            statement.setString(3, test.getSchool().getSchoolCd());
            statement.setInt(4, test.getNo());

            // 実行
            count = statement.executeUpdate();
            
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        // 1件以上削除されていれば成功
        return count > 0;
    }
}