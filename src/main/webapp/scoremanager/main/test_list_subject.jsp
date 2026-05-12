<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧（科目）</h2>
            
            <div class="card p-4 mb-4 shadow-sm">
                <%-- ① 科目情報検索フォーム --%>
                <form action="TestListSubjectExecute.action" method="get" class="row g-3 align-items-end mb-3 border-bottom pb-3">
                    <div class="col-auto">
                        <small class="text-muted d-block">科目情報</small>
                    </div>
                    <div class="col-auto">
                        <label class="form-label">入学年度</label>
                        <select name="f1" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <label class="form-label">クラス</label>
                        <select name="f2" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="c_num" items="${class_num_set}">
                                <option value="${c_num}" <c:if test="${c_num == f2}">selected</c:if>>${c_num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <label class="form-label">科目</label>
                        <select name="f3" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="sub" items="${subjects}">
                                <option value="${sub.subjectCd}" <c:if test="${sub.subjectCd == f3}">selected</c:if>>${sub.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="hidden" name="f" value="sj">
                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>
                </form>

                <%-- ② 学生情報検索フォーム（追加部分） --%>
                <form action="TestListStudentExecute.action" method="get" class="row g-3 align-items-end">
                    <div class="col-auto">
                        <small class="text-muted d-block">学生情報</small>
                    </div>
                    <div class="col-md-4">
                        <label class="form-label">学生番号</label>
                        <input type="text" name="f4" class="form-control" placeholder="学生番号を入力してください" value="${f4}">
                    </div>
                    <input type="hidden" name="f" value="st">
                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>
                </form>
            </div>
 
            <%-- 科目検索結果の表示エリア --%>
            <c:if test="${not empty tests}">
                <div class="mt-4">
                    <h5 class="mb-3">科目：${selected_subject_name}</h5>
                    <table class="table table-hover border-top">
                        <thead class="table-light">
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学籍番号</th>
                                <th>氏名</th>
                                <th>1回</th>
                                <th>2回</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="test" items="${tests}">
                                <tr>
                                    <td>${test.entYear}</td>
                                    <td>${test.classNum}</td>
                                    <td>${test.studentNo}</td>
                                    <td>${test.studentName}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty test.point1}">${test.point1}</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty test.point2}">${test.point2}</c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            
            <%-- 検索結果が空の場合 --%>
            <c:if test="${empty tests && not empty f1 && f1 != 0}">
                <p class="mt-3">学生情報が存在しませんでした。</p>
            </c:if>
        </section>
    </c:param>
</c:import>