<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>
	
	<c:param name="content">
    <section class="me-4">
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

        <%-- 全体を囲むフォーム --%>
        <form action="TestRegist.action" method="get">
            <div class="container-fluid border rounded p-4">
                
                <%-- 科目情報エリア --%>
                <div class="row align-items-end mb-4">
                    <div class="col-md-2">
                        <p class="fw-bold m-0 mt-1">科目情報</p>
                    </div>
                    <%-- 入学年度 --%>
                    <div class="col-2">
                        <label class="form-label" for="student-f1-select">入学年度</label>
                        <select class="form-select" id="student-f1-select" name="f1">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%-- クラス --%>
                    <div class="col-2">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="f2">
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%-- 科目 --%>
                    <div class="col-4">
                        <label class="form-label" for="subject-f3-select">科目</label>
                        <select class="form-select" id="subject-f3-select" name="f3">
                            <option value="0">--------</option>
                           <c:forEach var="subject" items="${subjects}">
							    <%-- .cd ではなく .subjectCd にする --%>
								<option value="${subject.subjectCd}" <c:if test="${subject.subjectCd==f3}">selected</c:if>>
								        ${subject.subjectName} <%-- .name ではなく .subjectName にする --%>
							    </option>
							</c:forEach>
                        </select>
                    </div>
                    <%-- 検索ボタン --%>
                    <div class="col-2">
                        <button type="submit" class="btn btn-secondary w-100">検索</button>
                    </div>
                </div>

                <hr> <%-- 区切り線 --%>

                <%-- 学生情報エリア --%>
                <div class="row align-items-end mb-3">
                    <div class="col-md-2">
                        <p class="fw-bold m-0 mt-1">学生情報</p>
                    </div>
                    <div class="col-4">
                        <label class="form-label">学生番号</label>
                        <input class="form-control" type="text" name="f4" value="${f4}" 
                               maxlength="10" placeholder="学生番号を入力してください">
                    </div>
                    <div class="col-2">
                        <button type="submit" class="btn btn-secondary w-100">検索</button>
                    </div>
                </div>

            </div>
            
            <%-- 注意書きメッセージ --%>
            <div class="mt-3 text-info small">
                科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
            </div>
        </form>
    </section>
    </c:param>
</c:import>

