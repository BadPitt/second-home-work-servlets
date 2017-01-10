<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
<spring:url value="/resources/styles.css" var="myStyles" />
<spring:url value="/resources/styles/material.min.css" var="materialStyles" />
<spring:url value="/resources/styles/material.min.js" var="materialJS" />
<link rel="stylesheet" href="${myStyles}">
<link rel="stylesheet" href="${materialStyles}"/>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<script type="text/javascript" src="${materialJS}"></script>
