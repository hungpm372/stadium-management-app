package vn.id.phanminhhung.models;

import java.io.Serializable;
import java.util.List;

public class Stadium implements Serializable {

    private int id;
    private String name;
    private int capacity;
    private String address;
    private String size;
    private List<String> images;
    private String status;
    private String repair;
    private PlayGround playGround;
    private Stand stand;
    private DressingRoom dressingRoom;
    private HealthStation healthStation;
    private Media media;

    public Stadium() {
        status = "Đang hoạt động";
    }

    public Stadium(int id, String name, int capacity, String address, String size, List<String> images, String status, String repair, PlayGround playGround, Stand stand, DressingRoom dressingRoom, HealthStation healthStation, Media media) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.address = address;
        this.size = size;
        this.images = images;
        this.status = status;
        this.repair = repair;
        this.playGround = playGround;
        this.stand = stand;
        this.dressingRoom = dressingRoom;
        this.healthStation = healthStation;
        this.media = media;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }

    public void setPlayGround(PlayGround playGround) {
        this.playGround = playGround;
    }

    public Stand getStand() {
        return stand;
    }

    public void setStand(Stand stand) {
        this.stand = stand;
    }

    public DressingRoom getDressingRoom() {
        return dressingRoom;
    }

    public void setDressingRoom(DressingRoom dressingRoom) {
        this.dressingRoom = dressingRoom;
    }

    public HealthStation getHealthStation() {
        return healthStation;
    }

    public void setHealthStation(HealthStation healthStation) {
        this.healthStation = healthStation;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    @Override
    public String toString() {
        return "Stadium{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", address='" + address + '\'' +
                ", size='" + size + '\'' +
                ", images=" + images +
                ", status='" + status + '\'' +
                ", repair='" + repair + '\'' +
                ", playGround=" + playGround +
                ", stand=" + stand +
                ", dressingRoom=" + dressingRoom +
                ", healthStation=" + healthStation +
                ", media=" + media +
                '}';
    }
}
