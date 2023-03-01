$('#searchVideoForm').submit(function (e) {
    e.preventDefault();
    $.ajax({
        url :  $('#searchVideoForm').attr('action'),
        type : 'post',
        data :  $('#searchVideoForm').serialize(),
        beforeSend: function () {
            spinnerSearch(true);
          },
          complete: function () {
            spinnerSearch(false);
          },
        success : function(response) {
            if ($(response).find('.has-error').length) {
                $('#searchVideoForm').replaceWith(response);
            }
            else{
                $("#videoResultsBlock").replaceWith(response);
            }
        },
        error: function (xhr) {
            alert(xhr.responseJSON["message"]);
          }
    });
});

function searchByChannel() {
    var channelId = event.currentTarget.attributes[3].nodeValue;
    

    $.ajax({
        url :  $('#searchVideoChannelForm').attr('action'),
        type : 'POST',
        data : {"channelId" : channelId},
        beforeSend: function () {
            spinnerSave(true);
          },
          complete: function () {
            spinnerSave(false);
          },
          success : function(response) {
            if ($(response).find('.has-error').length) {
                $('#searchVideoChannelForm').replaceWith(response);
            }
            else{
                $("#videoResultsBlock").replaceWith(response);
            }
        },
        error: function (xhr) {
          }
    });
}


function saveVideo() {
    var jsonData = { 
        "author" : event.target.parentNode.getAttribute("author"),
        "name" : event.target.parentNode.getAttribute("name"),
        "iframe" : event.target.parentNode.getAttribute("iframe"),
        "url" : event.target.parentNode.getAttribute("url"),
        "datePublication" : event.target.parentNode.getAttribute("datePublication"),
        "channelId" : event.target.parentNode.getAttribute("channelId"),
        "searchQuery" : event.target.parentNode.getAttribute("searchQuery"),
        "image" : event.target.parentNode.getAttribute("image"),

        }
        
        $.ajax({
            url :  $('#saveVideoForm').attr('action'),
            type : 'POST',
            data :  jsonData,
            beforeSend: function () {
                spinnerSave(true);
              },
              complete: function () {
                spinnerSave(false);
              },
            success : function(response) {
                if ($(response).find('.has-error').length) {
                    $('#saveVideoForm').replaceWith(response);
                }
                else{
                }
            },
            error: function (xhr) {
              }
        });
}
function spinnerSearch(state) {

    if (state == true)
        document.getElementsByClassName("loaderRecherche")[0].style.display = "block";
    else if (state == false)
    document.getElementsByClassName("loaderRecherche")[0].style.display = "none";

}


function spinnerSave(state) {

    if (state == true){
        event.target.parentNode.parentNode.parentNode.getElementsByClassName("loaderSauvegarde")[0].style.display = "block";
        frag = event.target.parentNode.parentNode.parentNode;
    }   
    else if (state == false)
        frag.getElementsByClassName("loaderSauvegarde")[0].style.display = "none";

}
