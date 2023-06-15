package com.example.projectem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectem.users.FirebaseServices;
import com.example.projectem.users.Task;
import com.example.projectem.users.TaskAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profiles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profiles extends Fragment {
    private ArrayList<Task> taskArrayList;

    private RecyclerView recyclerViewprofile;
    TaskAdapter taskAdapter;
    FirebaseServices fbs;
    private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profiles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profiles.
     */
    // TODO: Rename and change types and number of parameters
    public static Profiles newInstance(String param1, String param2) {
        Profiles fragment = new Profiles();
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
        return inflater.inflate(R.layout.fragment_profiles, container, false);
    }
/*    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewprofile = view.findViewById(R.id.recycleprofile);
        recyclerViewprofile.setLayoutManager(new LinearLayoutManager(getActivity()));
        profileAdapter = new ProfileAdapter(new ArrayList<>());
        recyclerViewprofile.setAdapter(profileAdapter);
        EventChangeListener();
    }/*

 */
    @Override
    public void onStart() {
        super.onStart();
        recyclerViewprofile = getActivity().findViewById(R.id.recycleprofile);
        recyclerViewprofile.setHasFixedSize(true);
        recyclerViewprofile.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskAdapter = new TaskAdapter(new ArrayList<>(),getActivity());
        recyclerViewprofile.setAdapter(taskAdapter);
        fbs=FirebaseServices.getInstance();
        EventChangeListener();
    }

    private void EventChangeListener() {
        fbs.getFire().collection("Profiles")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        taskArrayList = new ArrayList<Task>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Task task = document.toObject(Task.class);
                                taskArrayList.add(task);
                        }

                        // Create adapter and set it to RecyclerView
                        taskAdapter = new TaskAdapter(taskArrayList,getActivity());
                        recyclerViewprofile.setAdapter(taskAdapter);
                    }
                });


    }
}
/*

           fbs.getFire().collection("Profiles").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Fetching data....");
                progressDialog.show();

                if (error!=null){

                    if (progressDialog.isShowing())
                    progressDialog.dismiss();

                    Log.e("FireStore error",error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){

                    if (dc.getType()==DocumentChange.Type.ADDED){

                        profileArrayList.add(dc.getDocument().toObject(Profile.class));

                    }

                    profileAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing())
                    progressDialog.dismiss();



                }
            }
        });
*/