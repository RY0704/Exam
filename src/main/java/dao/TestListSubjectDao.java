package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {

    private String baseSql = 
            "SELECT st.ent_year, st.class_num, st.student_no, st.student_name, t.no, t.point " +
            "FROM student st " +
            "LEFT OUTER JOIN test t ON st.student_no = t.student_no " +
            "AND t.subject_cd = ? AND st.school_cd = t.school_cd " +
            "WHERE st.ent_year = ? AND st.class_num = ? AND st.school_cd = ? " +
            "ORDER BY st.student_no ASC";

    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        // SQLのORDER BYの並び順を保持するため LinkedHashMap を使用
        Map<String, TestListSubject> map = new LinkedHashMap<>();

        try {
            while (rSet.next()) {
                String studentNo = rSet.getString("student_no");
                TestListSubject item;

                if (map.containsKey(studentNo)) {
                    item = map.get(studentNo);
                } else {
                    item = new TestListSubject();
                    item.setEntYear(rSet.getInt("ent_year"));
                    item.setClassNum(rSet.getString("class_num"));
                    item.setStudentNo(studentNo);
                    item.setStudentName(rSet.getString("student_name"));
                    
                    // ★修正ポイント1：HashMapの型を <String, Integer> に変更
                    item.setPoints(new HashMap<String, Integer>());
                    map.put(studentNo, item);
                }

                int no = rSet.getInt("no");
                if (!rSet.wasNull()) {
                    int point = rSet.getInt("point");
                    
                    // ★修正ポイント2：キー(no)を String.valueOf() で文字列に変換して格納
                    item.getPoints().put(String.valueOf(no), point);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return new ArrayList<>(map.values());
    }

    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(baseSql);
            statement.setString(1, subject.getSubjectCd()); // 科目コード
            statement.setInt(2, entYear);                   // 入学年度
            statement.setString(3, classNum);               // クラス
            statement.setString(4, school.getSchoolCd());   // 学校コード

            rSet = statement.executeQuery();
            list = postFilter(rSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }
}