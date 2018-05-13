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
     that.searchEvent = function (req, res, next) {
        try {

            console.log(req)
            events.find({email: req.params.email, time: req.params.eventTime, name: req.params.name}, function (err, result) {

                if (result.length > 0) {
                    return res.send(generalResponse.sendSuccessResponse("event found with name" +req.params.name, 200, result));
                }
                else {
                    return res.send(generalResponse.sendFailureResponse("there is no such event", 400, result));
                }
            });
            return next();


        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/searchEvent:Exception Occured", 400, ex));
        }
    };

    // Create new event
    that.createEvent = function (req, res, next) {
        try {

            console.log(req);
            //return res.send(generalResponse.sendSuccessResponse("Event creation Was successful", 200, result));
            events.find({email: req.params.email, time: req.params.eventTime, location: req.params.eventLocation}, function (err, result) {

                if (result.length > 0) {
                    return res.send(generalResponse.sendFailureResponse("there is an another event already registered for same time and location", 400, result));
                }
                else {
                    var parameters = req.params;
                    var event = {
                        name: parameters.name,
                        location: parameters.eventLocation,
                        genere: parameters.genere,
                        is_public: parameters.is_public,
                        is_expired: false,
                        timing: parameters.eventTime,
                        place_id: parameters.placename,
                        creator: parameters.email
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
    return that;

};

module.exports = new EventsController();
