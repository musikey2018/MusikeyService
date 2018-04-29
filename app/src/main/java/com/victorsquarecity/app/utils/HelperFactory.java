package com.victorsquarecity.app.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;
import com.victorsquarecity.app.services.LoginWebService;
import com.victorsquarecity.app.services.VictorApp;
import com.victorsquarecity.app.utils.db.dao.DaoSession;
import com.victorsquarecity.app.utils.db.dao.UserORM;
import com.victorsquarecity.app.utils.db.dao.UserORMDao;
import com.victorsquarecity.app.utils.shake.ShakeIt;
import com.victorsquarecity.app.utils.shake.ShakeListener;

import org.greenrobot.greendao.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * Created by bilaw on 6/7/2017.
 */


public class HelperFactory {

    private static final String TAG = "HelperFactory";
    private static HelperFactory instance = new HelperFactory();
    static DaoSession daoSession;

    private HelperFactory() {
    }

    public static ResponseModel createResponseFromJSONR(String result, Context context) throws Exception {
        if (result == null)
            return null;

        Log.d("Result login", result);
        JSONObject jResponse = new JSONObject(result);

        String resp_desc = (String) jResponse.get(ResponseModelJSON.RESPONSE);
        String respcode = jResponse.get(ResponseModelJSON.RESPCODE).toString();
        String sessionId = null;
//        String tokenid = null;
//        if (respcode == true){
//            sessionId  = jResponse.getString("grav");//ResponseModelJSON.SESSION_ID);
//         tokenid = jResponse.getString(ResponseModelJSON.AUTH_TOKEN);
//    }
        ResponseModel response = new ResponseModel();
        response.setRespcode(respcode);
        response.setResponse(resp_desc);
//        response.setSessionid(sessionId);
//        response.setToken(tokenid);
        return response;
    }

    public static boolean getChatMessages(Context context, String myUserName, String myUserId, String mFriendID) {
        ResponseModel responseModel = new ResponseModel();
        String result = ChatWebService.getChatMessages(context, myUserName, myUserId, mFriendID);
        boolean success = authenticateUserFromJSON(responseModel, result, context);
        if (success == true)
            return true;
        else return false;


    }

    public static boolean authenticateUser(Application application, Context context, String email, String password, String username) throws Exception {

        String result = LoginWebService.getInstance().loginUser(context, email, password, username);


        Log.d(TAG, "authenticateUser() Result login" + result);
        JSONObject jResponse = new JSONObject(result);

        String resp_desc = (String) jResponse.get(ResponseModelJSON.RESPONSE);
        String respcode = (String) jResponse.get(ResponseModelJSON.RESPCODE);

        ResponseModel responseModel = new ResponseModel();
        responseModel.setRespcode(respcode);
        responseModel.setResponse(resp_desc);

        Log.d(TAG, "authenticateUser() Response Model " + responseModel.toString());

        if (jResponse.has("responseData") && !jResponse.isNull("responseData")) {
            setUserProfileData(jResponse, application);
        }

        return Boolean.parseBoolean(responseModel.getRespcode());

    }

    public static boolean sendUserForgotPasswordMail(Context context, String email) throws Exception {

        String result = LoginWebService.getInstance().sendUserForgotPasswordMail(context, email);

        JSONObject jResponse = new JSONObject(result);
        String respcode = (String) jResponse.get(ResponseModelJSON.RESPCODE);

        return Boolean.parseBoolean(respcode);

    }


    public static boolean registerUser(Context context, String email, String password, String username, String number, String age, String city, String state) {


        ResponseModel responseModel = new ResponseModel();
        String result = LoginWebService.getInstance().registerUser(context, email, password, username, number, age, city, state);
        boolean success = authenticateUserFromJSON(responseModel, result, context);
        return success;
    }

    public static boolean updateUserProfile(Context context,Application application, String email, String username, String number, String age, String city, String state) throws Exception {

        String result = LoginWebService.getInstance().updateUserProfile(context, email, username, number, age, city, state);
        ResponseModel responseModel = createResponseFromJSONR(result, context);
        boolean success = authenticateUserFromJSON(responseModel, result, context);
        if(success){
            Log.d(TAG, "updateUserProfile Result login" + result);
            JSONObject jResponse = new JSONObject(result);
            if (jResponse.has("responseData") && !jResponse.isNull("responseData")) {
                setUserProfileData(jResponse,application );
            }

        }
        return success;
    }

    public static boolean updateUserProfilePassword(Context context,Application application, String email, String password, String oldpassword) throws Exception {

        String result = null;
        ResponseModel responseModel = new ResponseModel();
        result = LoginWebService.getInstance().updateUserProfilePassword(context, email, password, oldpassword);
        boolean success = authenticateUserFromJSON(responseModel, result, context);
        if(success){
            Log.d(TAG, "updateUserProfilePassword:" + result);
            JSONObject jResponse = new JSONObject(result);
            if (jResponse.has("responseData") && !jResponse.isNull("responseData")) {
                setUserProfileData(jResponse,application );
            }

        }
        if (success == true)
            return true;
        else return false;
    }


    public static boolean resetPassword(Context context, String email,String password,String verificationCode) throws Exception {

        String result = LoginWebService.getInstance().verifyCode(context, email,password,verificationCode);

        JSONObject jResponse = new JSONObject(result);
        String respcode = (String) jResponse.get(ResponseModelJSON.RESPCODE);

        return Boolean.parseBoolean(respcode);

    }

