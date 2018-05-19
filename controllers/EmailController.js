/**
 * Created by Bilawal Aziz on 17.05.18.
 * for sending email notification
 */

var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: process.env.EMAIL_APP_SERVICE,
    auth: {
        user: process.env.EMAIL_APP_USER,
        pass: process.env.EMAIL_APP_PASS
    }
});
    
module.exports=transporter;