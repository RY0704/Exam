package bean;

import java.util.Map;
 
public class TestListSubject {
 
    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    
    // ★修正ポイント：キーの型を Integer から String に変更しています
    private Map<String, Integer> points;

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
 
    // ★修正ポイント：戻り値の型を Map<String, Integer> に変更
    public Map<String, Integer> getPoints() {
        return points;
    }
 
    // ★修正ポイント：引数の型を Map<String, Integer> に変更
    public void setPoints(Map<String, Integer> points) {
        this.points = points;
    }
}