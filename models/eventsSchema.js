/**
 * Created by Bilawal Aziz on 06.05.18.
 */

module.exports = (function eventsSchema() {

    var mongoose = require('../db').mongoose;

    var schema = {
        id: {type: String},
        name: {type: String}, 
        location: {
            type:{type: String},
            coordinates: [Number]  // [Long, Lat]
        },
        genere: {type: String},
        is_public: {type: Boolean},
        is_expired: {type: Boolean},
        time: {type: String},
        date: {type: Date},
        participants: {type: [String]},
        participantsCount: {type: Number, default:1},
        invitedParticipants: {type: [String]},
        invitedParticipantsCount: {type: Number, default:0},
        city: {type: String},
        creator: {type: String},
        votes: {type: Number, default:0},
        playlist: {type: String}
    };
    

    var collectionName = 'events';
    var eventsSchema = mongoose.Schema(schema);
    eventsSchema.index({location: '2dsphere'});
    var events = mongoose.model(collectionName, eventsSchema);


    return events;
})();