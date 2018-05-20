/**
 * Created by Bilawal Aziz on 20.05.18.
 */

function ChatController() {

    var that = this;
    var generalResponse = require('./GeneralResponse');
    var request = require('request').defaults({encoding: null});
    var cloudinary = require('../core/Cloudinary');
    var users = require('../models/musikeyUserSchema');
    var images = require('../models/imagesSchema');
    var chatService = require('../core/Chat')


    that.registerUserForChat = function (req, res, next) {

        var useremail = req.params.email;
        var userId =  req.params.userId;

        console.log("ChatController.registerUserForChat() email request ", useremail);
        console.log("ChatController.registerUserForChat() eventId ", userId);
        
        chatService.createUser({
            id: userId,
            name: useremail,
          })
            .then(() => {
              console.log('User registered for chat successfully');
              return res.send(generalResponse.sendSuccessResponse("registered for chat successfully", 200, {'userId':userId}));
            }).catch((err) => {
              console.log(err);
              return res.send(generalResponse.sendFailureResponse("Error Occured :something went wrong while registering for chat", 400, null));
        });

    }

    return that;

};

module.exports = new ChatController();
