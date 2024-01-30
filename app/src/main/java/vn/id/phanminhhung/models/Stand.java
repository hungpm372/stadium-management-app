package vn.id.phanminhhung.models;

import java.io.Serializable;
import java.util.List;

public class Stand implements Serializable {
    private int type;
    private String name;
    private List<String> images;
    private String standA;
    private String standB;
    private String standC;
    private String standD;
    private int brokenChair;

    public Stand() {
        name = "Khán đài";
        type = 1;
        standA = standB = standC = standD = "chưa có dữ liệu";
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

    public String getStandA() {
        return standA;
    }

    public void setStandA(String standA) {
        this.standA = standA;
    }

    public String getStandB() {
        return standB;
    }

    public void setStandB(String standB) {
        this.standB = standB;
    }

    public String getStandC() {
        return standC;
    }

    public void setStandC(String standC) {
        this.standC = standC;
    }

    public String getStandD() {
        return standD;
    }

    public void setStandD(String standD) {
        this.standD = standD;
    }

    public int getBrokenChair() {
        return brokenChair;
    }

    public void setBrokenChair(int brokenChair) {
        this.brokenChair = brokenChair;
    }

    @Override
    public String toString() {
        return "Stand{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", images=" + images +
                ", standA='" + standA + '\'' +
                ", standB='" + standB + '\'' +
                ", standC='" + standC + '\'' +
                ", standD='" + standD + '\'' +
                ", brokenChair=" + brokenChair +
                '}';
    }
}
