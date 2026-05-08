package bean;

public class Test {
	
	private String studentNo;
	
	private String subjectCd;
	
	private int no;
	
	private int point;
	
	private String classNum;
	
	private School school;

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getSubjectCd() {
		return subjectCd;
	}

	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	
	// Test.java の中に追加

	private Student student; // Studentオブジェクトを保持するフィールド

	// これが「窓口」になるメソッド
	public void setStudent(Student student) {
	    this.student = student;
	}

	// JSPで表示するためにゲッターも必要
	public Student getStudent() {
	    return student;
	}
}