package wrteam.ekart.dboy.model;

public class WalletHistory {

    String id, name, mobile, address, delivery_boy_id, type, opening_balance, amount, closing_balance, status, message, date_created;

    public WalletHistory ( ) {
    }

    public WalletHistory ( String id,String name,String mobile,String address,String delivery_boy_id,String type,String opening_balance,String amount,String closing_balance,String status,String message,String date_created ) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.delivery_boy_id = delivery_boy_id;
        this.type = type;
        this.opening_balance = opening_balance;
        this.amount = amount;
        this.closing_balance = closing_balance;
        this.status = status;
        this.message = message;
        this.date_created = date_created;
    }

    public String getId ( ) {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getName ( ) {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getMobile ( ) {
        return mobile;
    }

    public void setMobile ( String mobile ) {
        this.mobile = mobile;
    }

    public String getAddress ( ) {
        return address;
    }

    public void setAddress ( String address ) {
        this.address = address;
    }

    public String getDelivery_boy_id ( ) {
        return delivery_boy_id;
    }

    public void setDelivery_boy_id ( String delivery_boy_id ) {
        this.delivery_boy_id = delivery_boy_id;
    }

    public String getType ( ) {
        return type;
    }

    public void setType ( String type ) {
        this.type = type;
    }

    public String getOpening_balance ( ) {
        return opening_balance;
    }

    public void setOpening_balance ( String opening_balance ) {
        this.opening_balance = opening_balance;
    }

    public String getAmount ( ) {
        return amount;
    }

    public void setAmount ( String amount ) {
        this.amount = amount;
    }

    public String getClosing_balance ( ) {
        return closing_balance;
    }

    public void setClosing_balance ( String closing_balance ) {
        this.closing_balance = closing_balance;
    }

    public String getStatus ( ) {
        return status;
    }

    public void setStatus ( String status ) {
        this.status = status;
    }

    public String getMessage ( ) {
        return message;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }

    public String getDate_created ( ) {
        return date_created;
    }

    public void setDate_created ( String date_created ) {
        this.date_created = date_created;
    }
}
