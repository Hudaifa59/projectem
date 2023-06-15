package com.example.projectem;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectem.users.FirebaseServices;
import com.example.projectem.users.Profile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderBoard extends Fragment {
    private ArrayList<com.example.projectem.users.Profile> Profile;
    private RecyclerView recyclerViewprofile;
    LeaderBoardAdapter leaderBoardAdapte;
    ArrayList<Profile> profileArrayList;
    FirebaseServices fbs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LeaderBoard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderBoard newInstance(String param1, String param2) {
        LeaderBoard fragment = new LeaderBoard();
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
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomponents();
    }

    private void connectcomponents() {

        recyclerViewprofile = getActivity().findViewById(R.id.RVLeaderBoard);
        recyclerViewprofile.setHasFixedSize(true);
        recyclerViewprofile.setLayoutManager(new LinearLayoutManager(getActivity()));

        fbs = FirebaseServices.getInstance();
        profileArrayList = new ArrayList<Profile>();
        leaderBoardAdapte = new LeaderBoardAdapter(profileArrayList,getContext());
        recyclerViewprofile.setAdapter(leaderBoardAdapte);
        EventChangeListener();
    }

    private void EventChangeListener() {
        fbs.getFire().collection("Profiles")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Profile> taskArrayList = new ArrayList<Profile>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Profile task = document.toObject(Profile.class);
                            taskArrayList.add(task);
                        }
                        leaderBoardAdapte = new LeaderBoardAdapter(taskArrayList, getActivity());
                        recyclerViewprofile.setAdapter(leaderBoardAdapte);
                    }
                });
    }
}