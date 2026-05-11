<%-- 学生別成績一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
    <section class="me-4">
        <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧(学生)</h2>

        <form action="TestListStudentExecuteAction.action" method="get">
            <input type="hidden" id="search-mode" name="f" value="">
            <div class="container-fluid border rounded p-2">
                <%-- 科目情報エリア --%>
                <div class="row align-items-end mb-3">
                    <div class="col-md-2"><p class="fw-bold m-2 mt-1">科目情報</p></div>
                    <div class="col-2">
                        <label class="form-label">入学年度</label>
                        <select class="form-select" name="f1">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label">クラス</label>
                        <select class="form-select" name="f2">
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-4">
                        <label class="form-label">科目</label>
                        <select class="form-select" name="f3">
                            <option value="0">--------</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.subjectCd}" <c:if test="${subject.subjectCd==f3}">selected</c:if>>${subject.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-2">
                        <button type="submit" class="btn btn-secondary w-75" onclick="document.getElementById('search-mode').value='sj'">検索</button>
                    </div>
                </div>

                <hr>

                <%-- 学生情報エリア --%>
                <div class="row align-items-end mb-3">
                    <div class="col-md-2"><p class="fw-bold m-2 mt-1">学生情報</p></div>
                    <div class="col-4">
                        <label class="form-label">学生番号</label>
                        <input class="form-control" type="text" name="f4" value="${f4}" maxlength="10" placeholder="学生番号を入力してください">
                    </div>
                    <div class="col-2">
                        <button type="submit" class="btn btn-secondary w-75" onclick="document.getElementById('search-mode').value='st'">検索</button>
                    </div>
                </div>
            </div>
        </form>

        <%-- 1: 氏名の表示 --%>
        <c:if test="${not empty tests}">
            <div class="mt-4 mb-2">
                <%-- 学生氏名とかっこ書きした学生番号を表示 --%>
                <h3 class="h5 fw-bold">${tests[0].studentName}（${tests[0].studentNo}）</h3>
            </div>

            <%-- 2: 成績一覧テーブル --%>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>科目名</th>
                        <th>科目コード</th>
                        <th>回数</th>
                        <th>点数</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="t" items="${tests}">
                        <tr>
                            <%-- Beanのプロパティ名を指定 --%>
                            <td>${t.subjectName}</td>
                            <td>${t.subjectCd}</td>
                            <td>${t.no}</td>
                            <td>${t.point}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <div class="mt-3 text-info small">
            科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
        </div>
    </section>
    </c:param>
</c:import>