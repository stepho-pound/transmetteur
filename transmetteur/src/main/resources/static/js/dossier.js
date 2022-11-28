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