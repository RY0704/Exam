package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    /**
     * クラス図の指定: baseSql
     * 科目別成績一覧を取得するSQL（学生テーブルとテストテーブルを結合）
     */
    private String baseSql = 
        "SELECT st.ent_year, st.class_num, st.no AS student_no, st.name AS student_name, t.no, t.point " +
        "FROM student st " +
        "JOIN test t ON st.no = t.student_no " +
        "WHERE st.ent_year = ? AND st.class_num = ? AND t.subject_cd = ? AND st.school_cd = ?";

    /**
     * クラス図の指定: postFilter
     * ResultSetからTestListSubjectリストへの変換処理
     */
    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        // 学生番号をキーにして、作成済みのBeanを一時保存するマップ
        Map<String, TestListSubject> map = new HashMap<>();

        try {
            while (rSet.next()) {
                String studentNo = rSet.getString("student_no");
                TestListSubject item;

                if (map.containsKey(studentNo)) {
                    // すでにマップにある（2回目以降の点数の）場合、既存のBeanを取り出す
                    item = map.get(studentNo);
                } else {
                    // 初めて登場する学生の場合、新しくBeanを作る
                    item = new TestListSubject();
                    item.setEntYear(rSet.getInt("ent_year"));
                    item.setClassNum(rSet.getString("class_num"));
                    item.setStudentNo(studentNo);
                    item.setStudentName(rSet.getString("student_name"));
                    
                    // 点数用のマップを初期化してセット
                    item.setPoints(new HashMap<Integer, Integer>());
                    
                    map.put(studentNo, item);
                }

                // 今回の行の「回数」と「点数」を、Bean内のMapに追加
                int no = rSet.getInt("no");
                int point = rSet.getInt("point");
                item.getPoints().put(no, point);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        // Mapに溜まったBeanをListに変換して返す
        return new ArrayList<>(map.values());
    }

    /**
     * クラス図の指定: filter
     * 入学年度、クラス、科目、学校を条件に成績一覧を取得する
     */
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(baseSql);
            // 引数の順番通りに値をセット
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getSubjectCd()); // Subject Beanのコード取得メソッド
            statement.setString(4, school.getSchoolCd());  // School Beanのコード取得メソッド

            rSet = statement.executeQuery();
            // クラス図の通り postFilter を呼び出し
            list = postFilter(rSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            // リソースの解放
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }
}