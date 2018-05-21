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
    var notifier = require('../core/Notifier');

    var transporter = require('../core/Email');

    // Create new playlist
    that.createEventPlaylist = function (req, res, next) {
        try {

            console.log(req.params);
           
            var eventId = req.params.eventId;
            var playlistName = req.params.playlistName;
            var playlistSongs = req.params.playlistSongs.split(',');
            var songs = [];
            for(var i=0; i< playlistSongs.length; i++){
                songs[i] = {
                 name:playlistSongs[i],
                 votes: 0
                }
            }

            console.log(songs);

            eventPlaylist  = new playlist({
                'eventId': eventId,
                'name': playlistName,
                'songs': playlistSongs
            });
            console.log(req.params);

            playlist.findOneAndUpdate({'eventId':req.params.eventId},{'eventId':req.params.eventId, 'name': playlistName, 'songs': songs} ,{ new: 'true', upsert:'true' }, function (err, eventDoc) {
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
                return res.send(generalResponse.sendSuccessResponse("playlist created/updated successfully", 200, eventDoc));
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
            var newSong = { 'name':req.params.newSong}
           
            console.log(req.params);

            playlist.findOneAndUpdate({'eventId':req.params.eventId},{$push:{'songs':newSong}} ,{ new: 'true'}, function (err, eventDoc) {
                console.log('updated event playlist');
                console.log(eventDoc);
                if (err) {
                    console.log(err);
                    return res.send(generalResponse.sendFailureResponse("Error Occured While update for event playlist", 400, err));
                }

                if( null == eventDoc || undefined ==eventDoc) {
                    return res.send(generalResponse.sendFailureResponse("update failed", 200, null));
                }
                if(null!= eventDoc._id &&  eventDoc._id != undefined) 
                    notifier.trigger('playlist_update','newSong-'+eventDoc._id,eventDoc);

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
            //sort:{ "songs.$.votes": -1 }
            playlist.findOneAndUpdate({"eventId":req.params.eventId, "songs":{$elemMatch:{"name":songName}}},{$inc:{"songs.$.votes":1}, $push:{"songs.$.likedBy":likedBy}} ,{ new: 'true'}, function (err, eventDoc) {
                console.log('updated event playlist');
                console.log(eventDoc);
                if (err) {
                    console.log(err);
                    return res.send(generalResponse.sendFailureResponse("Error Occured While update for event playlist", 400, err));
                }
                if( null == eventDoc || undefined ==eventDoc) {
                    return res.send(generalResponse.sendFailureResponse("can not update vote for given song", 200, null));
                }
                if(null!= eventDoc._id &&  eventDoc._id != undefined) 
                    notifier.trigger('vote_update','vote-'+eventId,eventDoc);

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
