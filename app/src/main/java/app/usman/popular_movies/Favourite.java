package app.usman.popular_movies;

/**
 * Created by Usman Ahmad Jilani on 22-05-2016.
 */

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.twotoasters.jazzylistview.effects.HelixEffect;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import java.util.ArrayList;

import app.usman.popular_movies.Realm.FavMovies;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class Favourite extends Fragment {
    RealmConfiguration realmConfig;
    // Get a Realm instance for this thread
    Realm realm;
    View v;
    RecyclerView mRecyclerView;
    String API_KEY;
    FragmentManager fm;

    GridLayoutManager mLayoutManager;
    private Adapter mAdapter;
    final ArrayList<String> imglist = new ArrayList<String>();
    final ArrayList<String> titlelist = new ArrayList<String>();
    final ArrayList<String> back_imglist = new ArrayList<String>();
    final ArrayList<String> plotlist = new ArrayList<String>();
    final ArrayList<String> user_ratinglist = new ArrayList<String>();
    final ArrayList<String> release_datelist = new ArrayList<String>();
    final ArrayList<String> id_list = new ArrayList<String>();
    JazzyRecyclerViewScrollListener jazzyScrollListener;
    ArrayList<Movie> movies=new ArrayList<>();
    @Bind(R.id.tapBarMenu)
    TapBarMenu tapBarMenu;
    Button top_rated,pop,fav;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realmConfig = new RealmConfiguration.Builder(getActivity()).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);


    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_main, container, false);
        realmConfig = new RealmConfiguration.Builder(getActivity()).deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfig);
        top_rated= (Button) v.findViewById(R.id.item1);
        pop= (Button) v.findViewById(R.id.item4);
        fav= (Button) v.findViewById(R.id.item2);
        ButterKnife.bind(this,v);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        setHasOptionsMenu(true);
        mRecyclerView.setHasFixedSize(true);

        fm= getFragmentManager();
        API_KEY=getActivity().getResources().getString(R.string.API_KEY);


        fetchsData();
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
//                            b1.putString("genre", genre.get(position));
//                            b1.putInt("popularity", popularity.get(position));
//                            b1.putString("language", language.get(position));
                            tabletDetailFragment.setArguments(b1);
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.details_frag,getParentFragment());
                            ft.commit();


                        } else {

                            Intent intent = new Intent(getActivity(), Movie_description.class);

//                        intent.putExtra("title", titlelist.get(position));
//                        intent.putExtra("b_img", back_imglist.get(position));
//                        intent.putExtra("overview", plotlist.get(position));
//                        intent.putExtra("vote", user_ratinglist.get(position));
//                        intent.putExtra("r_date", release_datelist.get(position));
//                        intent.putExtra("p_img", imglist.get(position));
//                        intent.putExtra("id", id_list.get(position));
                            intent.putExtra("Movie", movies.get(position));
                            startActivity(intent);
                        }
                    }
                })
        );
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
                    return v;

                }



    @OnClick(R.id.tapBarMenu) public void onMenuButtonClick() {
        tapBarMenu.toggle();
    }

    @OnClick({  R.id.item1,R.id.item2 ,R.id.item4 }) public void onMenuItemClick(View view) {
        tapBarMenu.close();

    }
    @Nullable
    void fetchsData()
    {
       imglist.clear();
//        titlelist.clear();
//        back_imglist.clear();
//        plotlist.clear();
//        user_ratinglist.clear();
//        release_datelist.clear();
//        id_list.clear();
        movies.clear();
        if (getResources().getConfiguration().orientation == 2) {
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new MyAdapter1(imglist);
            mRecyclerView.setAdapter(mAdapter);

            RealmResults<FavMovies> result3 = realm.where(FavMovies.class).equalTo("fav", 1).findAll();
            if(result3.size()!=0)
            {
                for (int i=0;i<result3.size();i++)
                {
                    Movie movie=new Movie(result3.get(i).getId(),
                            result3.get(i).getTitle(),
                            result3.get(i).getOverview(),
                            result3.get(i).getBackdropImg(),
                            result3.get(i).getPosterImg(),
                            result3.get(i).getVote_average(),
                            result3.get(i).getRelease_date(),
                            result3.get(i).getVote_average(),
                            result3.get(i).getVote_average(),
                            result3.get(i).getVote_average());
                    movies.add(movie);
                    imglist.add(movie.getImgMain());
//                    imglist.add(result3.get(i).getPosterImg());
//                    titlelist.add(result3.get(i).getTitle());
//                    back_imglist.add(result3.get(i).getBackdropImg());
//                    plotlist.add(result3.get(i).getOverview());
//                    user_ratinglist.add(result3.get(i).getVote_average());
//                    release_datelist.add(result3.get(i).getRelease_date());
//                    id_list.add(result3.get(i).getId());
                }
            }
            mAdapter.notifyDataSetChanged();
//            mAdapter = new MyAdapter1(imglist);
//            mRecyclerView.setAdapter(mAdapter);

            jazzyScrollListener = new JazzyRecyclerViewScrollListener();
            mRecyclerView.addOnScrollListener(jazzyScrollListener);
            jazzyScrollListener.setTransitionEffect(new HelixEffect());


        }
        else if (getResources().getConfiguration().orientation == 1) {
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyAdapter1(imglist);
            mRecyclerView.setAdapter(mAdapter);

            RealmResults<FavMovies> result3 = realm.where(FavMovies.class).equalTo("fav", 1).findAll();
            if(result3.size()!=0)
            {
                for (int i=0;i<result3.size();i++)
                {
                    Movie movie=new Movie(result3.get(i).getId(),
                            result3.get(i).getTitle(),
                            result3.get(i).getOverview(),
                            result3.get(i).getBackdropImg(),
                            result3.get(i).getPosterImg(),
                            result3.get(i).getVote_average(),
                            result3.get(i).getRelease_date(),
                            result3.get(i).getVote_average(),
                            result3.get(i).getVote_average(),
                            result3.get(i).getVote_average());
                    movies.add(movie);
                    imglist.add(movie.getImgMain());
//                    imglist.add(result3.get(i).getPosterImg());
//                    titlelist.add(result3.get(i).getTitle());
//                    back_imglist.add(result3.get(i).getBackdropImg());
//                    plotlist.add(result3.get(i).getOverview());
//                    user_ratinglist.add(result3.get(i).getVote_average());
//                    release_datelist.add(result3.get(i).getRelease_date());
//                    id_list.add(result3.get(i).getId());
                }
            }
            mAdapter.notifyDataSetChanged();
//            mAdapter = new MyAdapter1(imglist);
//            mRecyclerView.setAdapter(mAdapter);


            jazzyScrollListener = new JazzyRecyclerViewScrollListener();
            mRecyclerView.addOnScrollListener(jazzyScrollListener);
            jazzyScrollListener.setTransitionEffect(new HelixEffect());


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mAdapter!=null) {

            fetchsData();


        }
    }
}

