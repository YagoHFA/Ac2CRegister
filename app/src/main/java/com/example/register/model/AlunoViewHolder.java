package com.example.register.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.register.R;

public class AlunoViewHolder extends RecyclerView.ViewHolder {
    TextView textViewRA, textViewNome, textViewEndereco, textViewCidade, textViewUF;

    public AlunoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewRA = itemView.findViewById(R.id.textViewRA);
        textViewNome = itemView.findViewById(R.id.textViewNome);
        textViewEndereco = itemView.findViewById(R.id.textViewEndereco);
        textViewCidade = itemView.findViewById(R.id.textViewCidade);
        textViewUF = itemView.findViewById(R.id.textViewUF);
    }
}
