/**
 * Created by Bilawal Aziz on 06.05.18.
 */

module.exports = (function eventsSchema() {

    var mongoose = require('../db').mongoose;

    var schema = {
        id: {type: String},
        name: {type: [String]}, // [Long, Lat]
        location: {type: String},
        genere: {type: String},
        is_public: {type: Boolean},
        is_expired: {type: String},
        timings: {type: String},
        participants: {type: [Object]},
        place_id: {type: String},
    };

    var collectionName = 'events';
    var eventsSchema = mongoose.Schema(schema);
    var events = mongoose.model(collectionName, eventsSchema);


    return events;
})();