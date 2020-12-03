package com.example.tubes_uas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tubes_uas.Api.ApiClient;
import com.example.tubes_uas.Api.ApiInterface;
import com.example.tubes_uas.Model.UserDAO;
import com.example.tubes_uas.Model.UserResponse;
import com.example.tubes_uas.UserCRUD.EditUserActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private MaterialTextView twNama, twEmail, twFasilitas, twJenis, twLama;
    private String sIdUser, sNama, sEmail, sFasilitas, sJenis, sLama;
    private MaterialButton btnLogout, btnEdit;
    private ProgressDialog progressDialog;
    private List<UserDAO> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        twNama = findViewById(R.id.twNama);
        twEmail = findViewById(R.id.twEmail);
        twFasilitas = findViewById(R.id.twFasilitas);
        twJenis = findViewById(R.id.twJenis);
        twLama = findViewById(R.id.twLama);
        btnLogout = findViewById(R.id.btnLogout);
        btnEdit = findViewById(R.id.btnEdit);
        
        Bundle bundle = getIntent().getExtras();
        sIdUser = bundle.getString("id");
        loadUserById(sIdUser);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditUserActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("id", users.getId());
                Intent mIntent = getIntent();
                sIdUser = mIntent.getStringExtra("id");
                intent.putExtras(mIntent);
                startActivity(intent);
            }
        });
    }

    private void loadUserById(String sIdUser) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.getUserById(sIdUser, "data");

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                sNama = response.body().getUsers().get(0).getNama();
                sEmail = response.body().getUsers().get(0).getEmail();
                sFasilitas = response.body().getUsers().get(0).getFasilitas();
                sJenis = response.body().getUsers().get(0).getJenis();
                sLama = response.body().getUsers().get(0).getLama();

                twNama.setText(sNama);
                twEmail.setText(sEmail);
                twFasilitas.setText(sFasilitas);
                twJenis.setText(sJenis);
                twLama.setText(sLama);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}