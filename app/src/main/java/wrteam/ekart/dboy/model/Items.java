package wrteam.ekart.dboy.model;

public class Items {
    String id;
    String product_variant_id;
    String name;
    String unit;
    String product_image;
    String price;
    String quantity;
    String subtotal;

    public Items ( ) {
    }

    public Items ( String id,String product_variant_id,String name,String unit,String product_image,String price,String quantity,String subtotal ) {
        this.id = id;
        this.product_variant_id = product_variant_id;
        this.name = name;
        this.unit = unit;
        this.product_image = product_image;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public String getId ( ) {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getProduct_variant_id ( ) {
        return product_variant_id;
    }

    public void setProduct_variant_id ( String product_variant_id ) {
        this.product_variant_id = product_variant_id;
    }

    public String getName ( ) {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getUnit ( ) {
        return unit;
    }

    public void setUnit ( String unit ) {
        this.unit = unit;
    }

    public String getPrice ( ) {
        return price;
    }

    public void setPrice ( String price ) {
        this.price = price;
    }

    public String getProduct_image ( ) {
        return product_image;
    }

    public void setProduct_image ( String product_image ) {
        this.product_image = product_image;
    }

    public String getQuantity ( ) {
        return quantity;
    }

    public void setQuantity ( String quantity ) {
        this.quantity = quantity;
    }

    public String getSubtotal ( ) {
        return subtotal;
    }

    public void setSubtotal ( String subtotal ) {
        this.subtotal = subtotal;
    }

}
