package skrijeljhasib.chat.Entity;

import java.util.Collection;
import java.util.Date;

public class Room implements Entity {
    private int id;

    private String key;

    private Date createdAt;

    private int status;

    private String name;

    private Collection messages;

    private Collection contexts;

    private boolean isPrivate; // should be private not isPrivate

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection getMessages() {
        return messages;
    }

    public void setMessages(Collection messages) {
        this.messages = messages;
    }

    public Collection getContexts() {
        return contexts;
    }

    public void setContexts(Collection contexts) {
        this.contexts = contexts;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String toString() {
        return super.toString();
    }
}
