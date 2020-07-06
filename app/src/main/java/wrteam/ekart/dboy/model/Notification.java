package wrteam.ekart.dboy.model;

public class Notification {

    private String date_sent, title, message;

    public Notification() {

    }

    public Notification(String date_sent, String title, String message) {
        this.date_sent = date_sent;
        this.title = title;
        this.message = message;
    }


    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
