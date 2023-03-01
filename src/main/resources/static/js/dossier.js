function bascule(id) 
{ 
	if (document.getElementById(id).style.display == "none")
			document.getElementById(id).style.display = "flex"; 
	else	document.getElementById(id).style.display = "none"; 
} 

$('#formNouveauDossier').submit(function (e) {
    e.preventDefault();
    $.ajax({
        url :  $('#formNouveauDossier').attr('action'),
        type : 'post',
        data :  $('#formNouveauDossier').serialize(),
        success : function(response) {
            if ($(response).find('.has-error').length) {
                $('#formNouveauDossier').replaceWith(response);
            }
            else{
                location.reload();
            }
        },
        error: function (xhr) {
            alert(xhr.responseJSON["message"]);
          }
    });
});

$('#formModifDossier').submit(function (e) {
    e.preventDefault();
    $.ajax({
        url :  $('#formModifDossier').attr('action'),
        type : 'post',
        data :  $('#formModifDossier').serialize(),
        success : function(response) {
            if ($(response).find('.has-error').length) {
                $('#formModifDossier').replaceWith(response);
            }
            else{
				$("#dossierRecapBlock").replaceWith(response);
            }
        },
        error: function (xhr) {
            alert(xhr.responseJSON["message"]);
          }
    });
});

$('#formDeleteDossier').submit(function (e) {
    e.preventDefault();
    $.ajax({
        url :  $('#formDeleteDossier').attr('action'),
        type : 'post',
        data :  $('#formDeleteDossier').serialize(),
        success : function(response) {
            if ($(response).find('.has-error').length) {
                $('#formDeleteDossier').replaceWith(response);
            }
            else{
				location = "/espace"
            }
        },
        error: function (xhr) {
            alert(xhr.responseJSON["message"]);
          }
    });
});


function deleteNewsFromDossier() {
    var dossierId = document.getElementById("dataRecapBlock").getAttribute("dossierid");
    var newsId = event.target.parentNode.getAttribute("newsId");
    $.ajax({
        url :  "/dossier/deleteNewsFromDossier/" + dossierId + "/" + newsId,
        type : 'DELETE',
        success : function(xhr) {
            if ($(xhr).find('.has-error').length) {
                $('#formDeleteNews').replaceWith(response);
            }
            else{
                $("#myNewsBlock").replaceWith($($.parseHTML(xhr)).get(29))
                $("#dataRecapBlock").replaceWith($($($($.parseHTML(xhr)).get(25)).html()).get(4))

            }
        },
        error: function (xhr) {
            if(xhr.status == 200){
                $("#dossierRecapBlock").replaceWith(response);

            }else{
                alert(xhr.status);
            }
          }
    });
}

function deleteVideoFromDossier() {
    var dossierId = document.getElementById("dataRecapBlock").getAttribute("dossierid");
    var videoId = event.target.parentNode.getAttribute("videoId");
    $.ajax({
        url :  "/dossier/deleteVideoFromDossier/" + dossierId + "/" + videoId,
        type : 'DELETE',
        success : function(xhr) {
            if ($(xhr).find('.has-error').length) {
                $('#formDeleteVideo').replaceWith(response);
            }
            else{
                $("#myVideoBlock").replaceWith($($.parseHTML(xhr)).get(31))
                $("#dataRecapBlock").replaceWith($($($($.parseHTML(xhr)).get(25)).html()).get(4))

            }
        },
        error: function (xhr) {
            if(xhr.status == 200){
                $("#dossierRecapBlock").replaceWith(response);

            }else{
                alert(xhr.status);
            }
          }
    });
}
