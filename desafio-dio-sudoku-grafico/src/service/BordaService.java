package service;

import enums.StatusJogoEnum;
import model.Borda;
import model.Espaco;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BordaService {

    private final static int LIMITE_BORDA = 9;

    private final Borda borda;

    public BordaService(final Map<String, String> configJogo) {
        this.borda = new Borda(iniciarBorda(configJogo));
    }

    public List<List<Espaco>> getSpaces(){
        return this.borda.getEspacos();
    }

    public void reiniciar(){
        this.borda.reiniciar();
    }

    public boolean temErros(){
        return this.borda.temErros();
    }

    public StatusJogoEnum getStatus(){
        return borda.getStatus();
    }

    public boolean terminarJogo(){
        return this.borda.JogoAcabou();
    }

    private List<List<Espaco>> iniciarBorda(final Map<String, String> configJogo) {
        List<List<Espaco>> espacos = new ArrayList<>();
        for (int i = 0; i < LIMITE_BORDA; i++) {
            espacos.add(new ArrayList<>());
            for (int j = 0; j < LIMITE_BORDA; j++) {
                var configuracaoPosicao = configJogo.get("%s,%s".formatted(i, j));
                if (configuracaoPosicao != null) {
                    var esperado = Integer.parseInt(configuracaoPosicao.split(";")[0]);
                    var fixo = Boolean.parseBoolean(configuracaoPosicao.split(";")[1]);
                    var espacoAtual = new Espaco(esperado, fixo);

                    System.out.printf("Posição [%d,%d] - Esperado: %d | Fixo: %b%n", i, j, esperado, fixo);

                    espacos.get(i).add(espacoAtual);
                } else {
                    espacos.get(i).add(new Espaco(0, false));
                }
            }
        }

        return espacos;
    }
}
