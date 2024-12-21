package com.rodriguezruiz.pokedex.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.MainActivity;
import com.rodriguezruiz.pokedex.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsPokemonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsPokemonFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}