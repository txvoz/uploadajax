function upload(url, file, success, error) {
    var url = "/upload";
    var data = new FormData();
    data.append('uploaded_file', file);
    //data.append('name', "nuevo_nombre");
    console.log(file);
    $.ajax({
        url: url,
        data: data,
        cache: false,
        contentType: false,
        //contentType: 'multipart/form-data',
        processData: false,
        type: 'POST',
        dataType : "html",
        success: function (data) {
            success(data);
        }
    });
}

$(function () {
    $("#frmArchivo").submit(function (e) {
        var fnExito = function (r) {
            alert("Si");
        };
        upload("/upload", $('input[type=file]')[0].files, fnExito);
        return false;
    });
});