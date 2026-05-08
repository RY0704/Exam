<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            
            <%-- 検索用フォーム --%>
            <form action="TestRegist.action" method="get" class="mb-4">
                <div class="row g-3 align-items-center">
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
                        <label>回数</label>
                        <select name="f4" class="form-select">
                            <option value="0">--------</option>
                            <c:forEach var="i" begin="1" end="2">
                                <option value="${i}" <c:if test="${i == f4}">selected</c:if>>${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-secondary mt-4">検索</button>
                    </div>
                </div>
            </form>

            <%-- 成績一覧・入力用フォーム --%>
            <c:if test="${not empty tests}">
                <div class="mb-3">
                    科目コード：${f3} （${f4}回目）
                </div>

                <%-- 登録・更新用のアクションへ --%>
                <form action="TestRegistExecute.action" method="post">
                    <%-- 
                        【重要】ここがポイント！
                        これらを hidden で持っておかないと、Action側で 
                        Integer.parseInt(req.getParameter("f4")) が null になりエラーになります。
                    --%>
                    <input type="hidden" name="f1" value="${f1}">
                    <input type="hidden" name="f2" value="${f2}">
                    <input type="hidden" name="f3" value="${f3}">
                    <input type="hidden" name="f4" value="${f4}">

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
                                    <td>${t.classNum}</td>
                                    <td>${t.student.studentNo}</td>
                                    <td>${t.student.studentName}</td>
                                    <td>
                                        <%-- 点数入力欄。nameは point_学籍番号 になります --%>
                                        <input type="number" name="point_${t.student.studentNo}" 
                                               value="${t.point}" class="form-control" 
                                               min="0" max="100" style="width: 100px;">
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                    <div class="mt-3">
                        <button type="submit" class="btn btn-primary">登録して終了</button>
                    </div>
                </form>
            </c:if>
            
            <%-- メッセージ表示（Actionで req.setAttribute("message", ...) した場合） --%>
            <c:if test="${not empty message}">
                <div class="alert alert-success mt-3">
                    ${message}
                </div>
            </c:if>
        </section>
    </c:param>
</c:import>