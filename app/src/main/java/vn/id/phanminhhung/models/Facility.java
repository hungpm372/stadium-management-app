package vn.id.phanminhhung.models;

import java.io.Serializable;
import java.util.List;

public class Facility implements Serializable {
    private int id;
    private String name;
    private List<String> images;

    public Facility() {
    }

    public Facility(int id, String name, List<String> images) {
        this.id = id;
        this.name = name;
        this.images = images;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", images=" + images +
                '}';
    }
}
