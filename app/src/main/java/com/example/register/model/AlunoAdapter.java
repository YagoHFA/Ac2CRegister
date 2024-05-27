package com.example.register.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoViewHolder> {

    private List<Aluno> alunos;

    public AlunoAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_aluno_item, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.textViewRA.setText("RA: " + aluno.getRa());
        holder.textViewNome.setText("Nome: " + aluno.getNome());

        String enderecoText = "Endereço: " + aluno.getLogradouro();

        if (!aluno.getComplemento().isEmpty()) {
            enderecoText += ", " + aluno.getComplemento();
        } else {
            enderecoText += "";
        }

        enderecoText += ", " + aluno.getBairro();
        holder.textViewEndereco.setText(enderecoText);
        holder.textViewCidade.setText("Cidade: " + aluno.getCidade());
        holder.textViewUF.setText("UF: " + aluno.getUf());
    }


    @Override
    public int getItemCount() {
        return alunos.size();
    }
}
