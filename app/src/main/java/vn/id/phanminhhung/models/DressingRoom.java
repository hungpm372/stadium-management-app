package vn.id.phanminhhung.models;

import java.io.Serializable;
import java.util.List;

public class DressingRoom implements Serializable {
    private int type;
    private String name;
    private String size;
    private String privateArea;
    private String seatingAvailable;
    private String personalLocker;
    private String lightingSystem;
    private String fireAlarmSystem;
    private String fireFightingEquipment;
    private String emergencyExit;
    private List<String> images;

    public DressingRoom() {
        type = 2;
        name = "Phòng thay đồ";
        size = privateArea = seatingAvailable = personalLocker = lightingSystem = fireAlarmSystem
                = fireFightingEquipment = emergencyExit = "chưa có dữ liệu";
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrivateArea() {
        return privateArea;
    }

    public void setPrivateArea(String privateArea) {
        this.privateArea = privateArea;
    }

    public String getSeatingAvailable() {
        return seatingAvailable;
    }

    public void setSeatingAvailable(String seatingAvailable) {
        this.seatingAvailable = seatingAvailable;
    }

    public String getPersonalLocker() {
        return personalLocker;
    }

    public void setPersonalLocker(String personalLocker) {
        this.personalLocker = personalLocker;
    }

    public String getLightingSystem() {
        return lightingSystem;
    }

    public void setLightingSystem(String lightingSystem) {
        this.lightingSystem = lightingSystem;
    }

    public String getFireAlarmSystem() {
        return fireAlarmSystem;
    }

    public void setFireAlarmSystem(String fireAlarmSystem) {
        this.fireAlarmSystem = fireAlarmSystem;
    }

    public String getFireFightingEquipment() {
        return fireFightingEquipment;
    }

    public void setFireFightingEquipment(String fireFightingEquipment) {
        this.fireFightingEquipment = fireFightingEquipment;
    }

    public String getEmergencyExit() {
        return emergencyExit;
    }

    public void setEmergencyExit(String emergencyExit) {
        this.emergencyExit = emergencyExit;
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

    @Override
    public String toString() {
        return "DressingRoom{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", privateArea='" + privateArea + '\'' +
                ", seatingAvailable='" + seatingAvailable + '\'' +
                ", personalLocker='" + personalLocker + '\'' +
                ", lightingSystem='" + lightingSystem + '\'' +
                ", fireAlarmSystem='" + fireAlarmSystem + '\'' +
                ", fireFightingEquipment='" + fireFightingEquipment + '\'' +
                ", emergencyExit='" + emergencyExit + '\'' +
                ", images=" + images +
                '}';
    }
}
