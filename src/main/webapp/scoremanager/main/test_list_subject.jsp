<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>
            
            <div class="border rounded p-4 mb-4">
                <%-- 1. 科目情報検索フォーム --%>
                <form action="TestListSubjectExecute.action" method="get" class="mb-4">
                    <div class="row g-3 align-items-center">
                        <div class="col-auto" style="width: 100px;">科目情報</div>
                        <div class="col-auto">
                            <label>入学年度</label>
                            <select name="f1" class="form-select">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <label>クラス</label>
                            <select name="f2" class="form-select">
                                <option value="0">--------</option>
                                <c:forEach var="c_num" items="${class_num_set}">
                                    <option value="${c_num}" <c:if test="${c_num == f2}">selected</c:if>>${c_num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <label>科目</label>
                            <select name="f3" class="form-select">
                                <option value="0">--------</option>
                                <c:forEach var="sub" items="${subjects}">
                                    <option value="${sub.subjectCd}" <c:if test="${sub.subjectCd == f3}">selected</c:if>>${sub.subjectName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-secondary mt-4">検索</button>
                        </div>
                    </div>
                </form>

                <hr>

                <%-- 2. 学生情報検索フォーム（★ここを追加） --%>
                <form action="TestListStudentExecute.action" method="get">
                    <div class="row g-3 align-items-center">
                        <div class="col-auto" style="width: 100px;">学生情報</div>
                        <div class="col-auto">
                            <label>学生番号</label>
                            <input type="text" name="f4" class="form-control" placeholder="学生番号を入力してください" value="${f4}">
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-secondary mt-4">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <%-- エラーメッセージ表示位置 --%>
            <c:if test="${not empty errors}">
                <div class="mb-2">
                    <p style="color: red; font-weight: bold; margin: 0;">${errors}</p>
                </div>
            </c:if>

            <%-- 青いガイドメッセージ --%>
            <div class="mb-4" style="color: #00bfff;">
                科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
            </div>

            <%-- 成績一覧表示エリア --%>
            <c:if test="${not empty tests}">
                <h3 class="h4">科目：${selected_subject_name}</h3>
                <table class="table table-hover mt-3">
                    <thead>
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
                        <c:forEach var="t" items="${tests}">
                            <tr>
                                <td>${t.entYear}</td>
                                <td>${t.classNum}</td>
                                <td>${t.studentNo}</td>
                                <td>${t.studentName}</td>
                                <td>${t.points[1]}</td>
                                <td>${t.points[2]}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </section>
    </c:param>
</c:import>