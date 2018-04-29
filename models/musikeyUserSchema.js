// Model for the users
module.exports = (function musickeyUserSchema () {

	var mongoose = require('../db').mongoose;

	var schema = {
		username: {type: String, required: true},
		email: {type: String, required: true},
		password: {type: String, required: true},
        hashKey: {type: String},
        age: {type: String,required: true},
        number: {type: String,required: true},
        state: {type: String, required:true},
        city: {type: String,required: true},
        id:{type: String},
        isRegisteredByFacebook: {type: Boolean},
        isRegisteredByGmail: {type: Boolean},
        isRegisteredByother: {type: Boolean},
        imageURL: {type: String},
        verificationCode: {type: String},
        friends: [{type:String}],
        comments:[{type:String}]
	};

    var collectionName = 'musickeyUser';
    var musickeyUserSchema = mongoose.Schema(schema);
    var musickeyUserSchema = mongoose.model(collectionName, musickeyUserSchema);

    return musickeyUserSchema;
})();