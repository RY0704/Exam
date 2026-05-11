<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績削除確認</h2>
            <div class="px-4 mb-4">
                <p class="text-danger fw-bold">以下の成績情報を削除します。よろしいですか？</p>
            </div>
            
            <table class="table table-bordered mx-4" style="width: auto;">
                <tr>
                    <th class="bg-light" style="width: 150px;">学籍番号</th>
                    <td>${test.student.studentNo}</td>
                </tr>
                <tr>
                    <th class="bg-light">氏名</th>
                    <td>${test.student.studentName}</td>
                </tr>
                <tr>
                    <th class="bg-light">科目コード</th>
                    <td>${test.subjectCd}</td>
                </tr>
                <tr>
                    <th class="bg-light">回数</th>
                    <td>${test.no}</td>
                </tr>
                <tr>
                    <th class="bg-light">点数</th>
                    <td>${test.point}</td>
                </tr>
            </table>

            <form action="TestDelete.action" method="post" class="px-4 mt-4">
                <%-- 削除実行に必要なデータ (Javaの stNum, cd, no に合わせる) --%>
                <input type="hidden" name="stNum" value="${test.student.studentNo}">
                <input type="hidden" name="cd" value="${test.subjectCd}">
                <input type="hidden" name="no" value="${test.no}">
                
                <%-- 検索状態を維持するためのパラメータ --%>
                <input type="hidden" name="f1" value="${f1}">
                <input type="hidden" name="f2" value="${f2}">
                <input type="hidden" name="f3" value="${f3}">
                <input type="hidden" name="f4" value="${f4}">
                
                <%-- 削除実行フラグ --%>
                <input type="hidden" name="execute" value="true">

                <button type="submit" class="btn btn-danger">削除を実行する</button>
                <%-- TestList ではなく TestRegist に戻る --%>
                <a href="TestRegist.action?f1=${f1}&f2=${f2}&f3=${f3}&f4=${f4}" class="btn btn-outline-secondary ms-2">キャンセル</a>
            </form>
        </section>
    </c:param>
</c:import>