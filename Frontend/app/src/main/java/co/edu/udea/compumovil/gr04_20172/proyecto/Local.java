package co.edu.udea.compumovil.gr04_20172.proyecto;

import java.util.List;

public class Local {
    String name;
    String direction;
    String phone;
    int photo;
    int id;

    public Local(String name, String phone, String direction, int photo, int id) {
        this.name = name;
        this.direction = direction;
        this.photo = photo;
        this.phone= phone;
        this.id = id;
    }
    private List<Local> locals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}



