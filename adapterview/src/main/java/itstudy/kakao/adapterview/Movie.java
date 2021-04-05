package itstudy.kakao.adapterview;

import java.io.Serializable;

class Movie implements Serializable {
    private int movieid;
    private String title;
    private String subtitle;
    private String pubdate;
    private String director;
    private String actor;
    private String genre;
    private double rating;
    private String thumbnail;
    private String link;

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieid=" + movieid +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", thumbnail='" + thumbnail + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}