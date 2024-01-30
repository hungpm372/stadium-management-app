package vn.id.phanminhhung.models;

import java.io.Serializable;
import java.util.List;

public class HealthStation implements Serializable {
    private int type;
    private String name;
    private String size;
    private int capacity;
    private int numberOfBeds;
    private String privateArea;
    private String seatingAvailable;
    private String medicalEquipment;
    private String emergencyExit;
    private String ventilationSystem;
    private String sterilizationEquipment;
    private String emergencyCallButton;
    private String storageCabinets;
    private List<String> images;

    public HealthStation() {
        type = 3;
        name = "Phòng y tế";
        size = privateArea = seatingAvailable = medicalEquipment
                = emergencyExit = ventilationSystem = sterilizationEquipment
                = emergencyCallButton = storageCabinets = "chưa có dữ liệu";
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
        return "HealthStation{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", capacity=" + capacity +
                ", numberOfBeds=" + numberOfBeds +
                ", privateArea='" + privateArea + '\'' +
                ", seatingAvailable='" + seatingAvailable + '\'' +
                ", medicalEquipment='" + medicalEquipment + '\'' +
                ", emergencyExit='" + emergencyExit + '\'' +
                ", ventilationSystem='" + ventilationSystem + '\'' +
                ", sterilizationEquipment='" + sterilizationEquipment + '\'' +
                ", emergencyCallButton='" + emergencyCallButton + '\'' +
                ", storageCabinets='" + storageCabinets + '\'' +
                ", images=" + images +
                '}';
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
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

    public String getMedicalEquipment() {
        return medicalEquipment;
    }

    public void setMedicalEquipment(String medicalEquipment) {
        this.medicalEquipment = medicalEquipment;
    }

    public String getEmergencyExit() {
        return emergencyExit;
    }

    public void setEmergencyExit(String emergencyExit) {
        this.emergencyExit = emergencyExit;
    }

    public String getVentilationSystem() {
        return ventilationSystem;
    }

    public void setVentilationSystem(String ventilationSystem) {
        this.ventilationSystem = ventilationSystem;
    }

    public String getSterilizationEquipment() {
        return sterilizationEquipment;
    }

    public void setSterilizationEquipment(String sterilizationEquipment) {
        this.sterilizationEquipment = sterilizationEquipment;
    }

    public String getEmergencyCallButton() {
        return emergencyCallButton;
    }

    public void setEmergencyCallButton(String emergencyCallButton) {
        this.emergencyCallButton = emergencyCallButton;
    }

    public String getStorageCabinets() {
        return storageCabinets;
    }

    public void setStorageCabinets(String storageCabinets) {
        this.storageCabinets = storageCabinets;
    }
}
