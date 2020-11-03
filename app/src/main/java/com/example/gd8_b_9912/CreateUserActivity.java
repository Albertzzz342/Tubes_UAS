package com.example.gd8_b_9912;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserActivity extends AppCompatActivity {
    private ImageButton ibBack;
    private EditText etNama, etNim, etPassword;
    private AutoCompleteTextView expossedDropdownFakultas, expossedDropdownProdi;
    private RadioGroup rgJenisKelamin;
    private MaterialButton btnCancel, btnCreate;
    private String sProdi = "", sFakultas = "", sJenisKelamin;
    private String[] saProdi = new String[] {"Informatika", "Manajemen", "Ilmu Komunikasi", "Ilmu Hukum"};
    private String[] saFakultas = new String[] {"FTI", "FBE", "FISIP", "FH"};
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        progressDialog = new ProgressDialog(this);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        expossedDropdownProdi = findViewById(R.id.edProdi);
        expossedDropdownFakultas = findViewById(R.id.edFakultas);
        rgJenisKelamin = findViewById(R.id.rgJenisKelamin);
        etPassword = findViewById(R.id.etPassword);
        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);

        ArrayAdapter<String> adapterProdi = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, saProdi);
        expossedDropdownProdi.setAdapter(adapterProdi);
        expossedDropdownProdi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sProdi = saProdi[i];
            }
        });

        ArrayAdapter<String> adapterFakultas = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, saFakultas);
        expossedDropdownFakultas.setAdapter(adapterFakultas);

        expossedDropdownFakultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sFakultas = saFakultas[i];
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rgJenisKelamin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbLakiLaki:
                        sJenisKelamin = "Laki-laki";
                        break;
                    case R.id.rbPerempuan:
                        sJenisKelamin = "Perempuan";
                        break;
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNama.getText().toString().isEmpty()){
                    etNama.setError("Isikan dengan benar");
                    etNama.requestFocus();
                } else if(etNim.getText().toString().isEmpty()){
                    etNim.setError("Isikan dengan benar");
                    etNim.requestFocus();
                } else if(sProdi.isEmpty()){
                    expossedDropdownProdi.setError("Isikan dengan benar", null);
                    expossedDropdownProdi.requestFocus();
                } else if(sFakultas.isEmpty()){
                    expossedDropdownFakultas.setError("Isikan dengan benar", null);
                    expossedDropdownFakultas.requestFocus();
                } else if(etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Isikan dengan benar");
                    etPassword.requestFocus();
                } else {
                    progressDialog.show();
                    saveUser();
                }
            }
        });
    }

    private void saveUser() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> add = apiService.createUser(etNama.getText().toString(),
                etNim.getText().toString(), sProdi, sFakultas, sJenisKelamin, etPassword.getText().toString());

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Toast.makeText(CreateUserActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(CreateUserActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}