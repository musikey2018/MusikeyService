/**
 * Created by Bilawal Aziz on 18.05.18.
 */

module.exports = (function imagesSchema() {

    var mongoose = require('../db').mongoose;

    var schema = {
        id: {type: String},
        useremail: {type: String},
        eventId: {type: String},
        imageData: [{type: String}],
        imageSchema:[{
            public_id: {type: String},
            version: {type: String},
            width: {type: Number},
            height: {type: Number},
            format: {type: String},
            created_at: {type: String},
            resource_type: {type: String},
            tags: [], 
            bytes: {type: Number}, 
            type: {type: String}, 
            etag: {type: String}, 
            url: {type: String},
            secure_url: {type: String},
            signature: {type: String},
            original_filename: {type: String},
            comments:[{
                user: {type: String},
                comment: {type: String},
            }]
        }]
        
    };
    

    var collectionName = 'images';
    var imagesSchema = mongoose.Schema(schema);
    imagesSchema.index({location: '2dsphere'});
    var images = mongoose.model(collectionName, imagesSchema);


    return images;
})();