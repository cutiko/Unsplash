package cl.cutiko.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

public class UnsplashUser {
    @Embedded
    private UnsplashUserProfile_image profile_image;
    private String name, bio, username;
    @ColumnInfo(name = "user_id")
    private String id;
    private int total_photos, total_likes, total_collections;

    public UnsplashUserProfile_image getProfile_image() {
        return this.profile_image;
    }

    public void setProfile_image(UnsplashUserProfile_image profile_image) {
        this.profile_image = profile_image;
    }

    public int getTotal_photos() {
        return this.total_photos;
    }

    public void setTotal_photos(int total_photos) {
        this.total_photos = total_photos;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getTotal_collections() {
        return this.total_collections;
    }

    public void setTotal_collections(int total_collections) {
        this.total_collections = total_collections;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotal_likes() {
        return this.total_likes;
    }

    public void setTotal_likes(int total_likes) {
        this.total_likes = total_likes;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
