package br.ufjf.dcc196.luisguilhermecipriani.pedrapapeltesoura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public enum Jogada{
        PEDRA(0), Papel(1), Tesoura(2);
        private final int valor;
        Jogada(int valor) {
            this.valor = valor;
        }
    }
    public enum Resultado{
        DERROTA(-1), EMPATE(0), VITORIA(1);
        private final int valor;
        Resultado(int valor){
            this.valor = valor;
        }
    }

    public static final Resultado TABELA[][] = {
            {Resultado.EMPATE, Resultado.DERROTA, Resultado.VITORIA},
            {Resultado.VITORIA, Resultado.EMPATE, Resultado.DERROTA},
            {Resultado.DERROTA, Resultado.VITORIA, Resultado.EMPATE},
    }
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

    public void buttonPedraClick(View view){
        rodada(Jogada.PEDRA);
    }
    public void buttonPapelClick(View view){
        rodada(Jogada.Papel);
    }
    public void buttonTesouraClick(View view){
        rodada(Jogada.Tesoura;
    }

    public void rodada(Jogada jogada){
        Jogada jogadaComputador = Jogada.values()[dado.nextInt(3)];
        switch (TABELA[jogada.valor][jogadaComputador.valor]){
            case VITORIA:
                pontosHumano += 3;
                break;
            case DERROTA:
                pontosComputador += 3;
                break;
            case EMPATE:
                pontosHumano += 1;
                pontosComputador += 1;
                break;
        }
    }
}