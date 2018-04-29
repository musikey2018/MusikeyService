package com.victorsquarecity.app.utils.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_ORM".
*/
public class UserORMDao extends AbstractDao<UserORM, Long> {

    public static final String TABLENAME = "USER_ORM";

    /**
     * Properties of entity UserORM.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property _id = new Property(0, long.class, "_id", true, "_id");
        public final static Property MUserName = new Property(1, String.class, "mUserName", false, "M_USER_NAME");
        public final static Property MEmail = new Property(2, String.class, "mEmail", false, "M_EMAIL");
        public final static Property MPassword = new Property(3, String.class, "mPassword", false, "M_PASSWORD");
        public final static Property MImageUrl = new Property(4, String.class, "mImageUrl", false, "M_IMAGE_URL");
        public final static Property MState = new Property(5, String.class, "mState", false, "M_STATE");
        public final static Property MCity = new Property(6, String.class, "mCity", false, "M_CITY");
        public final static Property MAddress = new Property(7, String.class, "mAddress", false, "M_ADDRESS");
        public final static Property MMobileNumber = new Property(8, String.class, "mMobileNumber", false, "M_MOBILE_NUMBER");
        public final static Property MAge = new Property(9, String.class, "mAge", false, "M_AGE");
        public final static Property MProfileID = new Property(10, String.class, "mProfileID", false, "M_PROFILE_ID");
        public final static Property Images = new Property(11, String.class, "images", false, "IMAGES");
        public final static Property Comments = new Property(12, String.class, "comments", false, "COMMENTS");
        public final static Property CheckIns = new Property(13, String.class, "checkIns", false, "CHECK_INS");
        public final static Property Friends = new Property(14, String.class, "friends", false, "FRIENDS");
    };

    private final GreenConverter imagesConverter = new GreenConverter();
    private final GreenConverter commentsConverter = new GreenConverter();
    private final GreenConverter checkInsConverter = new GreenConverter();
    private final GreenConverter friendsConverter = new GreenConverter();

    public UserORMDao(DaoConfig config) {
        super(config);
    }
    
    public UserORMDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_ORM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," + // 0: _id
                "\"M_USER_NAME\" TEXT NOT NULL ," + // 1: mUserName
                "\"M_EMAIL\" TEXT NOT NULL ," + // 2: mEmail
                "\"M_PASSWORD\" TEXT NOT NULL ," + // 3: mPassword
                "\"M_IMAGE_URL\" TEXT," + // 4: mImageUrl
                "\"M_STATE\" TEXT NOT NULL ," + // 5: mState
                "\"M_CITY\" TEXT NOT NULL ," + // 6: mCity
                "\"M_ADDRESS\" TEXT," + // 7: mAddress
                "\"M_MOBILE_NUMBER\" TEXT NOT NULL ," + // 8: mMobileNumber
                "\"M_AGE\" TEXT NOT NULL ," + // 9: mAge
                "\"M_PROFILE_ID\" TEXT," + // 10: mProfileID
                "\"IMAGES\" TEXT," + // 11: images
                "\"COMMENTS\" TEXT," + // 12: comments
                "\"CHECK_INS\" TEXT," + // 13: checkIns
                "\"FRIENDS\" TEXT);"); // 14: friends
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_ORM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserORM entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.get_id());
        stmt.bindString(2, entity.getMUserName());
        stmt.bindString(3, entity.getMEmail());
        stmt.bindString(4, entity.getMPassword());
 
        String mImageUrl = entity.getMImageUrl();
        if (mImageUrl != null) {
            stmt.bindString(5, mImageUrl);
        }
        stmt.bindString(6, entity.getMState());
        stmt.bindString(7, entity.getMCity());
 
        String mAddress = entity.getMAddress();
        if (mAddress != null) {
            stmt.bindString(8, mAddress);
        }
        stmt.bindString(9, entity.getMMobileNumber());
        stmt.bindString(10, entity.getMAge());
 
        String mProfileID = entity.getMProfileID();
        if (mProfileID != null) {
            stmt.bindString(11, mProfileID);
        }
 
        List images = entity.getImages();
        if (images != null) {
            stmt.bindString(12, imagesConverter.convertToDatabaseValue(images));
        }
 
        List comments = entity.getComments();
        if (comments != null) {
            stmt.bindString(13, commentsConverter.convertToDatabaseValue(comments));
        }
 
        List checkIns = entity.getCheckIns();
        if (checkIns != null) {
            stmt.bindString(14, checkInsConverter.convertToDatabaseValue(checkIns));
        }
 
        List friends = entity.getFriends();
        if (friends != null) {
            stmt.bindString(15, friendsConverter.convertToDatabaseValue(friends));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserORM entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.get_id());
        stmt.bindString(2, entity.getMUserName());
        stmt.bindString(3, entity.getMEmail());
        stmt.bindString(4, entity.getMPassword());
 
        String mImageUrl = entity.getMImageUrl();
        if (mImageUrl != null) {
            stmt.bindString(5, mImageUrl);
        }
        stmt.bindString(6, entity.getMState());
        stmt.bindString(7, entity.getMCity());
 
        String mAddress = entity.getMAddress();
        if (mAddress != null) {
            stmt.bindString(8, mAddress);
        }
        stmt.bindString(9, entity.getMMobileNumber());
        stmt.bindString(10, entity.getMAge());
 
        String mProfileID = entity.getMProfileID();
        if (mProfileID != null) {
            stmt.bindString(11, mProfileID);
        }
 
        List images = entity.getImages();
        if (images != null) {
            stmt.bindString(12, imagesConverter.convertToDatabaseValue(images));
        }
 
        List comments = entity.getComments();
        if (comments != null) {
            stmt.bindString(13, commentsConverter.convertToDatabaseValue(comments));
        }
 
        List checkIns = entity.getCheckIns();
        if (checkIns != null) {
            stmt.bindString(14, checkInsConverter.convertToDatabaseValue(checkIns));
        }
 
        List friends = entity.getFriends();
        if (friends != null) {
            stmt.bindString(15, friendsConverter.convertToDatabaseValue(friends));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public UserORM readEntity(Cursor cursor, int offset) {
        UserORM entity = new UserORM( //
            cursor.getLong(offset + 0), // _id
            cursor.getString(offset + 1), // mUserName
            cursor.getString(offset + 2), // mEmail
            cursor.getString(offset + 3), // mPassword
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // mImageUrl
            cursor.getString(offset + 5), // mState
            cursor.getString(offset + 6), // mCity
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // mAddress
            cursor.getString(offset + 8), // mMobileNumber
            cursor.getString(offset + 9), // mAge
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // mProfileID
            cursor.isNull(offset + 11) ? null : imagesConverter.convertToEntityProperty(cursor.getString(offset + 11)), // images
            cursor.isNull(offset + 12) ? null : commentsConverter.convertToEntityProperty(cursor.getString(offset + 12)), // comments
            cursor.isNull(offset + 13) ? null : checkInsConverter.convertToEntityProperty(cursor.getString(offset + 13)), // checkIns
            cursor.isNull(offset + 14) ? null : friendsConverter.convertToEntityProperty(cursor.getString(offset + 14)) // friends
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserORM entity, int offset) {
        entity.set_id(cursor.getLong(offset + 0));
        entity.setMUserName(cursor.getString(offset + 1));
        entity.setMEmail(cursor.getString(offset + 2));
        entity.setMPassword(cursor.getString(offset + 3));
        entity.setMImageUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMState(cursor.getString(offset + 5));
        entity.setMCity(cursor.getString(offset + 6));
        entity.setMAddress(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setMMobileNumber(cursor.getString(offset + 8));
        entity.setMAge(cursor.getString(offset + 9));
        entity.setMProfileID(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setImages(cursor.isNull(offset + 11) ? null : imagesConverter.convertToEntityProperty(cursor.getString(offset + 11)));
        entity.setComments(cursor.isNull(offset + 12) ? null : commentsConverter.convertToEntityProperty(cursor.getString(offset + 12)));
        entity.setCheckIns(cursor.isNull(offset + 13) ? null : checkInsConverter.convertToEntityProperty(cursor.getString(offset + 13)));
        entity.setFriends(cursor.isNull(offset + 14) ? null : friendsConverter.convertToEntityProperty(cursor.getString(offset + 14)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserORM entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserORM entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
