package com.example.ekta.popularmovies.Model;

/**
 * Created by Ekta on 28-09-2016.
 */
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
    private int dbId;
    private int id;
    String title;
    private String urlSelf;
    private String coverImage;
    String audienceScore;
    String popularity;
    private String tagLine;
    private String releaseDateTheater;
    String duration;
    private String genre;
    private String overview;

    public Movie() {

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    public Movie(int id, String title, String urlSelf, String coverImage, String audienceScore, String popularity, String tagLine, String releaseDate, String duration, String genre, String overview) {
        this.id = id;
        this.title = title;
        this.urlSelf = urlSelf;
        this.coverImage = coverImage;
        this.tagLine = tagLine;
        this.audienceScore = audienceScore;
        this.popularity = popularity;
        this.tagLine = tagLine;
        this.releaseDateTheater = releaseDate;
        this.duration = duration;
        this.genre = genre;
        this.overview = overview;


    }

    public Movie(Parcel in) {

        String[] data = new String[2];
        in.readStringArray(data);
        this.id = Integer.valueOf(data[0]);

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDateTheater() {
        return releaseDateTheater;
    }

    public void setReleaseDateTheater(String releaseDateTheater) {
        this.releaseDateTheater = releaseDateTheater;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public String getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(String audienceScore) {
        this.audienceScore = audienceScore;
    }


    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getTagLine() {
        return tagLine;
    }


    public void setReleasedate(String releaseDate) {
        this.releaseDateTheater = releaseDate;
    }

    public String getReleasedate() {
        return releaseDateTheater;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setImage(String image) {
        this.urlSelf = urlSelf;
    }

    public String getImage() {
        return urlSelf;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setStringid(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getStringid() {
        return String.valueOf(id);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);


    }
}

