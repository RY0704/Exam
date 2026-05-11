<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
 
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績情報変更</h2>
 
           <form action="TestUpdateExecute.action" method="post">
				    <%-- 検索状態の維持 --%>
				    <input type="hidden" name="f1" value="${f1}">
				    <input type="hidden" name="f2" value="${f2}">
				    <input type="hidden" name="f3" value="${f3}">
				    <input type="hidden" name="f4" value="${f4}">
				    <input type="hidden" name="class_num" value="${test.classNum}">			    
				    <input type="hidden" name="student_no" value="${test.student.studentNo}">
 
                    <%-- 入学年度 (表示のみ) --%>
                    <div class="row mb-3">
                        <div class="col-12 px-4">
                            <label class="form-label">入学年度</label>
                            <input class="form-control" type="text" value="${test.student.entYear}" readonly>
                        </div>
                    </div>
 
                    <%-- クラス (表示のみ) --%>
                    <div class="row mb-3">
                        <div class="col-12 px-4">
                            <label class="form-label">クラス</label>
                            <input class="form-control" type="text" value="${test.classNum}" readonly>
                        </div>
                    </div>
 
                    <%-- 学籍番号 (hiddenで送信＋表示はreadonly) --%>
                    <div class="row mb-3">
                        <div class="col-12 px-4">
                            <label class="form-label">学籍番号</label>
                            <input class="form-control" type="text" name="student_no" value="${test.student.studentNo}" readonly>
                        </div>
                    </div>
 
                    <%-- 氏名 (表示のみ) --%>
                    <div class="row mb-3">
                        <div class="col-12 px-4">
                            <label class="form-label">氏名</label>
                            <input class="form-control" type="text" value="${test.student.studentName}" readonly>
                        </div>
                    </div>
 
                    <%-- 点数 (編集可能) --%>
                    <div class="row mb-3">
                        <div class="col-12 px-4">
                            <label class="form-label fw-bold">点数</label>
                            <input class="form-control" type="number" name="point" value="${test.point}"
                                   min="0" max="100" required>
                            
                             <c:if test="${not empty error}">
                                <div style="color: #ffb200; font-size: 0.8em; margin-top: 5px;">
                                    ${error}
                                </div>
                             </c:if>
                        </div>
                    </div>
 
                    <%-- 変更ボタン --%>
                    <div class="row mt-4">
                        <div class="col-12 px-4">
                            <button type="submit" class="btn btn-primary">変更</button>
                        </div>
                    </div>
                </div>
            </form>
        </section>
        
        <div class="mt-3 px-4">
            <a href="TestRegist.action?f1=${f1}&f2=${f2}&f3=${f3}&f4=${f4}">戻る</a>
        </div>
    </c:param>
</c:import>
 