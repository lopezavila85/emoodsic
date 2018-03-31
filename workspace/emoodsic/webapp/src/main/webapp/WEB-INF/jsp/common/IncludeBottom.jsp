</div>

<div id="footer">
    <table style="width:100%; height: 10%;">
        <tbody>
        <tr>
            <td style="width:31%;">
                <span style="margin-left:10%;">© 2015 Emoodsic</span></td>
            <td style="width:25%;">                
                <a href="http://www.echonest.com" target="_blank">
                <img src="img/echonest.png" alt="EchoNest" title="EchoNest" width="150" height="40" ></a>
            </td>
            <td style="width:28%;">                
                <a href="http://www.unir.net/" target="_blank">
                <img src="img/unir.png" alt="UNIR" title="UNIR" width="180" height="100" ></a>
            </td>
            <td style="width:16%;margin-left:0%;">
            	<div id="languages">
            	    <%--
            		<ul id="languageList">
				        <li> <s:a id="en" cssClass="lang">English</s:a> </li>
				        <li> <s:a id="es" cssClass="lang">Español</s:a> </li>
				    </ul>			includeParams="all" value="">	    
				     --%>

					<s:url id="localeEN" namespace="/" action="locale">
				        <s:param name="request_locale" >en</s:param>
				    </s:url>
				    <s:url id="localeES" namespace="/" action="locale">
				        <s:param name="request_locale" >es</s:param>
				    </s:url>
		    
                    <%-- Stackoverflow: i18n-on-dropdown-menu-using-sselect-tag --%>
<%--					
					<s:url id="localeEN" includeParams="all" value="">
				        <s:param name="request_locale" >en</s:param>
				    </s:url>
				    <s:url id="localeES" includeParams="all" value="">
				        <s:param name="request_locale" >es</s:param>
				    </s:url>
--%>
				    <ul id="languageList">
				        <li> <s:a href="%{localeES}">
				            <img src="img/spanish.png" alt="Español" title="Español" width="32" height="32" ></s:a> </li>
				    	<li> <s:a href="%{localeEN}">
				            <img src="img/english.png" alt="English" title="English" width="32" height="32" ></s:a> </li>
				    </ul>

    			</div>
            </td>
        </tr>
        </tbody>
    </table>
</div>

</body>
</html>