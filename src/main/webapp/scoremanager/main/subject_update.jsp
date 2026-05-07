<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">
		<section class="me-4">
			<%-- No.1 画面タイトル --%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

			<%-- 中略（タイトル部分など） --%>

			<form action="SubjectUpdateExecute.action" method="post"> <%-- 更新用のActionへ --%>
			    <div class="container-fluid">
			
			        <div class="row mb-3">
			            <div class="col-12 px-4">
			                <label class="form-label">科目コード</label>
			                <%-- 条件追加: readonly --%>
			                <input class="form-control" type="text" name="cd" value="${subject.subjectCd}" readonly>
			            </div>
			        </div>
			
			
			      
			        <div class="row mb-3">
			            <div class="col-12 px-4">
			                <label class="form-label">科目名</label>
			                <%-- 条件変更: maxlength="20", 条件追加: required --%>
			                <input class="form-control" type="text" name="name" value="${subject.subjectName}" 
			                       maxlength="20" placeholder="科目名を入力してください" required>
			            </div>
			        </div>
			
			        
			        
			        <%-- 変更ボタン --%>
			        <div class="row mt-4">
			            <div class="col-12 px-4">
			                <button type="submit" class="btn btn-primary">変更</button>
			            </div>
			        </div>

			</form>
<%-- 以下、戻るリンクなど略 --%>			
		</section>
		<a href="SubjectList.action">戻る</a>
	</c:param>
</c:import>