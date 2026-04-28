package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    /**
     * 修正ポイント：成績一覧を取得するためのSQL（学生、科目、テストを結合）
     */
    private String baseSql = 
        "SELECT s.name AS subject_name, s.cd AS subject_cd, t.num, t.point " +
        "FROM test t " +
        "JOIN subject s ON t.subject_cd = s.cd AND t.school_cd = s.school_cd " +
        "WHERE t.student_no = ? AND t.school_cd = ?";

    /**
     * ResultSetからTestListStudentリストへの変換処理
     */
    private List<TestListStudent> postFilter(ResultSet resultSet) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                // TestListStudent（表示用Bean）にデータをセット
                TestListStudent item = new TestListStudent();
                
                item.setSubjectName(resultSet.getString("subject_name"));
                item.setSubjectCd(resultSet.getString("subject_cd"));
                item.setNum(resultSet.getInt("num"));
                item.setPoint(resultSet.getInt("point"));
             
                list.add(item); // 修正：宣言した変数itemを追加
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return list;
    }

    /**
     * 特定の学生の成績一覧を取得する
     */
    public List<TestListStudent> filter(Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // baseSqlを使用してプリペアードステートメントを作成
            statement = connection.prepareStatement(baseSql);
            // プレースホルダ(?)に値をセット
            statement.setString(1, student.getStudentNo());            
            statement.setString(2, student.getSchool().getSchoolCd());

            // SQL実行
            resultSet = statement.executeQuery();
            // リストに変換
            list = postFilter(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // 後処理（クローズ）
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }
}