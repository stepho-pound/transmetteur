jQuery(document).ready(function () {
    $('#searchNewsForm').submit(function (e) {
        e.preventDefault();
        $.ajax({
            url : $('#searchNewsForm').attr('action'),
            type : 'post',
            data : $('#searchNewsForm').serialize(),
            beforeSend: function () {
                spinnerSearch(true);
              },
              complete: function () {
                spinnerSearch(false);
              },
            success : function(response) {
                if ($(response).find('.has-error').length) {
                    $('#searchNewsForm').replaceWith(response);
                }
                else{
                    $("#newsResultsBlock").replaceWith(response);
                }
            },
            error: function (xhr) {
                alert(xhr.responseJSON["message"]);
              }
        });

    });
});

function saveNews() {
    var jsonData = { 
        "author" : event.target.parentNode.getAttribute("author"),
        "source" : event.target.parentNode.getAttribute("source"),
        "title" : event.target.parentNode.getAttribute("title"),
        "url" : event.target.parentNode.getAttribute("url"),
        "urlToImage" : event.target.parentNode.getAttribute("urlToImage"),
        "publishedAt" : event.target.parentNode.getAttribute("publishedAt"),
        "content" : event.target.parentNode.getAttribute("content"),
        "searchQuery" : event.target.parentNode.getAttribute("searchQuery"),
        "description" : event.target.parentNode.getAttribute("description"),

        }
        
        $.ajax({
            url :  $('#saveNewsForm').attr('action'),
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