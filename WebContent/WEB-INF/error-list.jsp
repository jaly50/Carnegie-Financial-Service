<!--
 * 08-600
 * Homework #9
 * Jiali Chen
 * andrewID: jialic
 * Dec 4, 2014
 *
-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="error" items="${errors}">
	<h3 style="color: red">${error}</h3>
</c:forEach>


