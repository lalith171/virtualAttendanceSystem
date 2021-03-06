package com.example.attendancesystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link firstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class firstFragment extends Fragment {

    EditText memail, mpass;
    Button mlog;
    TextView forgotPassword;
    FirebaseAuth fAuth;
    private static View signinview;
    private static FragmentManager signinManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public firstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment firstFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static firstFragment newInstance(String param1, String param2) {
        firstFragment fragment = new firstFragment();
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
        signinview = inflater.inflate(R.layout.fragment_first, container, false);

        signinManager = getActivity().getSupportFragmentManager();
        memail = (EditText) signinview.findViewById(R.id.editLogin);
        forgotPassword = (TextView) signinview.findViewById(R.id.textView9);
        mpass = (EditText) signinview.findViewById(R.id.editPass);
        mlog = (Button) signinview.findViewById(R.id.button);
        fAuth = FirebaseAuth.getInstance();

        mlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = memail.getText().toString().trim();
                String password = mpass.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    memail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mpass.setError("Email is Required.");
                    return;
                }
                if (password.length() < 6) {
                    mpass.setError("Password Should be 6 Character and more");
                    return;
                }
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    memail.setError("Email is Required.");
                    mpass.setError("Password is Required.");
                }
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Logged in Successfully !!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), HomePage.class));
                        } else {
                            Toast.makeText(getActivity(), "Password Incorrect !!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());// for dialog box
                passwordResetDialog.setTitle("Password bhul gaya kyaa?");
                passwordResetDialog.setMessage("Idhar email id daal aur reset maar");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error ! Reset Link is Not Sent", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // empty method()
                    }
                });

                passwordResetDialog.create().show(); //get the email
            }
        });
        return signinview;
    }
}