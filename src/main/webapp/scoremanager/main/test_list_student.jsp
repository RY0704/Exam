<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            
            <form action="TestList.action" method="get" class="mb-4">
                <div class="row g-3 align-items-center">
                    <div class="col-auto">
                        <label class="form-label">学籍番号</label>
                    </div>
                    <div class="col-auto">
                        <input type="text" name="f1" value="${student.no}" class="form-control" placeholder="学籍番号を入力">
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>
                </div>
            </form>

            <c:if test="${not empty errors}">
                <div class="alert alert-danger">${errors}</div>
            </c:if>

            <c:choose>
                <c:when test="${not empty tests}">
                    <div class="mb-3">
                        <strong>氏名：${student.name} (${student.no})</strong>
                    </div>
                    <table class="table table-hover">
                        <thead>
                            <tr class="table-light">
                                <th>科目名</th>
                                <th>科目コード</th>
                                <th>回数</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="test" items="${tests}">
                                <tr>
                                    <td>${test.subjectName}</td>
                                    <td>${test.subjectCd}</td>
                                    <td>${test.num}</td>
                                    <td>${test.point}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:when test="${not empty student}">
                    <div class="alert alert-warning">成績情報がありません。</div>
                </c:when>
            </c:choose>
        </section>
    </c:param>
</c:import>