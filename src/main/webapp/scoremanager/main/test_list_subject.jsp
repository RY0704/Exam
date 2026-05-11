<table class="table table-hover mt-4">
    <thead>
        <tr class="border-bottom border-danger border-2">
            <th>入学年度</th>
            <th>クラス</th>
            <th>学籍番号</th>
            <th>氏名</th>
            <th>1回</th>
            <th>2回</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="t" items="${tests}">
            <tr>
                <td>${t.entYear}</td>
                <td>${t.classNum}</td>
                <td>${t.studentNo}</td>
                <td>${t.studentName}</td>
                <%-- pointsマップから回数をキーにして点数を取り出す --%>
                <td>
                    <c:choose>
                        <c:when test="${not empty t.points[1]}">${t.points[1]}</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty t.points[2]}">${t.points[2]}</c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>