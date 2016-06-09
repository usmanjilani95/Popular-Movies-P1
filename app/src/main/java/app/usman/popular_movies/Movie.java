package app.usman.popular_movies;

import java.io.Serializable;

/**
 * Created by Usman Ahmad Jilani on 21-05-2016.
 */
public class Movie implements Serializable {
    String title=null,backdropImg=null,imgMain=null,popularity=null,releaseDate=null,voteCount=null,rating=null,id=null,overview=null,lang=null,tagline=null,duration=null;


    Movie(String id,String title,String overview,String backdropImg,String imgMain,String popularity,String releaseDate,String voteCount,String rating,String lang){
        this.id=id;
        this.title=title;
        this.overview=overview;
        this.backdropImg="http://image.tmdb.org/t/p/w500/"+backdropImg;
        this.imgMain="http://image.tmdb.org/t/p/w300/"+imgMain;
        this.popularity=popularity;
        this.releaseDate=releaseDate;
        this.voteCount=voteCount;
        this.rating=rating;
        this.lang=lang;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropImg() {
        return backdropImg;
    }

    public void setBackdropImg(String backdropImg) {
        this.backdropImg = backdropImg;
    }

    public String getImgMain() {
        return imgMain;
    }

    public void setImgMain(String imgMain) {
        this.imgMain = imgMain;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
