/**
 * Created by Bilawal Aziz on 17.05.18.
 */

module.exports = (function playlistSchema() {

    var mongoose = require('../db').mongoose;

    var schema = {
        id: {type: String},
        eventId: {type: String},
        name: {type: String},
        songs:[{
            name:{type:String},
            votes: {type: Number ,default:0},
            likedBy: [{type: String}]
        }]
    };
    

    var collectionName = 'playlists';
    var playlistSchema = mongoose.Schema(schema);
    var playlists = mongoose.model(collectionName, playlistSchema);


    return playlists;
})();