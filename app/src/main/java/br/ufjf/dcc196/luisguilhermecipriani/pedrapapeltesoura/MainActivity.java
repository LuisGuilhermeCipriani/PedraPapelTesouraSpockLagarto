package br.ufjf.dcc196.luisguilhermecipriani.pedrapapeltesoura;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public enum Jogada {
        PEDRA(0), Papel(1), Tesoura(2);
        private final int valor;

        Jogada(int valor) {
            this.valor = valor;
        }
    }

    public enum Resultado {
        DERROTA(-1), EMPATE(0), VITORIA(1);
        private final int valor;

        Resultado(int valor) {
            this.valor = valor;
        }
    }

    public static final Resultado TABELA[][] = {
            {Resultado.EMPATE, Resultado.DERROTA, Resultado.VITORIA},
            {Resultado.VITORIA, Resultado.EMPATE, Resultado.DERROTA},
            {Resultado.DERROTA, Resultado.VITORIA, Resultado.EMPATE},
    };
    private Integer pontosComputador = 0;
    private Integer pontosHumano = 0;

    private Button buttonPedra;
    private Button buttonPapel;
    private Button buttonTesoura;

    private ProgressBar progressBarComputador;
    private ProgressBar progressBarHumano;
    private TextView textViewStatus;

    private Random dado = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // componentes de entrada
        buttonPedra = findViewById(R.id.buttonPedra);
        buttonPapel = findViewById(R.id.buttonPapel);
        buttonTesoura = findViewById(R.id.buttonTesoura);
        // componentes de saída
        progressBarComputador = findViewById(R.id.progressBarComputador);
        progressBarHumano = findViewById(R.id.progressBarHumano);
        textViewStatus = findViewById(R.id.textViewStatus);
    }

    public void buttonPedraClick(View view) {
        rodada(Jogada.PEDRA);
    }

    public void buttonPapelClick(View view) {
        rodada(Jogada.Papel);
    }

    public void buttonTesouraClick(View view) {
        rodada(Jogada.Tesoura);
    }

    public void rodada(Jogada jogada) {
        Jogada jogadaComputador = Jogada.values()[dado.nextInt(3)];
        switch (TABELA[jogada.valor][jogadaComputador.valor]) {
            case VITORIA:
                pontosHumano += 3;
                Toast.makeText(this,
                        "O Humano ganhou",
                        Toast.LENGTH_SHORT).show();
                break;
            case DERROTA:
                pontosComputador += 3;
                Toast.makeText(this,
                        "O Computador ganhou",
                        Toast.LENGTH_SHORT).show();
                break;
            case EMPATE:
                pontosHumano += 1;
                pontosComputador += 1;
                Toast.makeText(this,
                        "Empate",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        atualizaStatus();
    }

    private void atualizaStatus() {
        progressBarComputador.setProgress(pontosComputador);
        progressBarHumano.setProgress(pontosHumano);
        if (pontosHumano < 15 && pontosComputador < 15) {
            textViewStatus.setText("Escolha uma opção...");
        } else if (pontosHumano >= 15 && pontosComputador < 15) {
            textViewStatus.setText("O Humano venceu o torneio!");
            iniciaTorneio();
        } else if (pontosHumano < 15 && pontosHumano >= 15) {
            textViewStatus.setText("O Computador venceu o torneio!");
            iniciaTorneio();
        } else {
            textViewStatus.setText("O torneio terminou empatado!");
            iniciaTorneio();
        }
    }

    private void iniciaTorneio() {
        pontosComputador = 0;
        pontosHumano = 0;
    }

    public void textViewStatusClick(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Reiniciar o torneio?");
        dialogBuilder.setMessage("Deseja reiniciar o torneio zerando o estado atual?");
        dialogBuilder.setPositiveButton("Reiniciar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        iniciaTorneio();
                        atualizaStatus();
                    }
                });
        dialogBuilder.create();
        dialogBuilder.show();
    }
}