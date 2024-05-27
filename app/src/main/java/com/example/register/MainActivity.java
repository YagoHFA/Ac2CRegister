package com.example.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRA, editTextNome, editTextCEP, editTextLogradouro, editTextComplemento, editTextBairro, editTextCidade, editTextUF;
    private Button buttonSave, buttonViewStudents;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRA = findViewById(R.id.editTextRA);
        editTextNome = findViewById(R.id.editTextNome);
        editTextCEP = findViewById(R.id.editTextCEP);
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextUF = findViewById(R.id.editTextUF);
        buttonSave = findViewById(R.id.buttonSave);
        buttonViewStudents = findViewById(R.id.buttonViewStudents);

        client = new OkHttpClient();

        editTextCEP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String cep = editTextCEP.getText().toString();
                    if (!cep.isEmpty()) {
                        fetchAddress(cep);
                    }
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent();
            }
        });

        buttonViewStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, alunoList.class);
                startActivity(intent);
            }
        });
    }

    private void fetchAddress(String cep) {
        Request request = new Request.Builder()
                .url("https://viacep.com.br/ws/" + cep + "/json/")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                        runOnUiThread(() -> {
                            try {
                                editTextLogradouro.setText(jsonObject.getString("logradouro"));
                                editTextComplemento.setText(jsonObject.getString("complemento"));
                                editTextBairro.setText(jsonObject.getString("bairro"));
                                editTextCidade.setText(jsonObject.getString("localidade"));
                                editTextUF.setText(jsonObject.getString("uf"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void saveStudent() {
        String ra = editTextRA.getText().toString();
        String nome = editTextNome.getText().toString();
        String cep = editTextCEP.getText().toString();
        String logradouro = editTextLogradouro.getText().toString();
        String complemento = editTextComplemento.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String uf = editTextUF.getText().toString();

        JSONObject alunoJson = new JSONObject();
        try {
            alunoJson.put("RA", ra);
            alunoJson.put("Name", nome);
            alunoJson.put("CEP", cep);
            alunoJson.put("Logradouro", logradouro);
            alunoJson.put("Complemento", complemento);
            alunoJson.put("Bairro", bairro);
            alunoJson.put("Cidade", cidade);
            alunoJson.put("UF", uf);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonString = alunoJson.toString();
        RequestBody body = RequestBody.create(jsonString, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("https://6654d5953c1d3b602937893d.mockapi.io/Aluno")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Falha ao cadastrar aluno", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Aluno cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Falha ao cadastrar aluno", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
}
