<%-- 成績削除完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">
        得点管理システム
    </c:param>

    <c:param name="content">
        <div id="wrap_box">
            <%-- ヘッダー部分は登録完了と統一 --%>
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">成績削除情報</h2>
            
            <div id="wrap_box">
                <%-- メッセージ部分：削除なので、少し落ち着いた背景色、または警告色の薄い版に --%>
                <p class="text-center" style="background-color:#e9ecef; padding: 10px;">削除が完了しました</p>

                <br>
                <br>
                <br>
                <%-- リンク部分：全角スペースではなく、Bootstrapのクラスやマージンを使うとより綺麗です --%>
                <div class="text-center">
                    <a href="TestList.action" class="btn btn-secondary">成績一覧に戻る</a>
                </div>
            </div>
        </div>
    </c:param>
</c:import>