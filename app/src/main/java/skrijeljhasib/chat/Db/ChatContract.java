package skrijeljhasib.chat.Db;

import android.provider.BaseColumns;

public final class ChatContract {

    private ChatContract() {
    }

    public static class RoomEntry implements BaseColumns {
        public static final String TABLE_NAME = "room";
        public static final String COLUMN_NAME_NAME = "name";
    }

    public static class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "message";
        public static final String COLUMN_NAME_BODY = "body";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_ROOM_ID = "room_id";
    }
}
