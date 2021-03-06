module.exports = function (app) {
    var user = require('./controllers/UserController');
    var location = require('./controllers/LocationController');
    var ImagesController = require('./controllers/ImagesController');
    var friend = require('./controllers/FriendController');
    var event = require('./controllers/EventsController');
    var playlist = require('./controllers/PlayListController');



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

    app.post('/uploadImage', ImagesController.uploadImage);
    app.post('/uploadFile', ImagesController.uploadFile);
    app.post('/uploadFileRaw', ImagesController.uploadFileRaw);
    app.get('/download', ImagesController.download);

    // friend related routes
    app.post('/addfriend', friend.addFriend);  // added friend
    app.post('/unfriend', friend.unFriend);
    app.get('/friends/:email', friend.getFriendListByEmail); // get friend by email
    app.get('/friendlist/:email', friend.searchFriend);  // get user friend list

    

    /// event based routes
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

    /// playlist based routes
    app.post('/createEventPlaylist', playlist.createEventPlaylist); // create playlist for event
    app.post('/addNewSongInPlaylist', playlist.addNewSongInPlaylist); // add new song to playlist for event
    app.post('/incrementSongVote', playlist.incrementSongVote); // increment vote for next song




};
