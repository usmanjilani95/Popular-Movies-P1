package app.usman.popular_movies;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
/**
 * Created by Usman Ahmad Jilani on 11-04-2016.
 */
public class popular  extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter1;
    private GridLayoutManager mLayoutManager;
    final ArrayList<String> imglist = new ArrayList<String>();
    final ArrayList<String> titlelist = new ArrayList<String>();
    final ArrayList<String> back_imglist = new ArrayList<String>();
    final ArrayList<String> plotlist = new ArrayList<String>();
    final ArrayList<String> user_ratinglist = new ArrayList<String>();
    final ArrayList<String> release_datelist = new ArrayList<String>();
    final ArrayList<String> idlist = new ArrayList<String>();
    final ArrayList<String> id_list = new ArrayList<String>();
    JazzyRecyclerViewScrollListener jazzyScrollListener;
    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    String API_KEY,Sortpopular;
    Button top_rated,pop,fav;
    ArrayList<Movie> movies=new ArrayList<>();
    FragmentManager fm;

    public popular() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);

        API_KEY = getString(R.string.API_KEY);
        Sortpopular = getString(R.string.Sortpopular);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        top_rated = (Button) view.findViewById(R.id.item1);
        pop = (Button) view.findViewById(R.id.item4);
        fav = (Button) view.findViewById(R.id.item2);
        ButterKnife.bind(this, view);
        mRecyclerView.setHasFixedSize(true);
        fm = getFragmentManager();
        if (getResources().getConfiguration().orientation == 2) {
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
            mRecyclerView.setLayoutManager(mLayoutManager);
            String url = "https://api.themoviedb.org/3" + Sortpopular + "?api_key=" + API_KEY;


            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                JSONArray json = response.getJSONArray("results");
                                for (int i = 0; i < json.length(); i++) {


                                    JSONObject jsonObject = json.getJSONObject(i);
                                    Movie movie = new Movie(jsonObject.getString("id"),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("overview"),
                                            jsonObject.getString("backdrop_path"),
                                            jsonObject.getString("poster_path"),
                                            jsonObject.getString("popularity"),
                                            jsonObject.getString("release_date"),
                                            jsonObject.getString("vote_count"),
                                            jsonObject.getString("vote_average"),
                                            jsonObject.getString("original_language"));
                                    movies.add(movie);
                                    imglist.add(movie.getImgMain());


//                                    String movy = jsonObject.getString("poster_path");
//                                    String site = "http://image.tmdb.org/t/p/w300/" + movy;
//                                    imglist.add(site);
//                                    String title = jsonObject.getString("title");
//                                    titlelist.add(title);
//                                    String backdrop_path = jsonObject.getString("backdrop_path");
//                                    String back_img = "http://image.tmdb.org/t/p/w780/" + backdrop_path;
//                                    back_imglist.add(back_img);
//                                    String plot = jsonObject.getString("overview");
//                                    plotlist.add(plot);
//                                    String user_rating = jsonObject.getString("vote_average");
//                                    user_ratinglist.add(user_rating);
//                                    String release_date = jsonObject.getString("release_date");
//                                    release_datelist.add(release_date);
//                                    String id = jsonObject.getString("id");
//                                    idlist.add(id);
                                }
                                mAdapter1 = new MyAdapter1(imglist);
                                mRecyclerView.setAdapter(mAdapter1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Something went wrong!!please check your connection 111", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getActivity(), "Something went wrong!!please check your connection", Toast.LENGTH_SHORT).show();
                        }
                    });

            Volley.newRequestQueue(getActivity()).add(jsonRequest);

            mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    // do something...
                    String url = "https://api.themoviedb.org/3" + Sortpopular + "?page=" + current_page + "&&api_key=" + API_KEY;


                    JsonObjectRequest jsonRequest = new JsonObjectRequest
                            (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // the response is already constructed as a JSONObject!
                                    try {
                                        JSONArray json = response.getJSONArray("results");
                                        for (int i = 0; i < json.length(); i++) {
                                            JSONObject jsonObject = json.getJSONObject(i);
                                            Movie movie = new Movie(jsonObject.getString("id"),
                                                    jsonObject.getString("title"),
                                                    jsonObject.getString("overview"),
                                                    jsonObject.getString("backdrop_path"),
                                                    jsonObject.getString("poster_path"),
                                                    jsonObject.getString("popularity"),
                                                    jsonObject.getString("release_date"),
                                                    jsonObject.getString("vote_count"),
                                                    jsonObject.getString("vote_average"),
                                                    jsonObject.getString("original_language"));
                                            movies.add(movie);
                                            imglist.add(movie.getImgMain());
//                                            String movy = jsonObject.getString("poster_path");
//                                            String site = "http://image.tmdb.org/t/p/w300/" + movy;
//                                            imglist.add(site);
//                                            String title = jsonObject.getString("title");
//                                            titlelist.add(title);
//                                            String backdrop_path = jsonObject.getString("backdrop_path");
//                                            String back_img = "http://image.tmdb.org/t/p/w780/" + backdrop_path;
//                                            back_imglist.add(back_img);
//                                            String plot = jsonObject.getString("overview");
//                                            plotlist.add(plot);
//                                            String user_rating = jsonObject.getString("vote_average");
//                                            user_ratinglist.add(user_rating);
//                                            String release_date = jsonObject.getString("release_date");
//                                            release_datelist.add(release_date);
//                                            String id = jsonObject.getString("id");
//                                            idlist.add(id);
                                        }
                                        mAdapter1.notifyItemInserted(imglist.size() - 1);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(), "Something went wrong!!please check your connection 111", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(getActivity(), "Something went wrong!!please check your connection", Toast.LENGTH_SHORT).show();
                                }
                            });

                    Volley.newRequestQueue(getActivity()).add(jsonRequest);

                }
            });


            jazzyScrollListener = new JazzyRecyclerViewScrollListener();
            mRecyclerView.addOnScrollListener(jazzyScrollListener);
            jazzyScrollListener.setTransitionEffect(new GrowEffect());


            mRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void onItemClick(View view, int position) {
                            boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
                            if (dual_pane) {
                                Tab_description tabletDetailFragment = new Tab_description();
                                Bundle b1 = new Bundle();


                                b1.putString("title", titlelist.get(position));
                                b1.putString("b_img", back_imglist.get(position));
                                b1.putString("overview", plotlist.get(position));
                                b1.putString("vote", user_ratinglist.get(position));
                                b1.putString("r_date", release_datelist.get(position));
                                b1.putString("p_img", imglist.get(position));
                                b1.putString("id", id_list.get(position));
                                // b1.putString("genre", genre.get(position));
                                // b1.putInt("popularity", popularity.get(position));
                                // b1.putString("language", language.get(position));
                                tabletDetailFragment.setArguments(b1);
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.details_frag, getParentFragment());
                                ft.commit();


                            } else {

                                // TODO Handle item click
                                Intent intent = new Intent(getActivity(), Movie_description.class);
//                            intent.putExtra("title", titlelist.get(position));
//                            intent.putExtra("b_img",back_imglist.get(position));
//                            intent.putExtra("overview",plotlist.get(position));
//                            intent.putExtra("vote",user_ratinglist.get(position));
//                            intent.putExtra("r_date",release_datelist.get(position));
//                            intent.putExtra("p_img",imglist.get(position));
//                            intent.putExtra("id",idlist.get(position));
                                intent.putExtra("Movie", movies.get(position));
                                startActivity(intent);
                            }
                        }
                    })
            );

        } else if (getResources().getConfiguration().orientation == 1) {
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            String url = "https://api.themoviedb.org/3" + Sortpopular + "?api_key=" + API_KEY;


            JsonObjectRequest jsonRequest = new JsonObjectRequest
                    (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // the response is already constructed as a JSONObject!
                            try {
                                JSONArray json = response.getJSONArray("results");
                                for (int i = 0; i < json.length(); i++) {
                                    JSONObject jsonObject = json.getJSONObject(i);
                                    Movie movie = new Movie(jsonObject.getString("id"),
                                            jsonObject.getString("title"),
                                            jsonObject.getString("overview"),
                                            jsonObject.getString("backdrop_path"),
                                            jsonObject.getString("poster_path"),
                                            jsonObject.getString("popularity"),
                                            jsonObject.getString("release_date"),
                                            jsonObject.getString("vote_count"),
                                            jsonObject.getString("vote_average"),
                                            jsonObject.getString("original_language"));
                                    movies.add(movie);
                                    imglist.add(movie.getImgMain());
//                                    String movy = jsonObject.getString("poster_path");
//                                    String site = "http://image.tmdb.org/t/p/w185/" + movy;
//                                    imglist.add(site);
//                                    String title = jsonObject.getString("title");
//                                    titlelist.add(title);
//                                    String backdrop_path = jsonObject.getString("backdrop_path");
//                                    String back_img = "http://image.tmdb.org/t/p/w780/" + backdrop_path;
//                                    back_imglist.add(back_img);
//                                    String plot = jsonObject.getString("overview");
//                                    plotlist.add(plot);
//                                    String user_rating = jsonObject.getString("vote_average");
//                                    user_ratinglist.add(user_rating);
//                                    String release_date = jsonObject.getString("release_date");
//                                    release_datelist.add(release_date);
//                                    String id = jsonObject.getString("id");
//                                    idlist.add(id);
                                }
                                mAdapter1 = new MyAdapter1(imglist);
                                mRecyclerView.setAdapter(mAdapter1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "Something went wrong!!please check your connection 111", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getActivity(), "Something went wrong!!please check your connection", Toast.LENGTH_SHORT).show();
                        }
                    });

            Volley.newRequestQueue(getActivity()).add(jsonRequest);

            mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    // do something...
                    String url = "https://api.themoviedb.org/3" + Sortpopular + "?page=" + current_page + "&&api_key=" + API_KEY;


                    JsonObjectRequest jsonRequest = new JsonObjectRequest
                            (Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // the response is already constructed as a JSONObject!
                                    try {
                                        JSONArray json = response.getJSONArray("results");
                                        for (int i = 0; i < json.length(); i++) {
                                            JSONObject jsonObject = json.getJSONObject(i);
                                            Movie movie = new Movie(jsonObject.getString("id"),
                                                    jsonObject.getString("title"),
                                                    jsonObject.getString("overview"),
                                                    jsonObject.getString("backdrop_path"),
                                                    jsonObject.getString("poster_path"),
                                                    jsonObject.getString("popularity"),
                                                    jsonObject.getString("release_date"),
                                                    jsonObject.getString("vote_count"),
                                                    jsonObject.getString("vote_average"),
                                                    jsonObject.getString("original_language"));
                                            movies.add(movie);
                                            imglist.add(movie.getImgMain());
//                                            String movy = jsonObject.getString("poster_path");
//                                            String site = "http://image.tmdb.org/t/p/w185/" + movy;
//                                            imglist.add(site);
//                                            String title = jsonObject.getString("title");
//                                            titlelist.add(title);
//                                            String backdrop_path = jsonObject.getString("backdrop_path");
//                                            String back_img = "http://image.tmdb.org/t/p/w780/" + backdrop_path;
//                                            back_imglist.add(back_img);
//                                            String plot = jsonObject.getString("overview");
//                                            plotlist.add(plot);
//                                            String user_rating = jsonObject.getString("vote_average");
//                                            user_ratinglist.add(user_rating);
//                                            String release_date = jsonObject.getString("release_date");
//                                            release_datelist.add(release_date);
//                                            String id = jsonObject.getString("id");
//                                            idlist.add(id);
                                        }
                                        mAdapter1.notifyItemInserted(imglist.size() - 1);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getActivity(), "Something went wrong!!please check your connection 111", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    Toast.makeText(getActivity(), "Something went wrong!!please check your connection", Toast.LENGTH_SHORT).show();
                                }
                            });

                    Volley.newRequestQueue(getActivity()).add(jsonRequest);

                }
            });


            jazzyScrollListener = new JazzyRecyclerViewScrollListener();
            mRecyclerView.addOnScrollListener(jazzyScrollListener);
            jazzyScrollListener.setTransitionEffect(new GrowEffect());


            mRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click
                            boolean dual_pane = getResources().getBoolean(R.bool.dual_pane);
                            if (dual_pane) {
                                Tab_description tabletDetailFragment = new Tab_description();
                                Bundle b1 = new Bundle();


                                b1.putString("title", titlelist.get(position));
                                b1.putString("b_img", back_imglist.get(position));
                                b1.putString("overview", plotlist.get(position));
                                b1.putString("vote", user_ratinglist.get(position));
                                b1.putString("r_date", release_datelist.get(position));
                                b1.putString("p_img", imglist.get(position));
                                b1.putString("id", id_list.get(position));
                                //b1.putString("genre", genre.get(position));
                                //b1.putInt("popularity", popularity.get(position));
                                //b1.putString("language", language.get(position));
                                tabletDetailFragment.setArguments(b1);
                                FragmentTransaction ft = fm.beginTransaction();
                                ft.replace(R.id.details_frag, getParentFragment());
                                ft.commit();


                            } else {
                                Intent intent = new Intent(getActivity(), Movie_description.class);
//                            intent.putExtra("title", titlelist.get(position));
//                            intent.putExtra("b_img",back_imglist.get(position));
//                            intent.putExtra("overview",plotlist.get(position));
//                            intent.putExtra("vote",user_ratinglist.get(position));
//                            intent.putExtra("r_date",release_datelist.get(position));
//                            intent.putExtra("p_img",imglist.get(position));
//                            intent.putExtra("id",idlist.get(position));
                                intent.putExtra("Movie", movies.get(position));
                                startActivity(intent);
                            }
                        }
                    })
            );

        }



        top_rated.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainActivity.class);

                startActivity(intent);
            }



        });
        pop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), popular.class);

                startActivity(intent);
            }



        });
        fav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Favourite.class);

                startActivity(intent);
            }



        });
        return view;
    }
    @OnClick(R.id.tapBarMenu) public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({  R.id.item1,R.id.item2 ,R.id.item4 }) public void onMenuItemClick(View view) {
        tapBarMenu.close();

    }



}

