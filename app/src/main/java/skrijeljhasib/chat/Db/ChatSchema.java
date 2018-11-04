package skrijeljhasib.chat.Db;

import skrijeljhasib.chat.Db.ChatContract.MessageEntry;
import skrijeljhasib.chat.Db.ChatContract.RoomEntry;

public class ChatSchema {

    public static final String SQL_CREATE_ROOM =
            "CREATE TABLE " + RoomEntry.TABLE_NAME + " (" +
                    RoomEntry._ID + " INTEGER PRIMARY KEY," +
                    RoomEntry.COLUMN_NAME_NAME + " TEXT)";

    public static final String SQL_CREATE_MESSAGE =
            "CREATE TABLE " + MessageEntry.TABLE_NAME + " (" +
                    MessageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MessageEntry.COLUMN_NAME_BODY + " TEXT," +
                    MessageEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    MessageEntry.COLUMN_NAME_CREATED_AT + " DATETIME," +
                    MessageEntry.COLUMN_NAME_ROOM_ID + " INTEGER, " +
                    "FOREIGN KEY(" + MessageEntry.COLUMN_NAME_ROOM_ID + ") REFERENCES " + RoomEntry.TABLE_NAME + "(" + RoomEntry._ID + "))";

    public static final String SQL_DELETE_ROOM =
            "DROP TABLE IF EXISTS " + RoomEntry.TABLE_NAME;

    public static final String SQL_DELETE_MESSAGE =
            "DROP TABLE IF EXISTS " + MessageEntry.TABLE_NAME;

}
