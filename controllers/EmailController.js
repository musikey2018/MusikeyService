/**
 * Created by Bilawal Aziz on 17.05.18.
 * for sending email notification
 */

var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'musikey2018@gmail.com',
        pass: '090078601!'
    }
});
    
module.exports=transporter;