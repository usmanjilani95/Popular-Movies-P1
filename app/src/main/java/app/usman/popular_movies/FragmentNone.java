package app.usman.popular_movies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Usman Ahmad Jilani on 09-06-2016.
 */
public class FragmentNone extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.none_movie,container,false);
        return view;
    }
}
