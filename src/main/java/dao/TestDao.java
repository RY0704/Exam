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
     * 指定された条件で1件の成績情報を取得する
     */
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // 単一取得でもLEFT JOINを使用し、学生情報が必ず取れるようにします
            String sql = "select s.student_no, s.student_name, s.ent_year, s.class_num as student_class_num, " +
                         "t.point, t.no, t.subject_cd, t.class_num as test_class_num " +
                         "from student s " +
                         "left join test t on s.student_no = t.student_no " +
                         "and t.subject_cd = ? and t.no = ? and t.school_cd = ? " +
                         "where s.student_no = ? and s.school_cd = ?";
            
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getSubjectCd());
            statement.setInt(2, no);
            statement.setString(3, school.getSchoolCd());
            statement.setString(4, student.getStudentNo());
            statement.setString(5, school.getSchoolCd());
            
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
     * 検索条件に合致する成績一覧を取得する
     * テストデータがなくても、該当クラスの学生全員を表示します
     */
    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // 【重要】STUDENTを主役にしてTESTをLEFT JOIN
            String sql = "select s.student_no, s.student_name, s.ent_year, s.class_num as student_class_num, " +
                         "t.point, t.no, t.subject_cd, t.class_num as test_class_num " +
                         "from student s " +
                         "left join test t on s.student_no = t.student_no " +
                         "and t.subject_cd = ? and t.no = ? and t.school_cd = ? " +
                         "where s.ent_year = ? and s.class_num = ? and s.school_cd = ? and s.is_attend = true " +
                         "order by s.student_no asc";

            statement = connection.prepareStatement(sql);
            // LEFT JOINの結合条件用 (? 1〜3)
            statement.setString(1, subject.getSubjectCd());
            statement.setInt(2, num);
            statement.setString(3, school.getSchoolCd());
            // 学生の絞り込み用 (? 4〜6)
            statement.setInt(4, entYear);
            statement.setString(5, classNum);
            statement.setString(6, school.getSchoolCd());

            ResultSet rSet = statement.executeQuery();
            list = postFilter(rSet, school);

            // リストが空でない場合、すべての要素に検索条件の科目をセット（JSP表示用）
            for (Test t : list) {
                t.setSubjectCd(subject.getSubjectCd());
                t.setNo(num);
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    /**
     * ResultSetからTestオブジェクトに詰め替える
     */
    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        while (rSet.next()) {
            Test test = new Test();
            // 学籍番号は学生テーブルから取得
            test.setStudentNo(rSet.getString("student_no"));
            
            // テストデータがある場合はその値を、ない場合は初期値をセット
            test.setPoint(rSet.getInt("point")); // データがない(NULL)場合は0が返る
            
            // クラス番号は、テストデータがあればそれを、なければ学生の所属クラスを使用
            String classNum = rSet.getString("test_class_num");
            if (classNum == null) {
                classNum = rSet.getString("student_class_num");
            }
            test.setClassNum(classNum);
            test.setSchool(school);

            // 学生情報をセット
            Student student = new Student();
            student.setStudentNo(rSet.getString("student_no"));
            student.setStudentName(rSet.getString("student_name"));
            student.setEntYear(rSet.getInt("ent_year"));
            test.setStudent(student);

            list.add(test);
        }
        return list;
    }

    /**
     * 成績情報のリストを一括保存する
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
            String sql = "merge into test (student_no, school_cd, subject_cd, no, point, class_num) " +
                         "key(student_no, school_cd, subject_cd, no) " +
                         "values(?, ?, ?, ?, ?, ?)";
            
            statement = connection.prepareStatement(sql);
            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSchool().getSchoolCd());
            statement.setString(3, test.getSubjectCd());
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