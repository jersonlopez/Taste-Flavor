package co.edu.udea.compumovil.gr04_20172.proyecto.DTOs;

/**
 * Created by jersonlopez on 3/11/17.
 */



public class Place  {
    private String name;
    private String photo;
    private String phone;
    private String Description;
    private String type;
    private String direction;
    private String state;

    public Place() {
    }

    public Place (String name, String photo, String phone, String Description, String type, String direction, String state) {
        this.name = name;
        this.photo = photo;
        this.type = type;
        this.Description= Description;
        this.direction = direction;
        this.phone = phone;
        this.state = state;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return Description;
    }

    public void setShortDescription(String Description) {
        this.Description = Description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
