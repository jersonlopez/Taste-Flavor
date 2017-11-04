package co.edu.udea.compumovil.gr04_20172.proyecto.DTOs;

/**
 * Created by jersonlopez on 3/11/17.
 */

public class Customer {
    String Id;
    String username;
    String userlastname;
    String city;
    String photo;

    public Customer() {
    }

    public Customer(String Id, String username, String userlastname, String city, String photo) {

        this.Id = Id;
        this.username = username;
        this.userlastname = userlastname;
        this.city = city;
        this.photo = photo;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserlastname() {
        return userlastname;
    }

    public void setUserlastname(String userlastname) {
        this.userlastname = userlastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
