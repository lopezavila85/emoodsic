<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Emoodsic</title>
<link rel="stylesheet" type="text/css" href="css/common.css">

<sj:head jqueryui="true" loadAtOnce="true" compressed="false" loadFromGoogle="false"
    jquerytheme="flick" debug="true" />

<script type="text/javascript" src="js/main.js"></script>

</head>

<body>
<div id="Content">
    <div id="navigation">
		<div id="logo">
			<h2>Emoodsic</h2>
		</div>
		
		<s:url var="qbmurl" action="queryByMood"/>
		<s:url var="logouturl" action="logout"/>
		<%-- <s:hidden id="hiddenQbmText" value="%{getText('qbmLabel')}" /> --%>
		
		<div style="text-align: right;">
			<p><s:property value="greetings"/></p>
			<s:a href="%{logouturl}" cssStyle="float:right; margin-right:1%;">
			    <s:text name="logoutSession" />
			</s:a>
		</div>
		
		<div id="qbmDiv" style="margin-left:5%;margin-top:1%;">
		    <%--
		    <input type = "button" value = "Consulta por Estado de Ánimo" 
		       onclick = "javascript:location.href='queryByMood.action';" />
		     --%>   
		     <s:a id="qbmActionButton" href="%{qbmurl}"
		         cssClass="ui-button ui-widget ui-state-default ui-corner-all"
		         cssStyle="float:left;margin-top:0.5%;padding: .4em 1em;">
		         <s:text name="qbmLabel" />
		     </s:a>
		     <br><br><br>
		     <p class="sectionText">
		         <s:text name="qbmDescription1" />		     
		     </p>
		     <p class="sectionText">
		         <s:text name="qbmDescription2" />		      
		     </p>
		     <table style="width:66%; margin-left:10%;color: #333;text-align:center;">
			  <tr>
				  <td><s:text name="Angry" /></td>
				  <td><s:text name="Excited" /></td>
				  <td><s:text name="Happy" /></td>
			  </tr>
			  <tr>
			      <td><s:text name="Nervous" /></td>
			      <td><s:text name="Calm" /></td>
			      <td><s:text name="Pleased" /></td>
			  </tr>
			  <tr>
			      <td><s:text name="Bored" /></td>
			      <td></td>
			      <td><s:text name="Relaxed" /></td>
			  </tr>
			  <tr>
			      <td><s:text name="Sad" /></td>
			      <td><s:text name="Sleepy" /></td>
			      <td><s:text name="Peaceful" /></td>
			  </tr>
			</table>
		</div>
    </div>

    <%@ include file="/WEB-INF/jsp/common/IncludeBottom.jsp"%>
