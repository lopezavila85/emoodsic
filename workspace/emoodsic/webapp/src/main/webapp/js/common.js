
function enableLanguageSwitch() {
    $(".lang").click(function() {
        var id = $(this).attr("id");
        $.ajax({
            type: "POST",
            url: "locale.action?request_locale=" + id,
            cache: false,
            success: function(){
                window.location.href='';
            }
        });
        return false;
    });
}


/* RESULT NOTIFICATIONS */

function notifySuccess(title, text) {
	PNotify.prototype.options.styling = "jqueryui";
	
	new PNotify({
		title : title,
		text : text,
	    type: 'success',
	    icon: 'ui-icon ui-icon-flag',
	    hide : false
	}); 
}

function notifyError(errorTitle, errorText) {
	PNotify.prototype.options.styling = "jqueryui";
	
	new PNotify({
		title : errorTitle,
		text : errorText,
	    type: 'error',
	    icon: 'ui-icon ui-icon-signal-diag',
	    hide:false
	});
}

function notifyInfo(infoTitle, infoText) {
	PNotify.prototype.options.styling = "jqueryui";
	
	new PNotify({
	    title: infoTitle,
	    text: infoText,
	    type: 'info',
	    hide: false
	});
}


