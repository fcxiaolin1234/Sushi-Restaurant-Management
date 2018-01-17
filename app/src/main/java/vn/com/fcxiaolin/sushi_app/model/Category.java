package vn.com.fcxiaolin.sushi_app.model;

/**
 * Created by HIEU on 17/01/2018.
 */

public class Category {

    private int id;
    private String name;
    private String imageLink;

    public Category(int id, String name, String imageLink) {
        this.id = id;
        this.name = name;
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
