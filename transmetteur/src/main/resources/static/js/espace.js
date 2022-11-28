function deleteNews() {
    var newsId = event.target.parentNode.getAttribute("newsId");
    $.ajax({
        url :  "/espace/deleteNews/" + newsId,
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

function deleteVideo() {
    var videoId = event.target.parentNode.getAttribute("videoId");
    $.ajax({
        url :  "/espace/deleteVideo/" + videoId,
        type : 'DELETE',
        success : function(xhr) {
            if ($(xhr).find('.has-error').length) {
                $('#formDeleteVideo').replaceWith(response);
            }
            else{
                $("#myVideosBlock").replaceWith($($.parseHTML(xhr)).get(31))
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




function addNewsToDossier() {
    var jsonData = { 
        "dossierId" : event.target.parentNode.getAttribute("dossierId"),
        "newsId" : event.target.parentNode.getAttribute("newsId"),

        }
        
        $.ajax({
            url :  $('#addNewsToDossierForm').attr('action'),
            type : 'POST',
            dataType:"json",
            data :  jsonData,
            beforeSend: function () {
                spinnerSave(true);
              },
              complete: function () {
                spinnerSave(false);
              },
            success : function(response) {
                if ($(response).find('.has-error').length) {
                    $('#saveNewsForm').replaceWith(response);
                }
                else{
                }
            },
            error: function (xhr) {
              }
        });
}