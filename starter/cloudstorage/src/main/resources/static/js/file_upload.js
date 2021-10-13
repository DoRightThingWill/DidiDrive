function uploadFile(e){

    console.log(e);
//    e.preventDefault();
    console.log("upload js is running");
    let formData = new FormData();
    formData.append("file",fileUpload.files[0]);




    $.ajax({
        type: "POST",
        url: "http://localhost:8080/upload",
        data: formData,
        contentType: false,
        cache: false,
        processData:false,
        success:function(resp){
            if(response.status==200){
                alert("File successfully uploaded !");
            }
        }

    })

}