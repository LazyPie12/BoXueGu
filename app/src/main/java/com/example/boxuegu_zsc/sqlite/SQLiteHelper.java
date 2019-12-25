package com.example.boxuegu_zsc.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    public static String DB_NAME = "bxg.db";
    public static final String U_USERINFO = "userinfo";  // 个人资料
    public static final String U_VIDEO_PLAY_LIST = "videoplaylist";  // 视频播放列表
    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建个人信息表
        db.execSQL("CREATE TABLE  IF NOT EXISTS " + U_USERINFO + "("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "userName VARCHAR, "
                        + "nickName VARCHAR, "
                        + "sex VARCHAR, "
                        + "signature VARCHAR"
                        + ")");
        // 创建视频播放记录表
        db.execSQL("CREATE TABLE  IF NOT EXISTS " + U_VIDEO_PLAY_LIST + "( "
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                +" userName VARCHAR,"//用户名
                + "chapterId INT, "//章节ID号
                + "videoId INT, "// 小节ID号
                + "videoPath VARCHAR, "// 视频地址
                + "title VARCHAR,"// 视频章节名称
                + "secondTitle VARCHAR"// 视频名称
                + ")");
    }

    // 但数据库版本号增加时才会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + U_USERINFO);
        db.execSQL("DROP TABLE IF EXISTS " + U_VIDEO_PLAY_LIST);
        onCreate(db);
    }
}
