<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            
            <form action="TestRegist.action" method="get" class="mb-4">
                <div class="row g-3 align-items-center">
                    <%-- ② 入学年度 --%>
                    <div class="col-auto">
                        <label>入学年度</label>
                        <select name="f1" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%-- ③ クラス --%>
                    <div class="col-auto">
                        <label>クラス</label>
                        <select name="f2" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="c_num" items="${class_num_set}">
                                <option value="${c_num}" <c:if test="${c_num == f2}">selected</c:if>>${c_num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%-- ④ 科目 --%>
                    <div class="col-auto">
                        <label>科目</label>
                        <select name="f3" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="sub" items="${subjects}">
                                <option value="${sub.cd}" <c:if test="${sub.cd == f3}">selected</c:if>>${sub.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%-- ⑤ 回数 --%>
                    <div class="col-auto">
                        <label>回数</label>
                        <select name="f4" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="i" begin="1" end="2">
                                <option value="${i}" <c:if test="${i == f4}">selected</c:if>>${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <%-- ⑩ 検索ボタン --%>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary mt-4">検索</button>
                    </div>
                </div>
            </form>

            <%-- 成績一覧の表示テーブル --%>
            <c:if test="${not empty tests}">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学籍番号</th>
                            <th>氏名</th>
                            <th>点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${tests}">
                            <tr>
                                <td>${t.student.entYear}</td>
                                <td>${t.student.classNum}</td>
                                <td>${t.student.no}</td>
                                <td>${t.student.name}</td>
                                <td>${t.point}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </section>
    </c:param>
</c:import>