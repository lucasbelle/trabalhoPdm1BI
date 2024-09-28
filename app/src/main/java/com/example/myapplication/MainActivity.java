package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Patterns;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextIdade, editTextDisciplina, editTextNota1, editTextNota2;
    private TextView textViewErro, textViewDados, textViewStatus;
    private Button buttonEnviar, buttonLimpar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextIdade = findViewById(R.id.editTextIdade);
        editTextDisciplina = findViewById(R.id.editTextDisciplina);
        editTextNota1 = findViewById(R.id.editTextNota1);
        editTextNota2 = findViewById(R.id.editTextNota2);
        textViewErro = findViewById(R.id.textViewErro);
        textViewDados = findViewById(R.id.textViewDados);
        textViewStatus = findViewById(R.id.textViewStatus);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        buttonLimpar = findViewById(R.id.buttonLimpar);

        //Botão de enviar;
        buttonEnviar.setOnClickListener(v -> validarCampos());

        //Botão de Limpar;
        buttonLimpar.setOnClickListener(v -> limparFormulario());
    }

    //Validando se os campos estão preenchidos;
    @SuppressLint("SetTextI18n")
    private void validarCampos() {
        String nome = editTextNome.getText().toString();
        String emailStr = editTextEmail.getText().toString();
        String idadeStr = editTextIdade.getText().toString();
        String disciplina = editTextDisciplina.getText().toString();
        String nota1Str = editTextNota1.getText().toString();
        String nota2Str = editTextNota2.getText().toString();

        textViewDados.setText("");
        textViewStatus.setText("");

        if (nome.isEmpty() || emailStr.isEmpty() || idadeStr.isEmpty() || disciplina.isEmpty() ||
                nota1Str.isEmpty() || nota2Str.isEmpty()) {
            textViewErro.setText("Todos os campos devem ser preenchidos!");
            return;
        }

        //Validação de e-mail;
        String email = editTextEmail.getText().toString();
        if (!isValidEmail(email)) {
            textViewErro.setText("E-mail inválido.");
            return;
        }

        //Validação de idade;
        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
            if (idade <= 0) {
                textViewErro.setText("A idade deve ser um número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            textViewErro.setText("Idade inválida.");
            return;
        }

        //Validação das notas;
        double nota1, nota2;
        try {
            nota1 = Double.parseDouble(nota1Str);
            nota2 = Double.parseDouble(nota2Str);
            if (nota1 < 0 || nota1 > 10 || nota2 < 0 || nota2 > 10) {
                textViewErro.setText("As notas devem estar entre 0 e 10.");
                return;
            }
        } catch (NumberFormatException e) {
            textViewErro.setText("Notas inválidas.");
            return;
        }

        //Cálculo da média;
        double media = (nota1 + nota2) / 2;
        String status = media >= 6 ? "Aprovado" : "Reprovado";

        textViewErro.setText("");

        //Exibir os dados;
        textViewDados.setText("Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Idade: " + idade + "\n" +
                "Disciplina: " + disciplina + "\n" +
                "Notas: " + nota1 + ", " + nota2 + "\n" +
                "Média: " + media);

        //Exibir status;
        if (status.equals("Aprovado")) {
            textViewStatus.setText("Aprovado");
            textViewStatus.setTextColor(Color.parseColor("#32CD32"));
        } else {
            textViewStatus.setText("Reprovado");
            textViewStatus.setTextColor(Color.RED);
        }
    }

    //Limpar formulário;
    private void limparFormulario() {
        editTextNome.setText("");
        editTextEmail.setText("");
        editTextIdade.setText("");
        editTextDisciplina.setText("");
        editTextNota1.setText("");
        editTextNota2.setText("");
        textViewErro.setText("");
        textViewDados.setText("");
        textViewStatus.setText("");
    }

    //Padrão e-mail;
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
