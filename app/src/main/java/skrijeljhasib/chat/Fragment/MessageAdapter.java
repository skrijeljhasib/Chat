package skrijeljhasib.chat.Fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import skrijeljhasib.chat.Entity.Message;
import skrijeljhasib.chat.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> messages;

    public MessageAdapter(Context context, List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Message message = messages.get(position);
        viewHolder.setMessage(message.getBody());
        viewHolder.setUsername(message.getUsername());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageView;
        private TextView usernameView;

        public ViewHolder(View itemView) {
            super(itemView);
            messageView = itemView.findViewById(R.id.message);
            usernameView = itemView.findViewById(R.id.username);
        }

        public void setUsername(String username) {
            if (null == usernameView) return;
            usernameView.setText(username);
        }

        public void setMessage(String message) {
            if (null == messageView) return;
            messageView.setText(message);
        }
    }
}
