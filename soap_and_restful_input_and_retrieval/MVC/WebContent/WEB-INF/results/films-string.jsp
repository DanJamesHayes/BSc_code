<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<% out.print("ID#Title#Year#Director#Stars#Review"); %>
<c:forEach items="${films}" var="film">
	<% out.println(); %>
	${film.id}#${film.title}#${film.year}#${film.director}#${film.stars}#${film.review}
</c:forEach>