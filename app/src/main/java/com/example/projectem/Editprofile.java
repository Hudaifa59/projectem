package com.example.projectem;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectem.users.FirebaseServices;
import com.example.projectem.users.Profile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Editprofile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Editprofile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Bitmap Image;
    private FirebaseServices fbs;
    private Profile pf;
    private Button btndone;
    private ImageView imgp;
    private EditText ename,ephone,ecar,eid;
    String path;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Editprofile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Editprofile.
     */
    // TODO: Rename and change types and number of parameters
    public static Editprofile newInstance(String param1, String param2) {
        Editprofile fragment = new Editprofile();
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
        return inflater.inflate(R.layout.fragment_editprofile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        connectcomp();
    }

    private void connectcomp() {
        fbs=FirebaseServices.getInstance();
        btndone=getView().findViewById(R.id.Update);
        imgp = getView().findViewById(R.id.upprofileimage);
        ename = getView().findViewById(R.id.upusername);
        ecar = getView().findViewById(R.id.upetcar);
        ephone = getView().findViewById(R.id.upephone);
        eid=getView().findViewById(R.id.upetid);
        imgp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=ephone.getText().toString();
                if ( ename.getText().toString().isEmpty() || eid.getText().toString().isEmpty()||ecar.getText().toString().isEmpty()||phone.isEmpty()) {
                    Toast.makeText(getActivity(), "There are some fields missing", Toast.LENGTH_SHORT).show();
                    return;
                }
                String path=UploadImageToFirebase();
                if(path==null)return;
                fbs.getFire().collection("Profile").whereEqualTo("email",fbs.getAuth().getCurrentUser().getEmail())
                        .get()
                        .addOnSuccessListener((QuerySnapshot querySnapshot) -> {
                            if (querySnapshot.isEmpty()) {
                                System.out.println("No users found.");
                                return;
                            }
                            System.out.println("Number of users: " + querySnapshot.size());

                            for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                                String userId = doc.getId();
                                Map<String, Object> updateFields = new HashMap<>();
                                updateFields.put("username",ename.getText().toString());
                                updateFields.put("phone",phone);
                                updateFields.put("carnumber",ecar.getText().toString());
                                updateFields.put("phone",ephone.getText().toString());
                                updateFields.put("image",path);
                                doc.getReference().update(updateFields)
                                        .addOnSuccessListener(aVoid -> {
                                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                            ft.replace(R.id.framehome,new Profilepage());
                                            ft.commit();
                                            System.out.println("ArrayList updated successfully.");
                                        })
                                        .addOnFailureListener(e -> {
                                            System.out.println("Error updating ArrayList: " + e.getMessage());
                                        });
                            }
                        })
                        .addOnFailureListener(e -> {
                            System.out.println("Error retrieving users: " + e.getMessage());
                        });

            }
        });

    }

    private String UploadImageToFirebase(){
        BitmapDrawable drawable = (BitmapDrawable) imgp.getDrawable();
        Image = drawable.getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Image.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[]data= baos.toByteArray();
        StorageReference ref =fbs.getStorage().getReference("listingPictures/"+ UUID.randomUUID().toString());
        UploadTask uploadTask =ref.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error with the picture", e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
        return ref.getPath();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&& data != null){
            Uri selectedImage= data.getData();
            imgp.setImageURI(selectedImage);
        }
    }
}