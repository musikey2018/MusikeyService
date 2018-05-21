/**
 * Created by Bilawal Aziz on 17.05.18.
 * for sending email notification
 */

var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: process.env.EMAIL_APP_SERVICE,
    host: process.env.EMAIL_APP_HOST,
    secure:false,
    auth: {
        user: process.env.EMAIL_APP_USER,
        pass: process.env.EMAIL_APP_PASS
    }
});
console.log(process.env.EMAIL_APP_HOST)
console.log(process.env.EMAIL_APP_USER)
module.exports=transporter;