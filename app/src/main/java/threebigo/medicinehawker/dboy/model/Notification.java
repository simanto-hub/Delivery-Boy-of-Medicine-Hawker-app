package threebigo.medicinehawker.dboy.model;

public class Notification {

    private String id, delivery_boy_id, order_id, title, message, type, date_created;

    public Notification ( ) {
    }

    public Notification ( String id,String delivery_boy_id,String order_id,String title,String message,String type,String date_created ) {
        this.id = id;
        this.delivery_boy_id = delivery_boy_id;
        this.order_id = order_id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.date_created = date_created;
    }

    public String getId ( ) {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getDelivery_boy_id ( ) {
        return delivery_boy_id;
    }

    public void setDelivery_boy_id ( String delivery_boy_id ) {
        this.delivery_boy_id = delivery_boy_id;
    }

    public String getOrder_id ( ) {
        return order_id;
    }

    public void setOrder_id ( String order_id ) {
        this.order_id = order_id;
    }

    public String getTitle ( ) {
        return title;
    }

    public void setTitle ( String title ) {
        this.title = title;
    }

    public String getMessage ( ) {
        return message;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }

    public String getType ( ) {
        return type;
    }

    public void setType ( String type ) {
        this.type = type;
    }

    public String getDate_created ( ) {
        return date_created;
    }

    public void setDate_created ( String date_created ) {
        this.date_created = date_created;
    }
}
