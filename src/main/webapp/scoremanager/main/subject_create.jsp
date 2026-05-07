<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">
		<section class="me-4">
			<%-- No.1 画面タイトル --%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>

			<form action="SubjectCreateExecute.action" method="post">
				<div class="container-fluid">
				<c:if test="${not empty errors}">
		            <div class="row mb-3">
		                <div class="col-12 px-4">
		                    <div class="alert alert-danger">${errors}</div>
		                </div>
		            </div>
		        </c:if>


					<%-- 科目コード --%>
					<div class="row mb-3">
						<div class="col-12 px-4">
							<label class="form-label">科目コード</label>
							<input class="form-control" type="text" name="cd" value="${cd}" 
								   maxlength="3" placeholder="科目コードを入力してください" required>
						</div>
					</div>

					<%-- 科目名 --%>
					<div class="row mb-3">
						<div class="col-12 px-4">
							<label class="form-label">科目名</label>
							<input class="form-control" type="text" name="name" value="${name}" 
								   maxlength="20" placeholder="科目名を入力してください" required>
						</div>
					</div>


					<%-- 登録ボタン --%>
					<div class="row mt-4">
						<div class="col-12 px-4">
							<button type="submit" name="end" class="btn btn-secondary">登録</button>
						</div>
					</div>

					<%-- 戻るリンク --%>
					<div class="row mt-3">
						<div class="col-12 px-4">
							<a href="SubjecctList.action" class="text-decoration-none">戻る</a>
						</div>
					</div>
				</div>
			</form>
		</section>
	</c:param>
</c:import>