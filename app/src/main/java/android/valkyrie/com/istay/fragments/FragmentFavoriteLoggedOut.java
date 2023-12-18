package android.valkyrie.com.istay.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.valkyrie.com.istay.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentFavoriteLoggedOut extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_logged_out, container, false);
    }
}
