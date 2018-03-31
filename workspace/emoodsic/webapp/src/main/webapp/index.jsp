<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Emoodsic</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/common.css">
<sj:head jqueryui="true" loadAtOnce="true" compressed="false" loadFromGoogle="false" jquerytheme="flick" debug="true" />

</head>

<body>
<div id="Content">
    <div id="title">
  	    <h1><s:text name="loginTitle" /></h1>
	</div> 
    
    <div id="instructions">
  		<p><s:text name="loginInstructions"></s:text></p>
   </div>
   
    <div id="login-form-container">
		<s:actionerror />
		<s:form id="login-form" action="authentication" method="post" cssStyle="vertical-align:middle;">
			<s:textfield key="loginEmail" />
			<s:password key="loginPassword" />
			<s:submit key="loginSubmit" align="center"
			    cssClass="ui-button ui-widget ui-state-default ui-corner-all"
		        cssStyle="float:left;margin-top:0.5%;padding: .4em 1em;margin-left:33%;">
				<s:param name="colspan" value="%{2}" />
				<s:param name="align" value="%{'center'}" />
			</s:submit>
			<s:hidden name="wholeForm" />
		</s:form>
	</div>	
    
    <%@ include file="/WEB-INF/jsp/common/IncludeBottom.jsp"%>
