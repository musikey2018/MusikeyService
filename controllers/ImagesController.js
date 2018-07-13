/**
 * Created by Bilawal Aziz on 30.04.18.
 */
import fs from 'fs';

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
        cloudinary.v2.uploader.upload(imageData,"mwcar4uv", {resource_type:"auto", "upload_preset":"mwcar4uv"}, function(error, uploadedImage) {
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
        var fileData =  new Buffer(req.params.fileData).toString();
        var publicId = eventId+"__"+req.params.fileName;
        
        var uploadFile = fs.writeFile(req.params.fileName+'.mp3', fileData, {encoding: 'base64'}, function(err) {
            console.log('File created');
        });
        console.log("ImagesController.uploadFile() email request ", useremail);
        console.log("ImagesController.uploadFile() eventId ", eventId);
        console.log("ImagesController.uploadFile() public_id   ", publicId);
        console.log("ImagesController.uploadFile() file data  ", fileData);
        cloudinary.v2.uploader.upload(uploadFile,"mwcar4uv", { resource_type: "video", public_id: publicId},function(error, result) {console.log(result, error)});
        //fileData = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=="
        // cloudinary.v2.uploader.upload(fileData, "n6vhv4ad",  {"resource_type": "auto", "public_id":publicId, "type":"upload"}, function(error, result) {
        //     console.log(result, error);
        //     return res.send(generalResponse.sendFailureResponse("Error Occured :something went wrong while uploading", 400, error));
        // });

    }

    that.uploadFileRaw = function (req, res, next) {

        var useremail = req.params.email;
        var eventId =  req.params.eventId;
        var dataType = req.params.dataType
        var fileData = "data:"+dataType+";base64,"+req.params.fileData;
        var publicId = eventId+"__"+req.params.fileName;

        console.log("ImagesController.uploadFileRaw() email request ", useremail);
        console.log("ImagesController.uploadFileRaw() eventId ", eventId);
        console.log("ImagesController.uploadFileRaw() public_id   ", publicId);
        console.log("ImagesController.uploadFileRaw() file data  ", fileData);
        

        //fileData = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg=="
        cloudinary.v2.uploader.unsigned_upload(fileData,"agonhyod", {"resource_type": "auto", "public_id":publicId }, function(error, result) {
            console.log(result, error);
            return res.send(generalResponse.sendFailureResponse("Error Occured :something went wrong while uploading", 400, error));
        });

    }


    return that;

};

module.exports = new ImagesController();
