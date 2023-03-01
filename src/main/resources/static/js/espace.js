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




function displayDossier() {

    let id = event.currentTarget.attributes['id'].nodeValue
    let cible = document.querySelector("button[id='"+id+"']")
    if (cible.nextElementSibling.style.display == "none")
    cible.nextElementSibling.style.display = "grid"; 
	else	cible.nextElementSibling.style.display = "none"; 
}

function addToDossier() {
   
        
    let dossierId = event.currentTarget.attributes['dossierid'].nodeValue
    let elementToAddId = event.currentTarget.parentElement.previousElementSibling.attributes['id'].nodeValue
    let elementType = event.currentTarget.parentElement.previousElementSibling.attributes['data-type'].nodeValue
    let url = ''

    if(elementType == 'news'){
        url = '/espace/addNewsToDossier'
        var jsonData = { 
            "newsId" : elementToAddId,
            "dossierId" : dossierId
    
            }
    }
    else if (elementType == 'video'){
        url = '/espace/addVideoToDossier'
        var jsonData = { 
            "videoId" : elementToAddId,
            "dossierId" : dossierId            }
    }
    $.ajax({
        url :  url,
        type : 'POST',
        data : jsonData,
        success : function(xhr) {
            if ($(xhr).find('.has-error').length) {
            }
            else{
                $("#myVideosBlock").replaceWith($($.parseHTML(xhr)).get(31))
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

