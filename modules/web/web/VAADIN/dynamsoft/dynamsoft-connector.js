kz_almanit_jcrm_web_screens_application_docattached_DocAttachedEdit = function () {
//https://usermanual.wiki/Document/Dynamic20Web20TWAIN20Developers20Guide.1198514479/html - manual
    var connector = this;
    var element = connector.getElement();
    element.innerHTML = "<div id=\"dwtcontrolContainer\"></div>";
    var DWObject;
    //картинки будут храниться тут
    var images = [];
    this.acquireImage = function () {
        DWObject = Dynamsoft.DWT.GetWebTwain('dwtcontrolContainer');
        if(DWObject == null){
            alert("Не установлена программа DynamicWebTWAIN");
            return;
        }
        DWObject.RegisterEvent("OnPostTransfer", this.DynamsoftOnPostTransfer);
        DWObject.RegisterEvent("OnPostAllTransfers", this.DynamsoftOnPostAllTransfers);
        DWObject.SelectSource(function() {
            //сбрасываем значение для повторных сканов
                images = [];
                DWObject.OpenSource();
                DWObject.AcquireImage();
            },
            function () {
                console.log("SelectSource failed");
            }
        )
    };

    //событие вызывается после сканирования каждого файла
    this.DynamsoftOnPostTransfer = function(){
        addImage();
    }

    //событие вызывается после сканирование всех файлов
    this.DynamsoftOnPostAllTransfers = function(){
        uploadAsBase64();
    }

    var addImage = function(){
        if (DWObject) {
            if (DWObject.HowManyImagesInBuffer === 0) {
                console.log("There is no image to upload!");
                return;
            }
            DWObject.SelectAllImages();
            DWObject.SelectedImagesCount = 1;
            DWObject.GetSelectedImagesSize(Dynamsoft.DWT.EnumDWT_ImageType.IT_PNG);
            var imageData = DWObject.SaveSelectedImagesToBase64Binary();
            images.push(imageData);
            console.log(images);
            DWObject.RemoveAllImages();
        }
    }

    var uploadAsBase64 = function() {
        if (DWObject) {
            console.log(connector.getState());
            var imageData = '';
            if(images.length == 0){
                return;
            }
            var data = {};
            data['base64File'] = images;
            data['docAttachedId'] = connector.getState().data.docAttachedId;
            data['extension'] = 'png';
            var url = getDynamsoftRequestUrl();
            $.ajax({
                type: 'POST',
                dataType:"json",
                contentType:"application/json; charset=utf-8",
                url: url,
                data: JSON.stringify(data),
                success: function(msg){
                    console.log(msg);
                },
                error: function(response){
                    console.log(response);
                }
            });
        }
    }

    var getDynamsoftRequestUrl = function() {
        var url = Dynamsoft.Lib.detect.ssl ? "https://" : "http://";
        url += location.hostname;
        var path = location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1);
        url += location.port === "" ? path : ":" + location.port + path;
        url += "rest/v2/services/jcrm_DynamsoftScanService/uploadScannedImage";
        return url;
    }
};