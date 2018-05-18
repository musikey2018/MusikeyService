/**
 * Created by Bilawal Aziz on 10.05.18.
 */

function PlayListController() {

    var that = this;

    var mongoose = require('mongoose');
    mongoose.set('debug', true);

    var generalResponse = require('./GeneralResponse');
    var events = require('../models/eventsSchema');
    var users = require('../models/musikeyUserSchema');
    var playlist = require('../models/playlistSchema');

    var transporter = require('./EmailController');

    // Create new playlist
    that.createEventPlaylist = function (req, res, next) {
        try {

            console.log(req.params);
           
            var eventId = req.params.eventId;
            var playlistName = req.params.playlistName;
            var playlistSongs = req.params.playlistSongs;

            eventPlaylist  = new playlist({
                'eventId': eventId,
                'name': playlistName,
                'songs': playlistSongs
            });
            console.log(req.params);

            playlist.findOneAndUpdate({'eventId':req.params.eventId},{'eventId':req.params.eventId, 'name': playlistName, 'songs': playlistSongst} ,{ new: 'true', upsert:'true' }, function (err, eventDoc) {
                console.log('updated event playlist');
                console.log(eventDoc);
                if (err) {
                    console.log(err);
                    return res.send(generalResponse.sendFailureResponse("Error Occured While update for event playlist", 400, err));
                }

                // events.findByIdAndUpdate(req.params.eventId,{ 'playlist': eventDoc} ,{ new: 'true' }, function (err, eventDoc) {
                //     console.log('updated event');
                //     console.log(eventDoc);
                //     if (err) {
                //         console.log(err);
                //         return res.send(generalResponse.sendFailureResponse("Error Occured  While update for event playlist", 400, err));
                //     }
                   
                //     return res.send(generalResponse.sendSuccessResponse("update event  with new playlist successfully", 200, eventDoc));
                // });
                return res.send(generalResponse.sendSuccessResponse("playlist updated successfully", 200, eventDoc));
            });

            
            return next();
        
        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/createEventPlaylist:Exception Occured", 400, ex));
        }
    };


    // add new song in playlist
    that.addNewSongInPlaylist = function (req, res, next) {
        try {

            console.log(req.params);
           
            var eventId = req.params.eventId;
            var newSong = req.params.newSong;
           
            console.log(req.params);

            playlist.findOneAndUpdate({'eventId':req.params.eventId},{$push:{'songs':newSong}} ,{ new: 'true'}, function (err, eventDoc) {
                console.log('updated event playlist');
                console.log(eventDoc);
                if (err) {
                    console.log(err);
                    return res.send(generalResponse.sendFailureResponse("Error Occured While update for event playlist", 400, err));
                }

                // events.findByIdAndUpdate(req.params.eventId,{ 'playlist': eventDoc} ,{ new: 'true' }, function (err, eventDoc) {
                //     console.log('updated event');
                //     console.log(eventDoc);
                //     if (err) {
                //         console.log(err);
                //         return res.send(generalResponse.sendFailureResponse("Error Occured  While update for event playlist", 400, err));
                //     }
                   
                //     return res.send(generalResponse.sendSuccessResponse("update event  with new playlist successfully", 200, eventDoc));
                // });
                return res.send(generalResponse.sendSuccessResponse("playlist updated successfully", 200, eventDoc));
            });

            
            return next();
        
        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/createEventPlaylist:Exception Occured", 400, ex));
        }
    };

    // increment song vote
    that.incrementSongVote = function (req, res, next) {
        
        try {

            console.log(req.params);
           
            var eventId = req.params.eventId;
            var songName = req.params.songName;
            var likedBy = req.params.likedBy;
           
            console.log(req.params);

            playlist.findOneAndUpdate({"eventId":req.params.eventId, "songs.song":songName},{$inc:{"songs.$.votes":newSong}, $push:{"songs.$.likedBy":likedBy}} ,{ new: 'true'}, function (err, eventDoc) {
                console.log('updated event playlist');
                console.log(eventDoc);
                if (err) {
                    console.log(err);
                    return res.send(generalResponse.sendFailureResponse("Error Occured While update for event playlist", 400, err));
                }

                // events.findByIdAndUpdate(req.params.eventId,{ 'playlist': eventDoc} ,{ new: 'true' }, function (err, eventDoc) {
                //     console.log('updated event');
                //     console.log(eventDoc);
                //     if (err) {
                //         console.log(err);
                //         return res.send(generalResponse.sendFailureResponse("Error Occured  While update for event playlist", 400, err));
                //     }
                   
                //     return res.send(generalResponse.sendSuccessResponse("update event  with new playlist successfully", 200, eventDoc));
                // });
                return res.send(generalResponse.sendSuccessResponse("playlist updated successfully", 200, eventDoc));
            });

            
            return next();
        
        }
        catch (ex) {
            console.log("Exception:" + ex);
            return res.send(generalResponse.sendFailureResponse("/createEventPlaylist:Exception Occured", 400, ex));
        }
    };


    return that;

};

module.exports = new PlayListController();
