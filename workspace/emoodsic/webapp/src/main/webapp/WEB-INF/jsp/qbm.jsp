<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Emoodsic</title>
<link rel="stylesheet" type="text/css" href="css/common.css">
<link rel="stylesheet" type="text/css" href="css/pnotify.custom.min.css" />
<link rel="stylesheet" type="text/css" href="css/qbm.css">

<sj:head jqueryui="true" loadAtOnce="true" compressed="false" loadFromGoogle="false"
    jquerytheme="flick" debug="true" />

<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/pnotify.custom.min.js"></script>
<script type="text/javascript" src="js/qbm.js"></script>

</head>

<body>
<div id="Content">
    <div id="navigation">
		<div id="logo">
			<h2><s:text name="qbmTitle"/></h2>
		</div>
		
		<%-- URLs --%>
		<s:url var="homeurl" action="home"/>
		<s:url var="logouturl" action="logout"/>
		<s:url var="moodcaturl" action="jsonMoodCategory"/>
		<s:url var="qbmplaylistloadurl" action="qbmPlaylistLoad" />
		<s:url var="scoresongurl" action="qbmPlaylistScoreSong" />
		
		<%-- HIDDEN TEXTS i18n --%>
		<s:hidden id="hiddenQbmCreateDialog1" value="%{getText('qbm.create.dialog1')}" />
		<s:hidden id="hiddenQbmCreateDialog2" value="%{getText('qbm.create.dialog2')}" />
		<s:hidden id="hiddenQbmCreateInfo" value="%{getText('qbm.create.info')}" />
		<s:hidden id="hiddenQbmCreateOk" value="%{getText('qbm.create.ok')}" />
		<s:hidden id="hiddenQbmCreateError1" value="%{getText('qbm.create.error1')}" />
		<s:hidden id="hiddenQbmCreateError2" value="%{getText('qbm.create.error2')}" />
		<s:hidden id="hiddenQbmScoreDialog1" value="%{getText('qbm.score.dialog1')}" />
		<s:hidden id="hiddenQbmScoreDialog2" value="%{getText('qbm.score.dialog2')}" />
		<s:hidden id="hiddenQbmScoreOk" value="%{getText('qbm.score.ok')}" />
		<s:hidden id="hiddenQbmScoreError1" value="%{getText('qbm.score.error1')}" />
		<s:hidden id="hiddenQbmScoreError2" value="%{getText('qbm.score.error2')}" />
		
		<s:if test="hasActionErrors()">
			<div class="errorMessage">
				<s:actionerror />
			</div>
		</s:if>
		<s:if test="hasActionMessages()">
			<div class="errorMessage">
				<s:actionmessage />
			</div>
		</s:if>
		
		<s:set name="isQbmPlaylistReady" value="qbmPlaylistReady" />
	
	    <sj:dialog id="qbmPlaylistDialog" cssClass="qbmDialog" title="%{getText('qbm.create')}"
        	 modal="true" buttons="{ 'OK':function() { qbmCreateOk(); },
                'Cancel':function() { qbmCreateCancel(); }
            }">
    	</sj:dialog>

	    <sj:dialog id="qbmScoreDialog" cssClass="qbmDialog" title="%{getText('qbm.score')}"
        	 modal="true" buttons="{ 'OK':function() { qbmScoreOk(); },
                 'Cancel':function() { qbmScoreCancel(); }
            }">
    	</sj:dialog>    			
			
		<div style="text-align: right;margin-bottom:2%;">
			<p><s:property value="greetings"/></p>
			<s:a href="%{logouturl}" cssStyle="float:right; margin-right:1%;"><s:text name="logoutSession"/></s:a>
			<s:a href="%{homeurl}" cssStyle="float:right; margin-right:1%;"><s:text name="homeLink"/></s:a>
		</div>
		
		<s:if test="%{#isQbmPlaylistReady == true}">
			<div id="qbmSelection">
			    <s:form id="formQbmPlaylist" theme="simple">	
		            <p><b><s:text name="qbm.create.instructions"/></b></p>
					<p><s:text name="qbm.current"/>
					<s:select				       
				        id="initialMoodCat"
				        name="initialMoodCat"
				        list="moodCategoryList"
				        listKey="idMoodCategory"
				        listValue="%{getText(name)}" />
				    
				    <span style="margin-left:2%;"></span>
				    <s:text name="qbm.desired"/> 
				    <s:select				       
				        id="finalMoodCat"
				        name="finalMoodCat"
				        list="moodCategoryList"
				        listKey="idMoodCategory"
				        listValue="%{getText(name)}"/>
				        
				    <sj:submit 
		            	id="formQbmSubmit"
		            	targets="formResult" 
		            	value="%{getText('qbm.create')}" 
		            	indicator="indicator"
		            	button="true"
		            	onclick="createQbmPlaylist()"
		            	cssStyle="margin-left:2%;"/>

		        </s:form>
			</div>
		</s:if>
		<s:else>	
		    <h3 class="sectionTitle"><s:label id="initialMoodName" name="initialMoodName"/>
		     -> <s:label id="finalMoodName" name="finalMoodName" /></h3>	
		    
		    <table style="width:100%">
  			<tr>
  			<td>
		    <div style="margin-left: 5%;margin-top:2%; width: 100%;float: left;margin-right: 4%;">
		    <sjg:grid id="qbmGrid" dataType="json" href="%{qbmplaylistloadurl}"
		        autowidth="true" shrinkToFit="true"
		        caption="%{getText('grid.caption')}"
		        hidegrid="false"
		        gridModel="qbmPlaylistSongs"
                rowNum="10"
                rownumbers="true" 
                cellurl="%{scoresongurl}"
                cellEdit="true"
		    >
			    <sjg:gridColumn name="idQbmPlaylistInfo" index="idQbmPlaylistInfo" key="true" 
			        title="idQbmPlaylistInfo" formatter="integer" hidden="true"
			        editable="false"/>
			    <sjg:gridColumn name="artist" index="artist" title="%{getText('qbm.grid.artist')}"			    
			       align="center" width="20" formatter="musicBrainzArtistFormatter"
			       editable="false"/>
			    <sjg:gridColumn name="song" index="song" title="%{getText('qbm.grid.song')}"
			        align="center" width="20" formatter="musicBrainzRecordingFormatter" 
			        editable="false"/>
			    <sjg:gridColumn name="youtubeVideoId" index="youtubeVideoId" title="YouTube"
			        align="center" width="10" formatter="youtubeVideoFormatter"
			        editable="false"/>
			    <sjg:gridColumn name="country" index="country" title="%{getText('qbm.grid.country')}"
			        align="center" width="15"
			        editable="false"/>
			    <sjg:gridColumn name="likertScore" index="likertScore" title="%{getText('qbm.grid.score')}" formatter="integer"
			        align="center" width="10"
			        editable="true"
			        edittype="select"
			        editrules="{number:true, required: true, minValue:0, maxValue:5}"
			        editoptions="{value:'0:0;1:1;2:2;3:3;4:4;5:5'}"/>	    
		    </sjg:grid>		    
		    </div>
			</td>
			<td>
		    <div style="float: left;margin-top:4%;">		    	    
			    <iframe id="youtubePlayer" width="320" height="240" src="about:blank">
				</iframe>
		    </div>
			</td>
			</tr>		
		    
		    <tr>
		    <td>
		    <div>
		    	<p style="float:left;margin-left: 3%;margin-right:22.5%">
		    	<s:text name="qbm.score.instructions"/></p>
		    	
    			<sj:submit 
	            	id="scoreSubmit"
	            	targets="" 
	            	value="%{getText('qbm.score')}" 
	            	indicator="indicator"
	            	button="true"
	            	onclick="scoreQbmPlaylist()"
	            	cssStyle="float:left;margin-top:0.5%;"/>
		    </div>
		    </td>
		    </tr>
		    </table>
		</s:else>    
    </div>
    


    <%@ include file="/WEB-INF/jsp/common/IncludeBottom.jsp"%>