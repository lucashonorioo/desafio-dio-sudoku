package model;

import enums.StatusJogoEnum;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Borda {

    private final List<List<Espaco>> espacos;

    public Borda(final List<List<Espaco>> espacos) {
        this.espacos = espacos;
    }

    public List<List<Espaco>> getEspacos() {
        return espacos;
    }

    public StatusJogoEnum getStatus(){
        if(espacos.stream().flatMap(Collection::stream).noneMatch(s -> !s.isFixo() && nonNull(s.getAtual()))){
            return StatusJogoEnum.NAO_INICIADO;
        }
        return espacos.stream().flatMap(Collection::stream).anyMatch(s -> isNull(s.getAtual())) ? StatusJogoEnum.INCOMPLETO : StatusJogoEnum.COMPLETO;
    }

    public boolean temErros(){
        if(getStatus() == StatusJogoEnum.NAO_INICIADO){
            return false;
        }
        return espacos.stream().flatMap(Collection::stream).anyMatch(s -> nonNull(s.getAtual()) && !s.getAtual().equals(s.getEsperado()));
    }

    public boolean alterarValor(final int coluna, final int linha, final int valor){
        var espaco = espacos.get(coluna).get(linha);
        if(espaco.isFixo()){
            return false;
        }
        espaco.setAtual(valor);
        return true;
    }

    public boolean valorLimpo(final int coluna, final int linha){
        var espaco = espacos.get(coluna).get(linha);
        if(espaco.isFixo()){
            return false;
        }
        espaco.espacoLimpo();
        return true;
    }

    public void reiniciar(){
        espacos.forEach(c -> c.forEach(Espaco::espacoLimpo));
    }

    public boolean JogoAcabou(){
        return !temErros() && getStatus() == StatusJogoEnum.COMPLETO;
    }

}
