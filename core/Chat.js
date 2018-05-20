/**
 * Created by Bilawal Aziz on 20.05.18.
 * for chat services
 */

const Chatkit = require('@pusher/chatkit-server');

const chatkit = new Chatkit.default({
    instanceLocator: process.env.CHAT_APP_INSTANCE,
    key: process.env.CHAT_APP_PASS,
  });
    
module.exports=chatkit;
