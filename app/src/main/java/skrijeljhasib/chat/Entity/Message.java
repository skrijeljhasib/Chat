package skrijeljhasib.chat.Entity;

import java.util.Collection;
import java.util.Date;

public class Message implements Entity
{
    private int id;

    private String body;

    private Date createdAt;

    private String username;

    private String displayUsername;

    private Room room;

    private Collection contexts;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getDisplayUsername()
    {
        return displayUsername;
    }

    public void setDisplayUsername(String displayUsername)
    {
        this.displayUsername = displayUsername;
    }

    public Room getRoom()
    {
        return room;
    }

    public void setRoom(Room room)
    {
        this.room = room;
    }

    public Collection getContexts()
    {
        return contexts;
    }

    public void setContexts(Collection contexts)
    {
        this.contexts = contexts;
    }
}
