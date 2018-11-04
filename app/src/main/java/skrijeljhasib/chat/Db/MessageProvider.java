package skrijeljhasib.chat.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import skrijeljhasib.chat.Db.ChatContract.MessageEntry;
import skrijeljhasib.chat.Entity.Message;

public class MessageProvider extends ChatDbConnection {

    public MessageProvider(Context context) {
        super(context);
    }

    public List<Message> getMessagesFromRoom(int roomId) {
        String selection = MessageEntry.COLUMN_NAME_ROOM_ID + " = ?";
        String[] selectionArgs = {"" + roomId + ""};

        String sortOrder = MessageEntry._ID + " ASC";

        Cursor cursor = getReadableDatabase().query(
                MessageEntry.TABLE_NAME,    // The table to query
                null,               // The array of columns to return (pass null to get all)
                selection,                  // The columns for the WHERE clause
                selectionArgs,              // The values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                sortOrder                   // The sort order
        );

        List<Message> messages = new ArrayList<>();

        while (cursor.moveToNext()) {
            Message message = new Message();
            message.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MessageEntry._ID)));
            message.setBody(cursor.getString(cursor.getColumnIndexOrThrow(MessageEntry.COLUMN_NAME_BODY)));
            message.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(MessageEntry.COLUMN_NAME_USERNAME)));
            messages.add(message);
        }
        cursor.close();

        return messages;
    }

    public void createMessage(Message message) {
        ContentValues values = new ContentValues();
        values.put(MessageEntry.COLUMN_NAME_BODY, message.getBody());
        values.put(MessageEntry.COLUMN_NAME_USERNAME, message.getUsername());
        //values.put(MessageEntry.COLUMN_NAME_CREATED_AT, message.getCreatedAt().toString());
        values.put(MessageEntry.COLUMN_NAME_ROOM_ID, message.getRoom().getId());
        getWritableDatabase().insert(MessageEntry.TABLE_NAME, null, values);
    }
}
