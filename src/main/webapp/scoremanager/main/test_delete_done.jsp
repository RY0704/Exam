<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 pt-3 pb-2 border-bottom">成績情報削除完了</h2>
            <div class="my-3">
                <p>削除が完了しました。</p>
            </div>
            <a href="TestList.action" class="btn btn-primary">一覧へ戻る</a>
        </section>
    </c:param>
</c:import>	