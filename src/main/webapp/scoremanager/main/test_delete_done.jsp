<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 pt-3 pb-2 border-bottom">成績情報削除完了</h2>
            <div class="my-3">
                <p>削除が完了しました。</p>
            </div>
            <a href="TestRegist.action?f1=${f1}&f2=${f2}&f3=${f3}&f4=${f4}" class="btn btn-primary">一覧へ戻る</a>
        </section>
    </c:param>
</c:import>