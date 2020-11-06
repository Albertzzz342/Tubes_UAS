package com.example.gd8_b_9912;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private MaterialTextView twNama, twNim, twFakultas, twProdi, twJenisKelamin;
    private String sIdUser, sNama, sNim, sFakultas, sProdi, sJenisKelamin;
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
        twNim = findViewById(R.id.twNim);
        twFakultas = findViewById(R.id.twFakultas);
        twProdi = findViewById(R.id.twProdi);
        twJenisKelamin = findViewById(R.id.twJenisKelamin);
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
                sNim = response.body().getUsers().get(0).getNim();
                sFakultas = response.body().getUsers().get(0).getFakultas();
                sProdi = response.body().getUsers().get(0).getProdi();
                sJenisKelamin = response.body().getUsers().get(0).getJenis_kelamin();

                twNama.setText(sNama);
                twNim.setText(sNim);
                twFakultas.setText(sFakultas);
                twProdi.setText(sProdi);
                twJenisKelamin.setText(sJenisKelamin);
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