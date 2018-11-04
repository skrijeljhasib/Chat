package skrijeljhasib.chat.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static skrijeljhasib.chat.Db.ChatSchema.SQL_CREATE_MESSAGE;
import static skrijeljhasib.chat.Db.ChatSchema.SQL_CREATE_ROOM;
import static skrijeljhasib.chat.Db.ChatSchema.SQL_DELETE_MESSAGE;
import static skrijeljhasib.chat.Db.ChatSchema.SQL_DELETE_ROOM;

public abstract class ChatDbConnection extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Chat.db";

    public ChatDbConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ROOM);
        db.execSQL(SQL_CREATE_MESSAGE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ROOM);
        db.execSQL(SQL_DELETE_MESSAGE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
