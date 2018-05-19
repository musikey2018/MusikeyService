/**
 * Created by Bilawal Aziz on 19.05.18.
 * for media services
 */

var cloudinary = require('cloudinary');

cloudinary.config({
    cloud_name: process.env.CLOUDINARY_APP_CLOUD,
    api_key: process.env.CLOUDINARY_APP_KEY,
    api_secret: process.env.CLOUDINARY_APP_PASS
});
    
module.exports=cloudinary;