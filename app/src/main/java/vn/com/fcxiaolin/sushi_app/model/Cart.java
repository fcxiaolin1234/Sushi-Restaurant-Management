package vn.com.fcxiaolin.sushi_app.model;

/**
 * Created by Dell on 1/19/2018.
 */

public class Cart {
    public int id;
    public String tenSp;
    public float giaSp;
    public String imgSp;
    public int soLuong;

    public Cart(int id, String tenSp, float giaSp, String imgSp, int soLuong) {
        this.id = id;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.imgSp = imgSp;
        this.soLuong = soLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public float getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(float giaSp) {
        this.giaSp = giaSp;
    }

    public String getImgSp() {
        return imgSp;
    }

    public void setImgSp(String imgSp) {
        this.imgSp = imgSp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
