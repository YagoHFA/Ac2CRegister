package com.example.register;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.model.Aluno;
import com.example.register.model.AlunoAdapter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class alunoList extends AppCompatActivity {

    private RecyclerView recyclerViewStudents;
    private AlunoAdapter studentAdapter;
    private List<Aluno> studentList;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_list);

        client = new OkHttpClient();
        recyclerViewStudents = findViewById(R.id.recyclerViewStudents);
        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));

        studentList = new ArrayList<>();
        studentAdapter = new AlunoAdapter(studentList);
        recyclerViewStudents.setAdapter(studentAdapter);

        fetchStudents();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void fetchStudents() {
        Request request = new Request.Builder()
                .url("https://6654d5953c1d3b602937893d.mockapi.io/Aluno")
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
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Aluno aluno = new Aluno(
                                    jsonObject.getInt("RA"),
                                    jsonObject.getString("Name"),
                                    jsonObject.getString("CEP"),
                                    jsonObject.getString("Logradouro"),
                                    jsonObject.getString("Complemento"),
                                    jsonObject.getString("Bairro"),
                                    jsonObject.getString("Cidade"),
                                    jsonObject.getString("UF")
                            );
                            studentList.add(aluno);
                        }
                        runOnUiThread(() -> studentAdapter.notifyDataSetChanged());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
