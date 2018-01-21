package vn.com.fcxiaolin.sushi_app.utils;

/**
 * Created by HIEU on 17/01/2018.
 */

public class Server {
    public static String localhost = "192.168.43.101";
    public static String categoryUrl = "http://" + localhost + "/server/get_category.php";
    public static String newProductUrl = "http://"+ localhost + "/server/get_new_product.php";
    public static String typeProductUrl = "http://"+ localhost + "/server/get_product_by_idcategory.php?page=";
    public static String orderUrl = "http://"+ localhost + "/server/customer_detail.php";
    public static String orderDetailUrl = "http://"+ localhost + "/server/order_detail.php";
}
