package vn.id.phanminhhung.models;

import java.io.Serializable;
import java.util.List;

public class Media implements Serializable {
    private int type;
    private String name;
    private List<String> images;
    private String size;
    private int seatingCapacity;
    private String lightingSystem;
    private String hasProjector;
    private String hasSoundSystem;
    private String hasMicrophones;
    private String hasStage;

    public Media() {
        type = 4;
        name = "Phòng truyền thông";
        size = lightingSystem = hasProjector = hasSoundSystem = hasMicrophones = hasStage = "chưa có dữ liệu";
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getLightingSystem() {
        return lightingSystem;
    }

    public void setLightingSystem(String lightingSystem) {
        this.lightingSystem = lightingSystem;
    }

    public String getHasProjector() {
        return hasProjector;
    }

    public void setHasProjector(String hasProjector) {
        this.hasProjector = hasProjector;
    }

    public String getHasSoundSystem() {
        return hasSoundSystem;
    }

    public void setHasSoundSystem(String hasSoundSystem) {
        this.hasSoundSystem = hasSoundSystem;
    }

    public String getHasMicrophones() {
        return hasMicrophones;
    }

    public void setHasMicrophones(String hasMicrophones) {
        this.hasMicrophones = hasMicrophones;
    }

    public String getHasStage() {
        return hasStage;
    }

    public void setHasStage(String hasStage) {
        this.hasStage = hasStage;
    }

    @Override
    public String toString() {
        return "Media{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", images=" + images +
                ", size='" + size + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                ", lightingSystem='" + lightingSystem + '\'' +
                ", hasProjector='" + hasProjector + '\'' +
                ", hasSoundSystem='" + hasSoundSystem + '\'' +
                ", hasMicrophones='" + hasMicrophones + '\'' +
                ", hasStage='" + hasStage + '\'' +
                '}';
    }
}
