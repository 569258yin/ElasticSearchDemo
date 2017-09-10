package elacticsearch.restapi;

import java.util.Date;

/**
 * Created by kevinyin on 2017/9/9.
 */
public class Message {
    private String user;
    private Date post_date;
    private String message;

    public Message(String user, Date post_date, String message) {
        this.user = user;
        this.post_date = post_date;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
