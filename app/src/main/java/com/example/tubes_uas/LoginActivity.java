package com.example.tubes_uas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tubes_uas.Api.ApiClient;
import com.example.tubes_uas.Api.ApiInterface;
import com.example.tubes_uas.Model.UserDAO;
import com.example.tubes_uas.Model.UserResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private ProgressDialog progressDialog;

    private Response<UserResponse> response;

//    UserDAO userLogin;
    List<UserDAO> userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                login();
            }
        });
    }

    private void login() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> call = apiService.loginUser(etEmail.getText().toString(),
                etPassword.getText().toString());

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.body().getMessage().equals("Authenticated")) {
                    if(etEmail.getText().toString().equals("admin") && etPassword.getText().toString().equals("admin")) {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        userLogin = response.body().getUsers();
//                        final UserDAO user = userLogin.get(0);
                        if (response.body().getUsers() != null){
                            Toast.makeText(LoginActivity.this, "isi", Toast.LENGTH_SHORT).show();
                            String orang = String.valueOf(response.body().getUser());
                            Toast.makeText(LoginActivity.this, orang, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "kosong", Toast.LENGTH_SHORT).show();
                        }
//                        Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("id", user.getId());
//                        Toast.makeText(LoginActivity.this, user.getId(), Toast.LENGTH_SHORT).show();
//                        i.putExtras(bundle);
//                        startActivity(i);
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}