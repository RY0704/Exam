package bean;

import java.util.Map;
 
public class TestListSubject {
 
    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    private Map<Integer, Integer> points;

    // --- JSP表示用の拡張メソッド ---
    
    /**
     * 1回目の点数を返します
     * JSPから ${test.point1} で呼び出せます
     */
    public Integer getPoint1() {
        if (points != null && points.containsKey(1)) {
            return points.get(1);
        }
        return null;
    }

    /**
     * 2回目の点数を返します
     * JSPから ${test.point2} で呼び出せます
     */
    public Integer getPoint2() {
        if (points != null && points.containsKey(2)) {
            return points.get(2);
        }
        return null;
    }

    // --- 標準のゲッター・セッター ---

    public int getEntYear() {
        return entYear;
    }
 
    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }
 
    public String getStudentNo() {
        return studentNo;
    }
 
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
 
    public String getStudentName() {
        return studentName;
    }
 
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
 
    public String getClassNum() {
        return classNum;
    }
 
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
 
    public Map<Integer, Integer> getPoints() {
        return points;
    }
 
    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }
}