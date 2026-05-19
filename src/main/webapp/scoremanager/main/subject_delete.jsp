<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
 
    <c:param name="content">
        <section class="me-4">
            <%-- 2. 画面タイトル (h2) --%>
            <h2 class="h3 pt-3 pb-2 border-bottom">科目情報削除</h2>
            
            <%-- 3. 確認メッセージ --%>
            <div class="my-3">
                <p>「${subject.subjectName}(${subject.subjectCd})」を削除してもよろしいですか？</p>
            </div>
 
            <%-- 4. 削除実行用のフォーム --%>
            <form action="SubjectDeleteExecute.action" method="post">
                <%-- 画面設計書(No.5, 6)に基づき、hidden属性でコードと名前を送付 --%>
                <input type="hidden" name="subject_cd" value="${subject.subjectCd}">
                <input type="hidden" name="subject_name" value="${subject.subjectName}">
                
                <%-- 削除ボタン (No.3) --%>
                <div class="mt-3">
                    <input type="submit" class="btn btn-danger" value="削除">
                </div>
            </form>
 
            <%-- 5. 戻るリンク (No.4) --%>
            <div class="mt-3">
                <a href="SubjectList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
 
 