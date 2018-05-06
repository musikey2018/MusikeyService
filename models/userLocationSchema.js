/**
 * Created by Bilawal Aziz on 30.04.18.
 */

module.exports = (function userLocationSchema() {

    var mongoose = require('../db').mongoose;

    var schema = {
        location: {type: [String]}, // [Long, Lat]
        radius: {type: String},
        keyword: {type: String},
        geometry: {type: Object},
        language: {type: String},
        id: {type: String},
        name: {type: String},
        photos: {type: Object},
        place_id: {type: String},
        vicinity: {type: String}
    };

    var collectionName = 'usergeolocations';
    var userLocationSchema = mongoose.Schema(schema);
    var userlocations = mongoose.model(collectionName, userLocationSchema);


    return userlocations;
})();