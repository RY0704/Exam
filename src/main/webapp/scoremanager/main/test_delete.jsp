<%-- 中略（c:importなどはそのまま） --%>
        <section class="me-4">
            <h2 class="h3 pt-3 pb-2 border-bottom">成績情報削除</h2>
            
            <div class="my-3">
                <p>
                    学生番号：${test.studentNo}<br>
                    科目コード：${test.subjectCd}<br>
                    回数：${test.no}回目
                </p>
                <p>の成績情報を削除してもよろしいですか？</p>
            </div>

            <form action="TestDeleteExecute.action" method="post">
                <%-- name属性を Action の getParameter と合わせます --%>
                <input type="hidden" name="student_cd" value="${test.studentNo}">
                <input type="hidden" name="subject_cd" value="${test.subjectCd}">
                <input type="hidden" name="no" value="${test.no}">
                
                <div class="mt-3">
                    <input type="submit" class="btn btn-danger" value="削除">
                </div>
            </form>
<%-- 後略 --%>