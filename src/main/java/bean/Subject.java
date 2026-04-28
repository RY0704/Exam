package bean;

import java.io.Serializable;

public class Subject implements Serializable {

	private String cd;
	private String name;
	private School school;

	public Subject() {
	}

	public String getSubjectCd() {
		return cd;
	}

	public void setSubjectCd(String cd) {
		this.cd = cd;
	}

	public String getSubjectName() {
		return name;
	}

	public void setSubjectName(String name) {
		this.name = name;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}