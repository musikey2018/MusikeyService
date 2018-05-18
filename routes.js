module.exports = function (app) {
    var user = require('./controllers/UserController');
    var location = require('./controllers/LocationController');
    var fileController = require('./controllers/FileController');
    var friend = require('./controllers/FriendController');
    var event = require('./controllers/EventsController');



    app.get('/', function (req, res, next) {
        return res.send("Musikey 2018 Rest services");
    });

    app.post('/register', user.register); //Register User
    app.post('/login', user.login);  // Authenticate User
    app.get('/users/:email', user.getUserByEmail);  // Get User Profile By email
    app.get('/forgotpassword/:email', user.forgotPassword);  // send forgot password at user email
    app.post('/resetpassword', user.resetPassword);  // resets password via verification code
    app.post('/updatepassword', user.changePassword);  // change password,provide old password,new password,email
    //app.get('/users', user.getUserList);  // Get User List
    app.post('/updateprofile', user.updateUserProfile);  // Update  User Profile
    app.del('/users/:email', user.deleteUserByEmail);  // Delete  User By Email


    app.get('/location', location.getLocation);  // Get Location

    app.post('/uploadImage', fileController.uploadImage);
    app.get('/download', fileController.download);

    app.post('/addfriend', friend.addFriend);
    app.post('/unfriend', friend.unFriend);
    app.get('/friends/:email', friend.getFriendListByEmail);
    app.get('/friendlist/:email', friend.searchFriend);

    
    app.post('/createEvent', event.createEvent);  //create event
    app.post('/joinEvent', event.joinEvent);  //join event by location and time
    app.post('/joinEventById', event.joinEventById); // join event by id

    app.get('/searchEventbyName?:name', event.searchEventbyName);  // search event by name
    app.get('/searchEventByCity?:eventCity', event.searchEventByCity);  // search event by city
    app.get('/searchEventByUser?:email', event.searchEventByUser);  // search event by creator email
    app.get('/searchEventbyDateRange?:eventStartDate/:eventEndDate', event.searchEventbyDateRange);  // search event by date range
    app.get('/searchEventNearBy?:userlocation', event.searchEventNearBy);  // search event by location near by 5km
    
    app.get('/searchFriendsEvents?:email', event.getEventsFriendsParticipated);  // search events in which friends have participated
    app.post('/inviteForEvent', event.inviteForEvent); // invite for event (eventid, email)


};
