/**
 * Created by Bilawal Aziz on 30.04.18.
 */

function LocationController() {

    var that = this;

    var mongoose = require('mongoose');
    mongoose.set('debug', true);

    var generalResponse = require('./GeneralResponse');
    var userlocations = require('../models/userLocatioSchema');

    var config = require("../maps-config.js");


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
    return that;

};

module.exports = new LocationController();
