/**
 * Created by Bilawal Aziz on 06.05.18.
 */

module.exports = (function eventsSchema() {

    var mongoose = require('../db').mongoose;

    var schema = {
        id: {type: String},
        name: {type: String}, // [Long, Lat]
        location: {
            type:{type: String},
            coordinates: []
        },
        genere: {type: String},
        is_public: {type: Boolean},
        is_expired: {type: String},
        time: {type: String},
        date: {type: Date},
        participants: {type: [String]},
        city: {type: String},
        creator: {type: String}
    };
    schema.index({location: '2dsphere'});

    var collectionName = 'events';
    var eventsSchema = mongoose.Schema(schema);
    var events = mongoose.model(collectionName, eventsSchema);


    return events;
})();