package co.edu.udea.compumovil.gr04_20172.proyecto.DTOs;

/**
 * Created by jersonlopez on 3/11/17.
 */

public class Food {

    private String name;
    private String photo;
    private String shortDescription;
    private String price;
    private String place;

    public Food() {
    }

    public Food (String name, String photo, String shortDescription, String price, String place) {
        this.photo = name;
        this.photo = photo;
        this.price = price;
        this.shortDescription = shortDescription;
        this.place = place;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
