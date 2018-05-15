/**
 * Created by Bilawal Aziz on 10.05.18.
 */

function EventsController() {

    var that = this;

    var mongoose = require('mongoose');
    mongoose.set('debug', true);

    var generalResponse = require('./GeneralResponse');
    var userlocations = require('../models/eventsSchema');
    var events = require('../models/eventsSchema');

    var config = require("../maps-config.js");
    var nodemailer = require('nodemailer');
    var transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: 'musikey2018@gmail.com',
            pass: '090078601!'
        }
    });


    var NearBySearch = require("googleplaces/lib/NearBySearch");
    var nearBySearch = new NearBySearch(config.apiKey, config.outputFormat);

    var json = require('../locationdata.json');

    that.getLocation = function (req, res, next) {

        var parameters = {
            location: req.params.location,
            radius: req.params.radius,
            keyword: req.params.keyword
        };

        //TODO search requested location in database

        var query = userlocations.find(parameters)
        query.exec(function (err, result) {
            if (err) {
                console.log("Error " + err)
                throw  err;
            } else {
                if (result.length > 0) {
                    console.log("present in database "+result)
                    res.send(result)
                } else {

                    var searchResults = []
                    //TODO if location not available search in google maps
                    nearBySearch(parameters, function (error, response) {
                        if (error) {
                            return res.send(generalResponse.sendFailureResponse(false, 400, error))
                        }
                        for (var key in response) {
                            if (key == 'results') {
                                for (var data in response[key]) {

                                    var locationData = response[key][data]
                                    var location = {
                                        location: req.params.location,
                                        radius: req.params.radius,
                                        keyword: req.params.keyword,
                                        geometry: locationData.geometry,
                                        language: "en",
                                        id: locationData.id,
                                        name: locationData.name,
                                        photos: locationData.photos,
                                        place_id: locationData.place_id,
                                    }
                                    searchResults.push(location)
                                }
                            }
                        }


                        console.log("Search results .length " + searchResults.length)


                        userlocations.create(
                            searchResults
                            , function (err, result) {
                                if (err) {
                                    console.log("Saving userlocations in db failed " + err)
                                    return res.send(generalResponse.sendFailureResponse(false, 400, error))
                                }
                                else {
                                    console.log("Saving location in db success " + result)
                                }
                            });


                        return res.send(generalResponse.sendSuccessResponse(true, 200, searchResults))

                    });

                }
            }
        });

    };

     // search event by name
     that.searchEventbyName = function (req, res, next) {
        try {

            console.log(req.params);
            events.find({name: req.params.name}, function (err, result) {

                if (typeof result != 'undefined' && result.length > 0) {
                    return res.send(generalResponse.sendSuccessResponse("event found with name:" +req.params.name, 200, result));
                }
                else {
                    return res.send(generalResponse.sendFailureResponse("there is no such event", 400, result));
                }
            });
            return next();


        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/searchEventbyName:Exception Occured", 400, ex));
        }
    };

    // search event by email
    that.searchEventByUser = function (req, res, next) {
        try {

            console.log(req.params);
            events.find({creator: req.params.email}, function (err, result) {

                if (typeof result != 'undefined' && result.length > 0) {
                    return res.send(generalResponse.sendSuccessResponse("event found with creator" +req.params.email, 200, result));
                }
                else {
                    return res.send(generalResponse.sendFailureResponse("there is no such event", 400, result));
                }
            });
            return next();


        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/searchEventByUser:Exception Occured", 400, ex));
        }
    };

    // search event by place
    that.searchEventByCity = function (req, res, next) {
        try {

            console.log(req.params);
            events.find({ city: req.params.eventCity}, function (err, result) {

                if (typeof result != 'undefined' && result.length > 0) {
                    return res.send(generalResponse.sendSuccessResponse("event found with city:" +req.params.eventCity, 200, result));
                }
                else {
                    return res.send(generalResponse.sendFailureResponse("there is no such event", 400, result));
                }
            });
            return next();


        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/searchEventByCity:Exception Occured", 400, ex));
        }
    };

    // search event by name
    that.searchEventbyDateRange = function (req, res, next) {
        try {

            console.log(req.params);
            var eventStartDate = Date.parse(req.params.eventStartDate);
            var eventEndDate = Date.parse(req.params.eventEndDate);
            events.find({date: { $gte: eventStartDate, $lte: eventEndDate  } } , function (err, result) {

                if (typeof result != 'undefined' && result.length > 0) {
                    return res.send(generalResponse.sendSuccessResponse("event found within date range" +eventStartDate +  ' - '+ eventEndDate, 200, result));
                }
                else {
                    return res.send(generalResponse.sendFailureResponse("there is no such event", 400, result));
                }
            });
            return next();


        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/searchEventbyDateRange:Exception Occured", 400, ex));
        }
    };

    // search event by near by 5km
    that.searchEventNearBy = function (req, res, next) {
        try {

            console.log(req.params);
            var userlocation = req.params.userlocation.split(',').map(Number);
            console.log(userlocation);

            events.find({location: { $nearSphere: {$geometry: {type : "Point", coordinates : userlocation }, $minDistance: 0, $maxDistance: 5000} }}, function (err, result) { 
            var query = events.find();
            //query.where('location').near({center:{coordinates:userlocation,type:'Point'}, maxDistance: 5});
            //query.exec(function (err, result) {
                if (typeof result != 'undefined' && result!= null && result.length > 0) {
                    return res.send(generalResponse.sendSuccessResponse("event found within coprdinates " +userlocation +  ' range of 1-5km', 200, result));
                }
                else {
                    return res.send(generalResponse.sendFailureResponse("there are no events near by", 400, result));
                }
            });
            return next();


        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/searchEventNearBy:Exception Occured", 400, ex));
        }
    };


    // Create new event
    that.createEvent = function (req, res, next) {
        try {

            console.log(req.params);
            var locationCoords = req.params.eventLocation.split(',').map(Number);
            var eventDate = Date.parse(req.params.eventDate);
            console.log(locationCoords);
            console.log(typeof eventDate);
            console.log(eventDate);
            //return res.send(generalResponse.sendSuccessResponse("Event creation Was successful", 200, result));
            events.find({creator: req.params.email, date: eventDate, time: req.params.eventTime, location: {type: "Point", coordinates: locationCoords} , city: req.params.eventCity}, function (err, result) {
                console.log('some thing is done');
                if (typeof result != 'undefined' && result.length > 0) {
                    return res.send(generalResponse.sendFailureResponse("there is an another event already registered for same time and location", 400, result));
                }
                else {
                    var parameters = req.params;
                    
                    var event = {
                        name: parameters.name,
                        location: {type: "Point", coordinates: locationCoords},
                        genere: parameters.genere,
                        is_public: parameters.is_public,
                        is_expired: false,
                        time: parameters.eventTime,
                        date: eventDate,
                        city: parameters.eventCity,
                        creator: parameters.email,
                        participantsCount : 1
                    };

                    events.create(
                        event
                        , function (err, result) {
                            if (err) {
                                console.log(err);
                                return res.send(generalResponse.sendFailureResponse("Error Occured While creating an event", 400, err));
                            }
                            else {

                                transporter.sendMail({
                                    to: event.email,
                                    subject: "Musikey - ( platform to share your music) Event created",
                                    text: "You have successfully created new event to Musikey. Exploration of World is now on tip of your hand"
                                }, function (error, info) {
                                    if (error) {
                                        console.log("UtilController that.sendEmail() Email Send error ", error);
                                    } else {
                                        console.log('UtilController that.sendEmail() Email sent: ' + info.response);
                                    }
                                });

                                return res.send(generalResponse.sendSuccessResponse("Event creation Was successful", 200, result));
                            }
                        });


                }
            });
            return next();


        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/register:Exception Occured", 400, ex));
        }
    };

    // add participant
    that.joinEvent = function (req, res, next) {
        try {

            console.log(req.params);
            events.findOneAndUpdate({timing: req.params.eventTime, location: req.params.eventLocation},{ $push: { participants:req.params.email}},{$inc : {participantsCount : 1}}, { new: true }, function (err, eventDoc) {
                console.log('updated event');
                console.log(eventDoc);
                if (err) {
                    console.log(err);
                    return res.send(generalResponse.sendFailureResponse("Error Occured While registering fot e an event", 400, err));
                }
                return res.send(generalResponse.sendSuccessResponse("you are registered for event successfully", 200, eventDoc));
            });
            return next();


        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/joinEvent:Exception Occured", 400, ex));
        }
    };
    return that;

};

module.exports = new EventsController();
