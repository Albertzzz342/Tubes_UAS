package com.example.tubes_uas.UserCRUD;


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

import com.example.tubes_uas.Api.ApiClient;
import com.example.tubes_uas.Api.ApiInterface;
import com.example.tubes_uas.Model.UserResponse;
import com.example.tubes_uas.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserActivity extends AppCompatActivity {
    private ImageButton ibBack;
    private EditText etNama, etEmail, etPassword;
    private AutoCompleteTextView exposedDropdownFasilitas, exposedDropdownJenis, exposedDropdownLama;
    private RadioGroup rgJenisKelamin;
    private MaterialButton btnCancel, btnCreate;
    private String sJenis = "", sFasilitas = "", sLama = "";
    private String[] saJenis = new String[] {"Ekslusif", "Biasa"};
    private String[] saFasilitas = new String[] {"Kamar Mandi Dalam", "Kamar Mandi Luar"};
    private String[] saLama = new String[] {"1 Bulan", "6 Bulan", "1 Tahun"};
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
        etEmail = findViewById(R.id.etEmail);
        exposedDropdownJenis = findViewById(R.id.edJenis);
        exposedDropdownFasilitas = findViewById(R.id.edFasilitas);
        exposedDropdownLama = findViewById(R.id.edLama);
        etPassword = findViewById(R.id.etPassword);
        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);

        ArrayAdapter<String> adapterJenis = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, saJenis);
        exposedDropdownJenis.setAdapter(adapterJenis);
        exposedDropdownJenis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sJenis = saJenis[i];
            }
        });

        ArrayAdapter<String> adapterFasilitas = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, saFasilitas);
        exposedDropdownFasilitas.setAdapter(adapterFasilitas);
        exposedDropdownFasilitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sFasilitas = saFasilitas[i];
            }
        });

        ArrayAdapter<String> adapterLama = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, saLama);
        exposedDropdownLama.setAdapter(adapterLama);
        exposedDropdownLama.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sLama = saLama[i];
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNama.getText().toString().isEmpty()){
                    etNama.setError("Isikan dengan benar");
                    etNama.requestFocus();
                } else if(etEmail.getText().toString().isEmpty()){
                    etEmail.setError("Isikan dengan benar");
                    etEmail.requestFocus();
                } else if(sJenis.isEmpty()){
                    exposedDropdownJenis.setError("Isikan dengan benar", null);
                    exposedDropdownJenis.requestFocus();
                } else if(sFasilitas.isEmpty()){
                    exposedDropdownFasilitas.setError("Isikan dengan benar", null);
                    exposedDropdownFasilitas.requestFocus();
                } else if(sLama.isEmpty()){
                    exposedDropdownLama.setError("Isikan dengan benar", null);
                    exposedDropdownLama.requestFocus();
                }else if(etPassword.getText().toString().isEmpty()){
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
                etEmail.getText().toString(), sJenis, sFasilitas, sLama, etPassword.getText().toString());

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