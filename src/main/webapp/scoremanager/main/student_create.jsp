<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="content">
		<section class="me-4">
			<%-- No.1 画面タイトル --%>
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

			<form action="StudentCreateExecute.action" method="post">
				<div class="container-fluid">
				<c:if test="${not empty errors}">
		            <div class="row mb-3">
		                <div class="col-12 px-4">
		                    <div class="alert alert-danger">${errors}</div>
		                </div>
		            </div>
		        </c:if>
					<%-- No.2,3 入学年度 --%>
					<div class="row mb-3">
						<div class="col-12 px-4">
							<label class="form-label">入学年度</label>
							<select class="form-select" name="ent_year" required>
								<option value="">--------</option>
								<c:forEach var="year" items="${ent_year_set}">
									<option value="${year}">${year}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<%-- No.4,5 学生番号 --%>
					<div class="row mb-3">
						<div class="col-12 px-4">
							<label class="form-label">学生番号</label>
							<input class="form-control" type="text" name="no" value="${no}" 
								   maxlength="10" placeholder="学生番号を入力してください" required>
						</div>
					</div>

					<%-- No.6,7 氏名 --%>
					<div class="row mb-3">
						<div class="col-12 px-4">
							<label class="form-label">氏名</label>
							<input class="form-control" type="text" name="name" value="${name}" 
								   maxlength="30" placeholder="氏名を入力してください" required>
						</div>
					</div>

					<%-- No.8,9 クラス --%>
					<div class="row mb-3">
						<div class="col-12 px-4">
							<label class="form-label">クラス</label>
							<select class="form-select" name="class_num" required>
								<c:forEach var="num" items="${class_num_set}">
									<option value="${num}">${num}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<%-- No.10 登録して終了ボタン --%>
					<div class="row mt-4">
						<div class="col-12 px-4">
							<button type="submit" name="end" class="btn btn-secondary">登録して終了</button>
						</div>
					</div>

					<%-- No.11 戻るリンク --%>
					<div class="row mt-3">
						<div class="col-12 px-4">
							<a href="StudentList.action" class="text-decoration-none">戻る</a>
						</div>
					</div>
				</div>
			</form>
		</section>
	</c:param>
</c:import>
<%--	value="${no}" の効果は、**「入力ミスのあとの再入力の手間を省くこと」**	--%>