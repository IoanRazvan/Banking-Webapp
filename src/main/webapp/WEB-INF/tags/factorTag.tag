<%@ tag body-content="empty" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="sourceCurrency"    rtexprvalue="true"  required="true" type="java.lang.String"  description="Currency of the Source Account" %>
<%@ attribute name="targetCurrency"  rtexprvalue="true"  required="true" type="java.lang.String"  description="Currency of the Target Account" %>
<c:choose>
    <c:when test="${sourceCurrency == targetCurrency}">
        <c:set var="factor" value="${1.0}"></c:set>
    </c:when>
    <c:when test="${targetCurrency == 'ron'}">
        <c:choose>
            <c:when test="${sourceCurrency == 'dollar'}">
                <c:set var="factor" value="${4.43}"></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="factor" value="${4.84}"></c:set>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:when test="${targetCurrency == 'euro'}">
        <c:choose>
            <c:when test="${sourceCurrency == 'dollar'}">
                <c:set var="factor" value="${1/1.09}"></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="factor" value="${1/4.84}"></c:set>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <c:choose>
            <c:when test="${sourceCurrency == 'euro'}">
                <c:set var="factor" value="${1.09}"></c:set>
            </c:when>
            <c:otherwise>
                <c:set var="factor" value="${1/4.43}"></c:set>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>
<c:out value="${factor}"></c:out>