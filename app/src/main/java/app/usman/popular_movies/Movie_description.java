package app.usman.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import app.usman.popular_movies.Realm.FavMovies;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class Movie_description extends AppCompatActivity {
    ImageView poster,title,rating_back,pop_back,lang_back,back_img,poster_img;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    TextView year,average,synopsis,mTrailer,mReview,moTitle,id,mAuthor,mText,mLang,mPop;
    FloatingActionButton share,fav;
    FrameLayout frame;
    Context context;
    String mTitle;
    String mBackdrop_Image;
    String mOverview;
    String mVote;
    String mRelease_Date;
    String mPoster_Image;
    String mId;
    String mlang;
    //private CollapsingToolbarLayout mCollapsingToolbarLayout;
    //private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    RealmConfiguration realmConfig;
    // Get a Realm instance for this thread
    Realm realm;
    //RealmList<FavMovies> a=new RealmList<>();
    FavMovies m,mf;
    //String key;

    private LinearLayoutManager mLayoutManager;
    ImageView mBackdrop,trailer_image;
    private ArrayList<String> trailerInfo = new ArrayList<>();
    private ArrayList<String> popul = new ArrayList<>();
    private ArrayList<String> language = new ArrayList<>();
    private ArrayList<String> runtime = new ArrayList<>();
    private ArrayList<String> votecount = new ArrayList<>();
    private ArrayList<String> videokey = new ArrayList<>();
    private ArrayList<String> reviewId = new ArrayList<>();
    private ArrayList<String> reviewAuthor = new ArrayList<>();
    private ArrayList<String> reviewText = new ArrayList<>();
    private ArrayList<String> thumb_img = new ArrayList<>();
    Movie movie;
    String key;
    TextView vote_count;


    String API_KEY;



    RatingBar r;
    float rate;
    double d;
    ScrollView v;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_scrolling);
        context=this;
           // setContentView(R.layout.content_scrolling);
            API_KEY=getString(R.string.API_KEY);
            realmConfig = new RealmConfiguration.Builder(context).deleteRealmIfMigrationNeeded().build();
            realm = Realm.getInstance(realmConfig);
            poster= (ImageView) findViewById(R.id.poster);
            year= (TextView) findViewById(R.id.year);
            frame= (FrameLayout) findViewById(R.id.frame1);
            mLang= (TextView) findViewById(R.id.language);
           // mBackdrop_Image= (ImageView) findViewById(R.id.imgBack);
           // mPoster_Image= (ImageView) findViewById(R.id.poster);
            average= (TextView) findViewById(R.id.rating);
            title= (ImageView) findViewById(R.id.imgBack);
            mPop= (TextView) findViewById(R.id.popularity);
            rating_back= (ImageView) findViewById(R.id.ratings_background);
            pop_back= (ImageView) findViewById(R.id.pop_background);
            lang_back= (ImageView) findViewById(R.id.lang_background);
            trailer_image= (ImageView) findViewById(R.id.trailer_image);
            synopsis= (TextView) findViewById(R.id.synopsis);
            share= (FloatingActionButton) findViewById(R.id.share);
            vote_count=(TextView) findViewById(R.id.vote_count);
        mAuthor= (TextView) findViewById(R.id.review_author_text);

        mText= (TextView) findViewById(R.id.review_text);
        mRecyclerView= (RecyclerView) findViewById(R.id.recycler_movie_details);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);
            //mBackdrop=(ImageView)findViewById(R.id.imgBack);
    //        r= (RatingBar) findViewById(R.id.rating);
            fav=(FloatingActionButton) findViewById(R.id.favbutton);
            //mTrailer= (TextView) findViewById(R.id.trailer);
            //mReview= (TextView) findViewById(R.id.review);
            moTitle= (TextView) findViewById(R.id.motitle);
            //v= (ScrollView) findViewById(R.id.sview);
            //mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout_movie_details);
            //mToolbar = (Toolbar)findViewById(R.id.toolbar_movie_details);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            //imageLoader.get("http://img.youtube.com/vi/" + source + "/0.jpg", ImageLoader.getImageListener(adjustableImageView, R.drawable.loading_trailer, R.drawable.error_trailer));



            Bundle extras = getIntent().getExtras();
            if (extras != null) {

                movie=(Movie)extras.getSerializable("Movie");
//               mTitle = extras.getString("title");
//               mBackdrop_Image =extras.getString("b_img");
//               mOverview = extras.getString("overview");
//               mVote = extras.getString("vote");
//                mRelease_Date = extras.getString("r_date");
//                mPoster_Image = extras.getString("p_img");
//                mId=extras.getString("id");
            }

        year.setText(mRelease_Date);
        average.setText(mVote);
        moTitle.setText(mTitle);
        synopsis.setText(mOverview);
