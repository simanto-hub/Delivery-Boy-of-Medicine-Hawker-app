package wrteam.ekart.dboy.model;

import java.util.ArrayList;

public class OrderList {

    String id;
    String user_id;
    String name;
    String mobile;
    String delivery_charge;
    String total;
    String tax;
    String promo_discount;
    String wallet_balance;
    String discount;
    String qty;
    String final_total;
    String promo_code;
    String deliver_by;
    String payment_method;
    String address;
    String delivery_time;
    String active_status;
    String date_added;
    String latitude;
    String longitude;
    ArrayList<Items> items;

    public OrderList ( ) {
    }

    public OrderList ( String id,String user_id,String name,String mobile,String delivery_charge,String total,String tax,String promo_discount,String wallet_balance,String discount,String qty,String final_total,String promo_code,String deliver_by,String payment_method,String address,String delivery_time,String active_status,String date_added,String latitude,String longitude,ArrayList<Items> items ) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.mobile = mobile;
        this.delivery_charge = delivery_charge;
        this.total = total;
        this.tax = tax;
        this.promo_discount = promo_discount;
        this.wallet_balance = wallet_balance;
        this.discount = discount;
        this.qty = qty;
        this.final_total = final_total;
        this.promo_code = promo_code;
        this.deliver_by = deliver_by;
        this.payment_method = payment_method;
        this.address = address;
        this.delivery_time = delivery_time;
        this.active_status = active_status;
        this.date_added = date_added;
        this.latitude = latitude;
        this.longitude = longitude;
        this.items = items;
    }

    public String getId ( ) {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getUser_id ( ) {
        return user_id;
    }

    public void setUser_id ( String user_id ) {
        this.user_id = user_id;
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

    public String getDelivery_charge ( ) {
        return delivery_charge;
    }

    public void setDelivery_charge ( String delivery_charge ) {
        this.delivery_charge = delivery_charge;
    }

    public String getTotal ( ) {
        return total;
    }

    public void setTotal ( String total ) {
        this.total = total;
    }

    public String getTax ( ) {
        return tax;
    }

    public void setTax ( String tax ) {
        this.tax = tax;
    }

    public String getPromo_discount ( ) {
        return promo_discount;
    }

    public void setPromo_discount ( String promo_discount ) {
        this.promo_discount = promo_discount;
    }

    public String getWallet_balance ( ) {
        return wallet_balance;
    }

    public void setWallet_balance ( String wallet_balance ) {
        this.wallet_balance = wallet_balance;
    }

    public String getDiscount ( ) {
        return discount;
    }

    public void setDiscount ( String discount ) {
        this.discount = discount;
    }

    public String getQty ( ) {
        return qty;
    }

    public void setQty ( String qty ) {
        this.qty = qty;
    }

    public String getFinal_total ( ) {
        return final_total;
    }

    public void setFinal_total ( String final_total ) {
        this.final_total = final_total;
    }

    public String getPromo_code ( ) {
        return promo_code;
    }

    public void setPromo_code ( String promo_code ) {
        this.promo_code = promo_code;
    }

    public String getDeliver_by ( ) {
        return deliver_by;
    }

    public void setDeliver_by ( String deliver_by ) {
        this.deliver_by = deliver_by;
    }

    public String getPayment_method ( ) {
        return payment_method;
    }

    public void setPayment_method ( String payment_method ) {
        this.payment_method = payment_method;
    }

    public String getAddress ( ) {
        return address;
    }

    public void setAddress ( String address ) {
        this.address = address;
    }

    public String getDelivery_time ( ) {
        return delivery_time;
    }

    public void setDelivery_time ( String delivery_time ) {
        this.delivery_time = delivery_time;
    }

    public String getActive_status ( ) {
        return active_status;
    }

    public void setActive_status ( String active_status ) {
        this.active_status = active_status;
    }

    public String getDate_added ( ) {
        return date_added;
    }

    public void setDate_added ( String date_added ) {
        this.date_added = date_added;
    }

    public String getLatitude ( ) {
        return latitude;
    }

    public void setLatitude ( String latitude ) {
        this.latitude = latitude;
    }

    public String getLongitude ( ) {
        return longitude;
    }

    public void setLongitude ( String longitude ) {
        this.longitude = longitude;
    }

    public ArrayList<Items> getItems ( ) {
        return items;
    }

    public void setItems ( ArrayList<Items> items ) {
        this.items = items;
    }

}
