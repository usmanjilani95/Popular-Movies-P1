package app.usman.popular_movies.Realm;

/**
 * Created by Usman Ahmad Jilani on 22-05-2016.
 */
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


/**
 * Created by NILESH on 17-05-2016.
 */
public class FavMovies extends RealmObject {

    String posterImg,backdropImg,title,overview,vote_average,release_date,vote_count,pop,lang;
    int fav;
    FavMovies object;


    @PrimaryKey
    String id;


  /*  public FavMovies()
    {

        this.fav=0;
        this.id=null;
    }*/


    public void setFav(int fav) {
        this.fav = fav;
    }

    public int getFav() {
        return fav;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setObject(FavMovies object) {
        this.object = object;
    }

    public FavMovies getObject() {
        return object;
    }

    public String getBackdropImg() {
        return backdropImg;
    }

    public void setBackdropImg(String backdropImg) {
        this.backdropImg = backdropImg;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