    public static boolean authenticateUserFromJSON(ResponseModel responseModel, String result, Context context) {

        try {
            if (result != null)
                responseModel = createResponseFromJSONR(result, context);
            else return false;
        } catch (Exception e) {
            Log.d(TAG, "HelperFactory authenticateUserFromJSON() exception while authentication " + e.getMessage());
            throw new RuntimeException(e);
        }
        return Boolean.parseBoolean(responseModel.getRespcode());
    }

    public static HelperFactory getInstance() {
        if (instance == null)
            instance = new HelperFactory();
        return instance;
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        return file;
    }

    public static void setRetryPolicy(StringRequest request) {
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    public static void initializeShakeService(final Context context) {
        ShakeIt.initializeShakeService(context, new ShakeListener() {

            @Override
            public void onShake(float force) {
                TastyToast.makeText(context, "Voted with Force " + force, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
            }

            @Override
            public void onAccelerationChanged(float x, float y, float z) {

            }
        });
    }

    public void stopShakeService(final Context context) {
        ShakeIt.stopShakeService(context);
    }

    public static void setUserProfileData(JSONObject result, Application application) throws Exception {

        Log.d(TAG, " setUserProfileData() result " + result);

        JSONObject resultData = result.getJSONObject("responseData");
        Log.d(TAG, " setUserProfileData() resultData " + resultData);

        JSONArray friends = resultData.getJSONArray("friends");

        List<String> friendsList = new ArrayList<>();
        for (int i = 0; i < friends.length(); i++) {
            friendsList.add(friends.get(i).toString());
        }

        JSONArray comments = resultData.getJSONArray("comments");
        List<String> commentsList = new ArrayList<>();
        for (int i = 0; i < comments.length(); i++) {
            commentsList.add(comments.get(i).toString());
        }
        JSONArray images = resultData.getJSONArray("images");
        List<String> imageList = new ArrayList<>();
        for (int i = 0; i < images.length(); i++) {
            imageList.add(images.get(i).toString());
        }

        JSONArray checkIns = resultData.getJSONArray("checkIns");
        List<String> checkInsList = new ArrayList<>();
        for (int i = 0; i < checkIns.length(); i++) {
            checkInsList.add(checkIns.get(i).toString());
        }


        String email = (String) resultData.get("email");
        String city = (String) resultData.get("city");
        String mobileNumber = (String) resultData.get("number");
        String state = (String) resultData.get("state");
        String password = (String) resultData.get("password");
        String age = (String) resultData.get("age");
        String username = (String) resultData.get("username");
        String userId = (String) resultData.get("_id");
        String imageUrl=null;
        if (resultData.has("imageURL") && !resultData.isNull("imageURL")) {
            imageUrl = (String) resultData.get("imageURL");
        }
        UserORM userORM = new UserORM();
        userORM.setMCity(city);
        userORM.setMEmail(email);
        userORM.setMImageUrl(imageUrl);
        userORM.setMMobileNumber(mobileNumber);
        userORM.setMProfileID(userId);
        userORM.setMPassword(password);
        userORM.setMState(state);
        userORM.setMUserName(username);
        userORM.setMAge(age);
        userORM.setImages(imageList);
        userORM.setCheckIns(checkInsList);
        userORM.setComments(commentsList);
        userORM.setFriends(friendsList);

        DaoSession daoSession = ((VictorApp) application).getDaoSession();
        daoSession.getUserORMDao().deleteAll();

        UserORMDao userORMDao = daoSession.getUserORMDao();
        ((VictorApp) application).setEmail(email);
        userORMDao.insert(userORM);


    }

    public static UserORM getUserProfileData(Application application) {

        DaoSession daoSession = ((VictorApp) application).getDaoSession();
        UserORMDao userORMDao = daoSession.getUserORMDao();
        String email = ((VictorApp) application).getEmail();

        Log.d(TAG,"getUserProfileData() email "+email);

        UserORM user = null;

        List<UserORM> userORMs1 = userORMDao.loadAll();
        if(email != null && !email.isEmpty()){
            Query<UserORM> query = userORMDao.queryBuilder().where(UserORMDao.Properties.MEmail.eq(email)).build();
            Log.d(TAG,"getUserProfileData() query "+query);
            List<UserORM> userORMs = query.list();
            Log.d(TAG,"getUserProfileData() userOrms "+userORMs.size());


            if (userORMs.size() > 0) {
                user = userORMs.get(0);
            }
        }else if(userORMs1.size() > 0) {
            Log.d(TAG,"getUserProfileData() userOrm "+userORMs1.size());

            user = userORMs1.get(0);
            Log.d(TAG,"getUserProfileData() user "+user.toString());
        }

        return user;

    }

    public String uploadProfileImage(final String email, final String imageString, Context context) {

        String URL = VictorConstants.HOST_URL + VictorConstants.WS_UPLOAD;

           /* progressDialog = new ProgressDialog(UpdateProfileActivity.this);
            progressDialog.setMessage("Uploading, please wait...");
            progressDialog.show();*/

        //converting image to base64 string
        //   ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //   mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        //    byte[] imageBytes = baos.toByteArray();
        //    final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);


        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.POST, URL, future, future) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("imageData", imageString);
                parameters.put("email", email);
                return parameters;
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

        String resp = null;
        try {
            resp = future.get(VictorConstants.REQUEST_TIMEOUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            // Toasty.error(UpdateProfileActivity.this, "Some error occurred -> " + e.getCause(), Toast.LENGTH_LONG).show();
            throw new RuntimeException(e.getCause());
        }

        return resp;


    }


}
