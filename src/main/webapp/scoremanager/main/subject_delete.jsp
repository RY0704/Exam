<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>得点管理システム</title>
</head>
<body>
    <section>
        <h2>科目情報削除</h2>
        <p>科目情報を削除します。よろしいですか？</p>
        
        <div>
            科目コード：${subject.cd}<br>
            科目名：${subject.name}
        </div>

        <form action="SubjectDeleteExecute.action" method="post">
            <input type="hidden" name="cd" value="${subject.cd}">
            <input type="submit" value="削除">
        </form>

        <a href="SubjectList.action">戻る</a>
    </section>
</body>
</html>