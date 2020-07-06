package wrteam.ekart.dboy.helper;

import com.android.volley.toolbox.ImageLoader;

public class Constant {

    public static final String BASE_URL = "https://ekart.wrteam.in/delivery-boy/";
    //change admin pane user here

    public static final String MAIN_URL = BASE_URL + "api/api-v1.php";

    public static final String NOTIFICATIONS_URL = "https://ekart.wrteam.in/api-firebase/sections.php";

    public static final String AccessKey = "accesskey";
    public static final String AccessKeyVal = "90336";
    public static final String GetVal = "1";
    public static final String LOAD_LIMIT = "10";


    public static final String LOGIN = "login";
    public static final String GET_DELIVERY_BOY_BY_ID = "get_delivery_boy_by_id";
    public static final String GET_ORDERS_BY_DELIVERY_BOY_ID = "get_orders_by_delivery_boy_id";
    public static final String UPDATE_DELIVERY_BOY_PROFILE = "update_delivery_boy_profile";
    public static final String UPDATE_ORDER_STATUS = "update_order_status";
    public static final String GET_FUND_TRANSFERS = "get_fund_transfers";
    public static final String DELIVERY_BOY_FORGOT_PASSWORD = "delivery_boy_forgot_password";
    public static final String GET_DELIVERY_BOY_NOTIFICATION = "get-delivery-boy-notifications";


    public static final String ID = "id";
    public static final String DELIVERY_BOY_ID = "delivery_boy_id";
    public static final String ORDER_STATUS = "order_status";
    public static final String FUND_TRANSFER = "fund_transfer";
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String ADDRESS = "address";
    public static final String BONUS = "bonus";
    public static final String BALANCE = "balance";
    public static final String STATUS = "status";
    public static final String CREATED_AT = "date_created";
    public static final String SETTING_CURRENCY_SYMBOL = "";
    public static final String DATA = "data";
    public static final String TOTAL = "total";
    public static final String UPDATE_NAME = "name";
    public static final String UPDATE_ADDRESS = "address";
    public static final String OLD_PASSWORD = "old_password";
    public static final String UPDATE_PASSWORD = "update_password";
    public static final String CONFIRM_PASSWORD = "confirm_password";
    public static final String MESSAGE = "message";
    public static final String FROM = "from";
    public static final String ORDER_ID = "order_id";
    public static final String FCM_ID = "fcm_id";
    public static final String DELIVERY_CHARGE = "delivery_charge";
    public static final String DELIVERY_TIME = "delivery_time";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String PAYMENT_METHOD = "payment_method";
    public static final String DELIVER_BY = "deliver_by";
    public static final String PROMO_CODE = "promo_code";
    public static final String FINAL_TOTAL = "final_total";
    public static final String QTY = "qty";
    public static final String DISCOUNT = "discount";
    public static final String PROMO_DISCOUNT = "promo_discount";
    public static final String TAX = "tax";
    public static final String SUBTOTAL = "subtotal";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    public static final String UNIT = "unit";
    public static final String PRODUCT_VARIANT_ID = "product_variant_id";
    public static final String DATE_ADDED = "date_added";
    public static final String STR_WALLET_BALANCE = "wallet_balance";
    public static final String ITEMS = "items";
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";
    public static final String ACTIVE_STATUS = "active_status";
    public static final String SUCCESS = "SUCCESS";
    public static final String RECEIVED = "Received";
    public static final String PROCESSED = "Processed";
    public static final String SHIPPED = "Shipped";
    public static final String DELIVERED = "Delivered";
    public static final String CANCELLED = "Cancelled";
    public static final String RETURNED = "Returned";
    public static final String SHOW = "show";
    public static final String HIDE = "hide";
    public static final String ERROR = "error";
    public static final String LOAD_ITEM_LIMIT = "10";
    public static String country_code = "";
    public static String TYPE = "type";
    public static String verificationCode;
    public static String PRODUCT_LOAD_LIMIT="10";

    //set your jwt secret key here...key must same in PHP and Android and Ekart Delivery Boy
    public static String JWT_KEY = "replace_with_your_strong_jwt_secret_key";

    public static String TOKEN = "token";
    public static int Position_Value;
    public static boolean CLICK = false;
    public static ImageLoader imageLoader = AppController.getInstance ().getImageLoader ();

}
