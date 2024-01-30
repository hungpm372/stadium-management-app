package vn.id.phanminhhung.models;

import java.io.Serializable;
import java.util.List;

public class PlayGround implements Serializable {

    private String id;
    private int type;
    private String name;
    private List<String> images;
    private String size;
    private String yardSurface;
    private int athleticsTrack;
    private int motorbikeRacingTrack;

    public PlayGround() {
        type = 0;
        name = "Sân chơi";
        size = yardSurface = "chưa có dữ liệu";
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

    public String getYardSurface() {
        return yardSurface;
    }

    public void setYardSurface(String yardSurface) {
        this.yardSurface = yardSurface;
    }

    public int getAthleticsTrack() {
        return athleticsTrack;
    }

    public void setAthleticsTrack(int athleticsTrack) {
        this.athleticsTrack = athleticsTrack;
    }

    public int getMotorbikeRacingTrack() {
        return motorbikeRacingTrack;
    }

    public void setMotorbikeRacingTrack(int motorbikeRacingTrack) {
        this.motorbikeRacingTrack = motorbikeRacingTrack;
    }

    @Override
    public String toString() {
        return "PlayGround{" +
                "name='" + name + '\'' +
                ", images=" + images +
                ", size='" + size + '\'' +
                ", yardSurface='" + yardSurface + '\'' +
                ", athleticsTrack=" + athleticsTrack +
                ", motorbikeRacingTrack=" + motorbikeRacingTrack +
                '}';
    }
}
