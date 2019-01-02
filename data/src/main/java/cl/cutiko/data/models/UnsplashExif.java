package cl.cutiko.data.models;

public class UnsplashExif {
    private String exposure_time, aperture, focal_length, model, make;
    private int iso;

    public String getExposure_time() {
        return this.exposure_time;
    }

    public void setExposure_time(String exposure_time) {
        this.exposure_time = exposure_time;
    }

    public String getAperture() {
        return this.aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getFocal_length() {
        return this.focal_length;
    }

    public void setFocal_length(String focal_length) {
        this.focal_length = focal_length;
    }

    public int getIso() {
        return this.iso;
    }

    public void setIso(int iso) {
        this.iso = iso;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }
}
