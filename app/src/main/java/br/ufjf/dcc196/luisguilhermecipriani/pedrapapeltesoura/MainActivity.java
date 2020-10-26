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
        PEDRA(0), PAPEL(1), TESOURA(2), LAGARTO(3), SPOCK(4);
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
            {Resultado.EMPATE, Resultado.DERROTA, Resultado.VITORIA, Resultado.VITORIA, Resultado.DERROTA},
            {Resultado.VITORIA, Resultado.EMPATE, Resultado.DERROTA, Resultado.DERROTA, Resultado.VITORIA},
            {Resultado.DERROTA, Resultado.VITORIA, Resultado.EMPATE, Resultado.VITORIA, Resultado.DERROTA},
            {Resultado.DERROTA, Resultado.VITORIA, Resultado.DERROTA, Resultado.EMPATE, Resultado.VITORIA},
            {Resultado.VITORIA, Resultado.DERROTA, Resultado.VITORIA, Resultado.DERROTA, Resultado.EMPATE},
    };
    private Integer pontosComputador = 0;
    private Integer pontosHumano = 0;

    private Button buttonPedra;
    private Button buttonPapel;
    private Button buttonTesoura;
    private Button buttonLagarto;
    private Button buttonSpock;

    private ProgressBar progressBarComputador;
    private ProgressBar progressBarHumano;
    private TextView textViewStatus;

    private Random dado = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // Retirada da barra de título
        // componentes de entrada
        buttonPedra = findViewById(R.id.buttonPedra);
        buttonPapel = findViewById(R.id.buttonPapel);
        buttonTesoura = findViewById(R.id.buttonTesoura);
        buttonLagarto = findViewById(R.id.buttonLagarto);
        buttonSpock = findViewById(R.id.buttonSpock);
        // componentes de saída
        progressBarComputador = findViewById(R.id.progressBarComputador);
        progressBarHumano = findViewById(R.id.progressBarHumano);
        textViewStatus = findViewById(R.id.textViewStatus);
    }

    public void buttonPedraClick(View view) {
        rodada(Jogada.PEDRA);
    }

    public void buttonPapelClick(View view) {
        rodada(Jogada.PAPEL);
    }

    public void buttonTesouraClick(View view) {
        rodada(Jogada.TESOURA);
    }

    public void buttonLagartoClick(View view) {
        rodada(Jogada.LAGARTO);
    }

    public void buttonSpockClick(View view) {
        rodada(Jogada.SPOCK);
    }

    public void rodada(Jogada jogada) {
        Jogada jogadaComputador = Jogada.values()[dado.nextInt(5)];
        switch (TABELA[jogada.valor][jogadaComputador.valor]) {
            case VITORIA:
                pontosHumano += 3;
                Toast.makeText(this,
                        informaRegras(jogada, jogadaComputador),
                        Toast.LENGTH_SHORT).show();
                break;
            case DERROTA:
                pontosComputador += 3;
                Toast.makeText(this,
                        informaRegras(jogada, jogadaComputador),
                        Toast.LENGTH_SHORT).show();
                break;
            case EMPATE:
                pontosHumano += 1;
                pontosComputador += 1;
                Toast.makeText(this,
                        informaRegras(jogada, jogadaComputador),
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

    public void textViewStatusClick(View view) {
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

    public String informaRegras(Jogada jogada, Jogada jogadaComputador) {
        String mensagem = mensagem = "Empate - Ambos escolheram a mesma opção";;
        switch (jogada) {
            case PEDRA:
                if (jogadaComputador.valor == 1) {
                    mensagem = "Computador ganhou - Papel cobre pedra";
                } else if (jogadaComputador.valor == 2) {
                    mensagem = "Humano ganhou - Pedra amassa tesoura";
                } else if (jogadaComputador.valor == 3) {
                    mensagem = "Humano ganhou - Pedra esmaga lagarto";
                } else if (jogadaComputador.valor == 4) {
                    mensagem = "Computador ganhou - Spock vaporiza pedra";
                }
                break;
            case PAPEL:
                if (jogadaComputador.valor == 0) {
                    mensagem = "Humano ganhou - Papel cobre pedra";
                } else if (jogadaComputador.valor == 2) {
                    mensagem = "Computador ganhou - Tesoura corta papel";
                } else if (jogadaComputador.valor == 3) {
                    mensagem = "Computador ganhou - Lagarto come papel";
                } else if (jogadaComputador.valor == 4) {
                    mensagem = "Humano ganhou - Papel refuta Spock";
                }
                break;
            case TESOURA:
                if (jogadaComputador.valor == 0) {
                    mensagem = "Computador ganhou - Pedra amassa tesoura";
                } else if (jogadaComputador.valor == 1) {
                    mensagem = "Humano ganhou - Tesoura corta papel";
                } else if (jogadaComputador.valor == 3) {
                    mensagem = "Humano ganhou - Tesoura decapita lagarto";
                } else if (jogadaComputador.valor == 4) {
                    mensagem = "Computador ganhou - Spock derrete tesoura";
                }
                break;
            case LAGARTO:
                if (jogadaComputador.valor == 0) {
                    mensagem = "Computador ganhou - Pedra esmaga lagarto";
                } else if (jogadaComputador.valor == 1) {
                    mensagem = "Humano ganhou - Lagarto come papel";
                } else if (jogadaComputador.valor == 2) {
                    mensagem = "Computador ganhou - Tesoura decapita lagarto";
                } else if (jogadaComputador.valor == 4) {
                    mensagem = "Humano ganhou - Lagarto envenena Spock";
                }
                break;
            case SPOCK:
                if (jogadaComputador.valor == 0) {
                    mensagem = "Humano ganhou - Spock vaporiza pedra";
                } else if (jogadaComputador.valor == 1) {
                    mensagem = "Computador ganhou - Papel refuta Spock";
                } else if (jogadaComputador.valor == 2) {
                    mensagem = "Humano ganhou - Spock derrete tesoura";
                } else if (jogadaComputador.valor == 3) {
                    mensagem = "Computador ganhou - Lagarto envenena Spock";
                }
                break;
        }
        return mensagem;
    }
}