/**
 * Created by Bilawal Aziz on 30.04.18.
 */

function ImagesController() {

    var that = this;
    var generalResponse = require('./GeneralResponse');
    var request = require('request').defaults({encoding: null});
    var cloudinary = require('../core/Cloudinary');
    var users = require('../models/musikeyUserSchema');
    var images = require('../models/imagesSchema');


    

    that.download = function (req, res, next) {

        request.get(req.params.url, function (error, response, body) {
            if (!error && response.statusCode == 200) {
                data = "data:" + response.headers["content-type"] + ";base64," + new Buffer(body).toString('base64');
                console.log(data);
                return res.send(generalResponse.sendSuccessResponse(true, 200, data))
            } else {
                console.error("Error inside download ", error);
                return res.send(generalResponse.sendFailureResponse(false, 400, error))
            }
        });
    }

    that.uploadImage = function (req, res, next) {

        var useremail = req.params.email;
        var eventId =  req.params.eventId;
        var imageData = req.params.imageData ;

        console.log("ImagesController.upload() email request ", useremail);
        console.log("ImagesController.upload() eventId ", eventId);
        console.log("ImagesController.upload() image data  ", imageData);
        
        //imageData = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=="
        cloudinary.v2.uploader.upload(imageData,"n6vhv4ad", function(error, uploadedImage) {
            if (error) {
                console.log("ImagesController.upload() error ocurred", error);
                return res.send(generalResponse.sendFailureResponse("Error Occured :something went wrong while uploading", 400, null));
            } else {
                if('undefined' != uploadedImage  && null != uploadedImage) {
                    console.log('image is uploaded on cloud saving in database');
                    console.log(uploadedImage);
                    if(uploadedImage.url.length > 1 || uploadedImage.secure_url.length > 1) {
                        console.log('access url is:' + uploadedImage.url);
                        
                        images.findOneAndUpdate({"eventId":req.params.eventId, "imageSchema":{$elemMatch:{"public_id":uploadedImage.public_id}}},{'eventId':eventId, 'useremail':useremail, $push:{'imageData':imageData,"imageSchema":uploadedImage}} ,{ new: 'true', upsert:'true' }, function (err, imageDoc) {
                            console.log('image added in database successfully');
                            console.log(imageDoc);
                            if (err) {
                                console.log(err);
                                return res.send(generalResponse.sendFailureResponse("Error Occured While inserting image in database", 400, err));
                            }
                            return res.send(generalResponse.sendSuccessResponse("image is uploaded successfully", 200, imageDoc));
                        });

                    }
                }
            }

        });

    }

    that.uploadFile = function (req, res, next) {

        var useremail = req.params.email;
        var eventId =  req.params.eventId;
        var fileData = "data:raw;base64,"+req.params.fileData;
        var public_id = eventId+"__"+req.params.fileName;

        console.log("ImagesController.upload() email request ", useremail);
        console.log("ImagesController.upload() eventId ", eventId);
        console.log("ImagesController.upload() public_id   ", public_id);
        console.log("ImagesController.upload() file data  ", fileData);
        
        
        //fileData = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=="
        cloudinary.v2.uploader.upload(fileData, "n6vhv4ad", { "resource_type":"video", "public_id":public_id,"folder":"mp3"}, function(error, uploadedFile) {
            console.log("uploadedFile is ::::",uploadedFile)
            console.log("error is ::::",error)
            return res.send(generalResponse.sendFailureResponse("Error Occured :something went wrong while uploading", 400, uploadedFile));
            if (error) {
                console.log("ImagesController.upload() error ocurred", error);
                console.log("errormessage is ::::",error.message)
                console.log("errormessage is ::::",error.http_code)
                return res.send(generalResponse.sendFailureResponse("Error Occured :something went wrong while uploading", 400, null));
            } else {
                
                if('undefined' != uploadedFile  && null != uploadedFile) {
                    console.log('image is uploaded on cloud saving in database');
                    console.log(uploadedFile);
                    if(uploadedFile.url.length > 1 || uploadedFile.secure_url.length > 1) {
                        console.log('access url is:' + uploadedFile.url);
                        
                        images.findOneAndUpdate({"eventId":req.params.eventId, "imageSchema":{$elemMatch:{"public_id":uploadedFile.public_id}}},{'eventId':eventId, 'useremail':useremail, $push:{'imageData':imageData,"imageSchema":uploadedFile}} ,{ new: 'true', upsert:'true' }, function (err, imageDoc) {
                            console.log('image added in database successfully');
                            console.log(imageDoc);
                            if (err) {
                                console.log(err);
                                return res.send(generalResponse.sendFailureResponse("Error Occured While inserting image in database", 400, err));
                            }
                            return res.send(generalResponse.sendSuccessResponse("image is uploaded successfully", 200, imageDoc));
                        });

                    }
                }
            }

        });

    }


    return that;

};

module.exports = new ImagesController();
