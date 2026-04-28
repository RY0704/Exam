package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    /**
     * 基本となるSQL文
     */
    private String baseSql = "select * from student where school_cd = ?";

    /**
     * ResultSetからStudentリストへの変換処理（共通処理）
     */
    private List<TestListStudent> postFilter(ResultSet resultSet) throws Exception {
        // リストを初期化
        List<TestListStudent> list = new ArrayList<>();
        try {
            // リザルトセットを全件走査
            while (resultSet.next()) {
                // 学生インスタンスを初期化
                Student student = new Student();
                // 学生インスタンスに検索結果をセット
                student.setStudentNo(resultSet.getString("student_no"));
                student.setStudentName(resultSet.getString("student_name"));
                student.setEntYear(resultSet.getInt("ent_year"));
                student.setClassNum(resultSet.getString("class_num"));
                student.setAttend(resultSet.getBoolean("is_attend"));
             
                // リストに追加
               list.add(student);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 学生情報の絞り込み検索
     */
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
        // リストを初期化
        List<Student> list = new ArrayList<>();
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet resultSet = null;
        
        // SQL文の組み立て
        String condition = " and ent_year = ? and class_num = ?";
        String order = " order by student_no asc";
        String conditionIsAttend = "";

        // 在学フラグがtrueの場合、条件を追加
        if (isAttend) {
            conditionIsAttend = " and is_attend = true";
        }

        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
            
            // パラメータをバインド
            statement.setString(1, school.getSchoolCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            
            // SQLを実行
            resultSet = statement.executeQuery();
            
            // リストへの変換処理を実行
            list = postFilter(resultSet, school);
            
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
        return list;
    }
}