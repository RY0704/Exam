<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
            
            <div class="px-4">
                <p class="mb-4">科目情報を削除します。よろしいですか？</p>
                
                <div class="mb-4 p-3 border rounded bg-light">
                    <strong>科目コード：</strong> ${subject.subjectCd}<br>
                    <strong>科目名：</strong> ${subject.subjectName}
                </div>

                <form action="SubjectDeleteExecute.action" method="post" class="d-inline">
                    <%-- 削除実行に必要なコードをhiddenで送信 --%>
                    <input type="hidden" name="cd" value="${subject.subjectCd}">
                    <button type="submit" class="btn btn-danger">削除</button>
                </form>

                <a href="SubjectList.action" class="btn btn-link">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>