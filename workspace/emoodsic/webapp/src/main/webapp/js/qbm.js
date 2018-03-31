
var showCreateSuccess = false;

$(document).ready(function() {	
	
	var label1 = $("#hiddenQbmCreateOk")[0].value;
	var label2 = $("#hiddenQbmScoreOk")[0].value;
	
	var created = sessionStorage.getItem('showCreateSuccess');
	var scored = sessionStorage.getItem('showScoreSuccess');
	
	if (created != undefined) {		
		notifySuccess("Emoodsic", label1);		
	
	} else if (scored != undefined) {		
		notifySuccess("Emoodsic", label2);		
	}

	sessionStorage.removeItem('showCreateSuccess');
	sessionStorage.removeItem('showScoreSuccess');
	setTimeout("hideDialogs()", 200);
	
});		

function hideDialogs() {		
	
	$('#qbmPlaylistDialog').dialog('close');
	$('#qbmScoreDialog').dialog('close');
}

//CREATE PLAYLIST
function createQbmPlaylist() {  	
	    
	var label1 = $("#hiddenQbmCreateDialog1")[0].value;
	var label2 = $("#hiddenQbmCreateDialog2")[0].value;
	
	$('#qbmPlaylistDialog').html(label1 + ' ' 
	    + $("#initialMoodCat option:selected" ).text()
	     + ' ' + label2  + ' '
	    + $("#finalMoodCat option:selected" ).text()
	    + '?');    		
	$('#qbmPlaylistDialog').dialog();   	
}

function qbmCreateCancel() {

	$('#qbmPlaylistDialog').dialog('close');	
}

function qbmCreateOk() {
	
	$('#qbmPlaylistDialog').dialog('close');
	
	var label1 = $("#hiddenQbmCreateInfo")[0].value;
	var label3 = $("#hiddenQbmCreateError1")[0].value;
	var label4 = $("#hiddenQbmCreateError2")[0].value;
	
	notifyInfo("Emoodsic", label1);
	
	$.getJSON('/emoodsic/generateQbmPlaylist.action',
	    {
		    initialMoodCat : $("#initialMoodCat").val(),
		    finalMoodCat: $("#finalMoodCat").val()
		},
		function(response) {
			
			if (response.newQbmPlaylistCreated) {
				//console.log("QbmPlaylist created OK!");
				//window.top.location.href = "./jsp/qbm.jsp";
				sessionStorage.setItem('showCreateSuccess', 'true');
				location.reload();
			} else {
				notifyError("Error", label3)
			}
		})			
		.fail(function() {			
			notifyError("Error", label4)
		}
	);
}

function musicBrainzArtistFormatter(cellValue, options, rowObject) {
	
	if (rowObject.gidArtistMb == null) {
		return rowObject.artist;
	}
    return "<a target='_blank' href='http://musicbrainz.org/artist/" + rowObject.gidArtistMb
        + "' >" + rowObject.artist +"</a>";
}  

function musicBrainzRecordingFormatter(cellValue, options, rowObject) {
	
	if (rowObject.gidRecordingMb == null) {
		return rowObject.song;
	}
    return "<a target='_blank' href='http://musicbrainz.org/recording/" + rowObject.gidRecordingMb
        + "' >" + rowObject.song +"</a>";
}

function loadVideo(anchor) {
	
	var source = "http://www.youtube.com/embed/" + anchor.id;
	$('#youtubePlayer').attr('src', source);
	return false;
}

function youtubeVideoFormatter(cellValue, options, rowObject) {
	
	if (rowObject.youtubeVideoId == null) {
		return "<a target='_blank' href='http://www.youtube.com/>Buscar</a>";
		
	}	
	
	//Without setting the id it does not work.
	return "<a href='' id='" + rowObject.youtubeVideoId
	    + "' onclick='return loadVideo(this); return false;'>Play</a>";
}

//SCORE PLAYLIST
function scoreQbmPlaylist() {
	
	var label1 = $("#hiddenQbmScoreDialog1")[0].value;
	var label2 = $("#hiddenQbmScoreDialog2")[0].value;
	
	$('#qbmScoreDialog').html(label1 + ' '
	    + $("#initialMoodName" ).html()
	    + ' ' + label2 + ' '
	    + $("#finalMoodName" ).html()
	    + '?');    		
	$('#qbmScoreDialog').dialog(); 
}

function qbmScoreCancel() {
	$('#qbmScoreDialog').dialog('close');	
}

function qbmScoreOk() {
	$('#qbmScoreDialog').dialog('close');
	
	var label3 = $("#hiddenQbmScoreError1")[0].value;
	var label4 = $("#hiddenQbmScoreError2")[0].value;
	
	$.getJSON('/emoodsic/qbmPlaylistScore.action',
	    {
		},
		function(response) {
			
			if (response.playlistScoredOk) {
				//console.log("QbmPlaylist scored OK!");
				//window.top.location.href = "./jsp/qbm.jsp";
				sessionStorage.setItem('showScoreSuccess', 'true');
				location.reload();				
			} else {
				notifyError("Error", label3)
			}
		})			
		.fail(function() {			
			notifyError("Error", label4)
		}
	);
}