//        d=Double.parseDouble(mVote);
            Glide.with(getApplicationContext()).load(Uri.parse(movie.getImgMain())).error(R.drawable.placeholder).into(poster);
            Glide.with(getApplicationContext()).load(Uri.parse(movie.getBackdropImg())).error(R.drawable.placeholder).into(title);
//        Glide.with(getApplicationContext()).load(Uri.parse(mPoster_Image)).error(R.drawable.placeholder).into(poster);
//        Glide.with(getApplicationContext()).load(Uri.parse(mBackdrop_Image)).error(R.drawable.placeholder).into(title);

        title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(videokey.size()>0){
                    String video_path = "https://www.youtube.com/watch?v="+videokey.get(0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video_path));
                    startActivity(intent);}
                }
            });
            year.setText(movie.getReleaseDate());
            //average.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

            //rating_back.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
            //frame.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
//            frame.addView(rating_back);
  //          frame.addView(average);
//            setContentView(frame);
            moTitle.setText(movie.getTitle());
            synopsis.setText(movie.getOverview());
            d=Double.parseDouble(movie.getRating());
            average.setText(movie.getRating());
            vote_count.setText(movie.getVoteCount());
            mLang.setText(movie.getLang());
            DecimalFormat df2 = new DecimalFormat(".##");
            d=Double.parseDouble(movie.getPopularity());
            d=Math.round(d*100.00)/100.00;
            String value= String.valueOf(d);
            mPop.setText(value);
            //id.setText(mId);
            getSupportActionBar().setTitle(movie.getTitle());
            rate=(float)d;

            Log.i("test", String.valueOf(rate));
    //        r.setRating(rate / 2);

            mId=movie.getId();
            share.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String shareBody = "Here is the share content body";
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mTitle);
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mOverview);
            startActivity(Intent.createChooser(sharingIntent,"Share Via"));
        }
    });
        fetchdata();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                //m=realm.createObject(FavMovies.class);
                //  Toast.makeText(getBaseContext(),String.valueOf(mf.getFav()),Toast.LENGTH_SHORT).show();
                if(mf.getFav()==0) {
                    realm.commitTransaction();
                    fetchdata1();
                }
                else if(mf.getFav()==1)
                {

                    realm.commitTransaction();
                    fetchdata2();
                }

            }
        });



            String url = "https://api.themoviedb.org/3/movie/"+mId+"/videos?api_key="+API_KEY;


            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                JSONArray json = response.getJSONArray("results");
                                if(json.length()<=0){
                                    findViewById(R.id.imgPlay).setVisibility(View.GONE);
                                }
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject jsonObject = json.getJSONObject(i);

                                     key = jsonObject.getString("key");
                                    videokey.add(key);

                                    String name = jsonObject.getString("name");
                                    trailerInfo.add(name);
                                }
                                for(int i=0;i<videokey.size();i++)
                                {
                                    String url3="http://img.youtube.com/vi/"+videokey.get(i)+"/default.jpg";
                                    thumb_img.add(url3);
                                }
                                if(thumb_img.size()!=0 && trailerInfo.size()!=0) {
                                    mAdapter = new MyAdapter3(thumb_img, trailerInfo);
                                    mRecyclerView.setAdapter(mAdapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Something went wrong!!please check your connection 111", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went wrong!!please check your connection", Toast.LENGTH_SHORT).show();
                        }
                    });

            Volley.newRequestQueue(getApplicationContext()).add(jsonRequest);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener2(getApplicationContext(), new RecyclerItemClickListener2.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String video_path = "https://www.youtube.com/watch?v="+videokey.get(position);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video_path));
                        startActivity(intent);

                    }
                }
                ));

        url = "https://api.themoviedb.org/3/movie/"+mId+"/reviews?api_key="+API_KEY;

        JsonObjectRequest jsonRequest1 = new JsonObjectRequest
                (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONArray json = response.getJSONArray("results");
                            for (int i = 0; i < json.length(); i++) {
                                JSONObject jsonObject = json.getJSONObject(i);

                                reviewId.add(jsonObject.getString("id"));
                                reviewText.add(jsonObject.getString("content"));
                                reviewAuthor.add(jsonObject.getString("author"));
                            }
                            if(reviewAuthor.size()!=0 && reviewText.size()!=0) {
                                mAuthor.setText(reviewAuthor.get(0));
                                mText.setText(reviewText.get(0));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Something went wrong!!please check your connection 111", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Something went wrong!!please check your connection", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getApplicationContext()).add(jsonRequest1);


    }

    public void fetchdata()
    {
        realm.beginTransaction();
        // FavMovies m=realm.createObject(FavMovies.class);
        //a=m.getMovies_id();
        m=realm.createObject(FavMovies.class);
        RealmResults<FavMovies> result3 = realm.where(FavMovies.class).equalTo("id", mId).findAll();
        if(result3.size()!=0)
            mf=result3.first().getObject();
        else
            mf=m;
        if(mf.getFav()==0) {
            share.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_off));
        }


        else if(mf.getFav()==1) {
            share.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.btn_star_big_on));
        }


        realm.commitTransaction();

        //fetchdata();
        // realm.cancelTransaction();


    }

    void fetchdata1()
    {
        realm.beginTransaction();
        // FavMovies m=realm.createObject(FavMovies.class);
        mf=realm.createObject(FavMovies.class);
        mf.setFav(1);
        mf.setId(movie.getId());
        mf.setObject(mf);
        mf.setBackdropImg(movie.getBackdropImg());
        mf.setOverview(movie.getOverview());
        mf.setPosterImg(movie.getImgMain());
        mf.setRelease_date(movie.getReleaseDate());
        mf.setTitle(movie.getTitle());
        mf.setVote_average(movie.getVoteCount());
        share.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
        realm.commitTransaction();
        // new Favourite().fetchsData();


    }


    void fetchdata2()
    {
        realm.beginTransaction();

        share.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));

        RealmResults<FavMovies> result2 = realm.where(FavMovies.class).findAll();
        mf.deleteFromRealm();
        //result2.deleteAllFromRealm();
        realm.commitTransaction();
        realm.beginTransaction();
        mf=realm.createObject(FavMovies.class);
        realm.commitTransaction();
//        new Favourite().fetchsData();


    }




}

 class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {
    private ArrayList<String> keylist;
    private ArrayList<String> namelist;
    private ArrayList<String> thumblist;
    public static Context context;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    int a,b;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        public TextView mTextView;
        public ImageView mImageView;
        public ImageView mKeyView;


        public ViewHolder(View v) {
            super(v);

            mImageView= (ImageView) v.findViewById(R.id.trailer_image);
            mTextView= (TextView) v.findViewById(R.id.title_text);
            context = v.getContext();

        }

        @Override
        public void onClick(View view) {
            Log.i("MyActivity", "onClick " + getAdapterPosition() + " ");
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter3(ArrayList<String> myDataset,ArrayList<String> myDataset2){
        this.thumblist = myDataset;
        this.namelist= myDataset2;



    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_layout, parent, false);

        ViewHolder vh = new ViewHolder((View) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Glide.with(context).load(Uri.parse(thumblist.get(position))).error(R.drawable.placeholder).into(holder.mImageView);
        holder.mTextView.setText(namelist.get(position));

    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return thumblist.size();
    }

}
