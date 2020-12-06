package com.example.tubes_uas.CateringCRUD;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tubes_uas.Api.ApiClient;
import com.example.tubes_uas.Api.ApiInterface;
import com.example.tubes_uas.KosCRUD.CreateKosActivity;
import com.example.tubes_uas.Model.UserResponse;
import com.example.tubes_uas.R;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCateringActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private AutoCompleteTextView exposedDropdownHari, exposedDropdownPaket, exposedDropdownBulan;
    private MaterialButton btnCancel, btnCreate;
    private String sPaket = "", sHari = "", sBulan = "";
    private String[] saPaket = new String[] {"Nasi Babi", "Nasi Ayam", "Nasi Goreng"};
    private String[] saHari = new String[] {"1x Sehari", "2x Sehari", "3x Sehari"};
    private String[] saBulan = new String[] {"1 Minggu", "2 Minggu", "4 Minggu"};
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_catering);

        progressDialog = new ProgressDialog(this);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        exposedDropdownPaket = findViewById(R.id.edPaket);
        exposedDropdownHari = findViewById(R.id.edHari);
        exposedDropdownBulan = findViewById(R.id.edBulan);
        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnCreate);

        ArrayAdapter<String> adapterPaket = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, saPaket);
        exposedDropdownPaket.setAdapter(adapterPaket);
        exposedDropdownPaket.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sPaket = saPaket[i];
            }
        });

        ArrayAdapter<String> adapterHari = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, saHari);
        exposedDropdownHari.setAdapter(adapterHari);
        exposedDropdownHari.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sHari = saHari[i];
            }
        });

        ArrayAdapter<String> adapterBulan = new ArrayAdapter<>(Objects.requireNonNull(this),
                R.layout.list_item, R.id.item_list, saBulan);
        exposedDropdownBulan.setAdapter(adapterBulan);
        exposedDropdownBulan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sBulan = saBulan[i];
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
                if(sPaket.isEmpty()){
                    exposedDropdownPaket.setError("Isikan dengan benar", null);
                    exposedDropdownPaket.requestFocus();
                } else if(sHari.isEmpty()){
                    exposedDropdownHari.setError("Isikan dengan benar", null);
                    exposedDropdownHari.requestFocus();
                } else if(sBulan.isEmpty()){
                    exposedDropdownBulan.setError("Isikan dengan benar", null);
                    exposedDropdownBulan.requestFocus();
                }else {
                    progressDialog.show();
                    saveCatering();
                }
            }
        });
    }

    private void saveCatering() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<UserResponse> add = apiService.createCatering(sPaket, sHari, sBulan);

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Toast.makeText(CreateCateringActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(CreateCateringActivity.this, "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}