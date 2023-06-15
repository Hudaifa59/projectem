package com.example.projectem;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarFragment extends Fragment {
    GifImageView iv ;
    ImageView bluetooth ;
    Button btn ;
    TextView mpg , distance , co2 ;
    int i = 0 , l = 0 ;
    final Handler handler = new Handler();
    ConstraintLayout cs ;
    Button btn1 ;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarFragment newInstance(String param1, String param2) {
        CarFragment fragment = new CarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car, container, false);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {
        iv = getView().findViewById(R.id.IVCar);
        cs = getView().findViewById(R.id.lol);
        bluetooth = getView().findViewById(R.id.IVBluetooth);
        mpg = getView().findViewById(R.id.TVMPG);
        distance = getView().findViewById(R.id.TVDistance);
        co2 = getView().findViewById(R.id.TVCO2);
        mpg.setVisibility(View.INVISIBLE);
        distance.setVisibility(View.INVISIBLE);
        co2.setVisibility(View.INVISIBLE);
        btn1 = getView().findViewById(R.id.BTNStart);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        mpg.setVisibility(View.VISIBLE);
                        distance.setVisibility(View.VISIBLE);
                        co2.setVisibility(View.VISIBLE);
                    }
                }, 5000);
                TranslateAnimation animation = new TranslateAnimation(0.0f, 800.0f,
                        0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
                animation.setDuration(2500);  // animation duration
                animation.setRepeatCount(1);  // animation repeat count
                animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
                //animation.setFillAfter(true);

                iv.startAnimation(animation);  // start animation
            }
        });
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity2.class);
                startActivity(intent);
            }
        });
    }
}