package app.popular_movies.usman.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.twotoasters.jazzylistview.effects.GrowEffect;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;
    final ArrayList<String> imglist = new ArrayList<String>();
    final ArrayList<String> titlelist = new ArrayList<String>();
    final ArrayList<String> back_imglist = new ArrayList<String>();
    final ArrayList<String> plotlist = new ArrayList<String>();
    final ArrayList<String> user_ratinglist = new ArrayList<String>();
    final ArrayList<String> release_datelist = new ArrayList<String>();
    JazzyRecyclerViewScrollListener jazzyScrollListener;
    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    String API_KEY,Sorttop_rated;
    Button top_rated,pop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        API_KEY=getString(R.string.API_KEY);
        Sorttop_rated=getString(R.string.Sorttop_rated);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        top_rated= (Button) findViewById(R.id.item1);
        pop= (Button) findViewById(R.id.item4);
        ButterKnife.bind(this);
        mRecyclerView.setHasFixedSize(true);
        if(getResources().getConfiguration().orientation== 2) {
            mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
            mRecyclerView.setLayoutManager(mLayoutManager);
            ButterKnife.bind(this);
            String url = "https://api.themoviedb.org/3"+Sorttop_rated+"?api_key="+API_KEY;


            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                JSONArray json = response.getJSONArray("results");
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject jsonObject = json.getJSONObject(i);
                                    String movy = jsonObject.getString("poster_path");
                                    String site = "http://image.tmdb.org/t/p/w300/" + movy;
                                    imglist.add(site);
                                    String title = jsonObject.getString("title");
                                    titlelist.add(title);
                                    String backdrop_path = jsonObject.getString("backdrop_path");
                                    String back_img = "http://image.tmdb.org/t/p/w780/" + backdrop_path;
                                    back_imglist.add(back_img);
                                    String plot = jsonObject.getString("overview");
                                    plotlist.add(plot);
                                    String user_rating = jsonObject.getString("vote_average");
                                    user_ratinglist.add(user_rating);
                                    String release_date = jsonObject.getString("release_date");
                                    release_datelist.add(release_date);
                                }
                                mAdapter = new MyAdapter(imglist);
                                mRecyclerView.setAdapter(mAdapter);
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

            mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    // do something...
                    String url = "https://api.themoviedb.org/3"+Sorttop_rated+"?page="+current_page+"&&api_key="+API_KEY;


                    JsonObjectRequest jsonRequest = new JsonObjectRequest
                            (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // the response is already constructed as a JSONObject!
                                    try {
                                        JSONArray json = response.getJSONArray("results");
                                        for (int i = 0; i < json.length(); i++) {
                                            JSONObject jsonObject = json.getJSONObject(i);
                                            String movy = jsonObject.getString("poster_path");
                                            String site = "http://image.tmdb.org/t/p/w185/" + movy;
                                            imglist.add(site);
                                            String title = jsonObject.getString("title");
                                            titlelist.add(title);
                                            String backdrop_path = jsonObject.getString("backdrop_path");
                                            String back_img = "http://image.tmdb.org/t/p/w780/" + backdrop_path;
                                            back_imglist.add(back_img);
                                            String plot = jsonObject.getString("overview");
                                            plotlist.add(plot);
                                            String user_rating = jsonObject.getString("vote_average");
                                            user_ratinglist.add(user_rating);
                                            String release_date = jsonObject.getString("release_date");
                                            release_datelist.add(release_date);
                                        }
                                        mAdapter.notifyItemInserted(imglist.size()-1);

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

                }
            });


            jazzyScrollListener = new JazzyRecyclerViewScrollListener();
            mRecyclerView.addOnScrollListener(jazzyScrollListener);
            jazzyScrollListener.setTransitionEffect(new GrowEffect());




            mRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener2(getApplicationContext(), new RecyclerItemClickListener2.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click

                        Intent intent = new Intent(getApplicationContext(), Movie_description.class);
                       intent.putExtra("title", titlelist.get(position));
                        intent.putExtra("b_img",back_imglist.get(position));
                        intent.putExtra("overview",plotlist.get(position));
                        intent.putExtra("vote",user_ratinglist.get(position));
                        intent.putExtra("r_date",release_datelist.get(position));
                        intent.putExtra("p_img",imglist.get(position));
                        startActivity(intent);
                        }
                    })
            );

        }
        else if(getResources().getConfiguration().orientation == 1) {
            mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            String url = "https://api.themoviedb.org/3"+Sorttop_rated+"?api_key="+API_KEY;
            ButterKnife.bind(this);

            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                JSONArray json = response.getJSONArray("results");
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject jsonObject = json.getJSONObject(i);
                                    String movy = jsonObject.getString("poster_path");
                                    String site = "http://image.tmdb.org/t/p/w185/" + movy;
                                    imglist.add(site);
                                    String title = jsonObject.getString("title");
                                    titlelist.add(title);
                                    String backdrop_path = jsonObject.getString("backdrop_path");
                                    String back_img = "http://image.tmdb.org/t/p/w780/" + backdrop_path;
                                    back_imglist.add(back_img);
                                    String plot = jsonObject.getString("overview");
                                    plotlist.add(plot);
                                    String user_rating = jsonObject.getString("vote_average");
                                    user_ratinglist.add(user_rating);
                                    String release_date = jsonObject.getString("release_date");
                                    release_datelist.add(release_date);
                                }
                                mAdapter = new MyAdapter(imglist);
                                mRecyclerView.setAdapter(mAdapter);
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

            mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    // do something...
                    String url = "https://api.themoviedb.org/3"+Sorttop_rated+"?page="+current_page+"&&api_key="+API_KEY;


                    JsonObjectRequest jsonRequest = new JsonObjectRequest
                            (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // the response is already constructed as a JSONObject!
                                    try {
                                        JSONArray json = response.getJSONArray("results");
                                        for (int i = 0; i < json.length(); i++) {
                                            JSONObject jsonObject = json.getJSONObject(i);
                                            String movy = jsonObject.getString("poster_path");
                                            String site = "http://image.tmdb.org/t/p/w185/" + movy;
                                            imglist.add(site);
                                            String title = jsonObject.getString("title");
                                            titlelist.add(title);
                                            String backdrop_path = jsonObject.getString("backdrop_path");
                                            String back_img = "http://image.tmdb.org/t/p/w780/" + backdrop_path;
                                            back_imglist.add(back_img);
                                            String plot = jsonObject.getString("overview");
                                            plotlist.add(plot);
                                            String user_rating = jsonObject.getString("vote_average");
                                            user_ratinglist.add(user_rating);
                                            String release_date = jsonObject.getString("release_date");
                                            release_datelist.add(release_date);
                                        }
                                        mAdapter.notifyItemInserted(imglist.size()-1);

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

                }
            });


            jazzyScrollListener = new JazzyRecyclerViewScrollListener();
            mRecyclerView.addOnScrollListener(jazzyScrollListener);
            jazzyScrollListener.setTransitionEffect(new GrowEffect());





            mRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener2(getApplicationContext(), new RecyclerItemClickListener2.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click

                      Intent intent = new Intent(getApplicationContext(), Movie_description.class);
                        intent.putExtra("title", titlelist.get(position));
                        intent.putExtra("b_img",back_imglist.get(position));
                        intent.putExtra("overview",plotlist.get(position));
                        intent.putExtra("vote",user_ratinglist.get(position));
                        intent.putExtra("r_date",release_datelist.get(position));
                        intent.putExtra("p_img",imglist.get(position));
                        startActivity(intent);
                        }
                    })
            );

        }

        top_rated.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }



        });
        pop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), popular.class);

                startActivity(intent);
            }



        });
    }

    @OnClick(R.id.tapBarMenu) public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({  R.id.item1, R.id.item4 }) public void onMenuItemClick(View view) {
        tapBarMenu.close();

    }

}









class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> imagelist;
    private ArrayList<String> titlelist;
    public static Context context;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    int a,b;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        public TextView mTextView;
        public ImageView mImageView;


        public ViewHolder(View v) {
            super(v);

           mImageView= (ImageView) v.findViewById(R.id.imview);
            context = v.getContext();

        }
        @Override
        public void onClick(View view) {
            Log.i("MyActivity", "onClick " + getAdapterPosition() + " ");
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<String> myDataset){
        imagelist = myDataset;




    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridlayout, parent, false);

        ViewHolder vh = new ViewHolder((View) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Glide.with(context).load(imagelist.get(position)).error(R.drawable.placeholder).into(holder.mImageView);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return imagelist.size();
    }

}

class RecyclerItemClickListener2 implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerItemClickListener2(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}

