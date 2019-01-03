package cl.cutiko.data.models;

import androidx.room.ColumnInfo;

public class UnsplashUserProfile_image {

    @ColumnInfo(name = "small_user")
    private String small;
    @ColumnInfo(name = "large_user")
    private String large;
    @ColumnInfo(name = "medium_user")
    private String medium;

    public String getSmall() {
        return this.small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return this.large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return this.medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
