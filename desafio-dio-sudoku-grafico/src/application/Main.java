package application;

import model.Borda;
import model.Espaco;
import utilities.ModeloDeQuadro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;
import static utilities.ModeloDeQuadro.BOARD_TEMPLATE;

public class Main {

    private final static Scanner sc = new Scanner(System.in);

    private static Borda borda;

    private final static int LIMITE_BORDA = 9;

    public static void main(String[] args) {

        final var posicoes = Stream.of(args).collect(toMap(
                k -> k.split(",")[0],
                v -> v.split(",")[1],
                (v1, v2) -> v1 + ";" + v2
        ));
        var opcao = -1;
        while(true) {
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo Jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - limpar jogo");
            System.out.println("7 - Finalizar jogo");
            System.out.println("8 - Sair");
            System.out.print("Digite: ");
            opcao = sc.nextInt();


            switch (opcao) {
                case 1 -> comecarJogo(posicoes);
                case 2 -> inserirNumero();
                case 3 -> removerNumero();
                case 4 -> mostrarJogoAtual();
                case 5 -> mostrarStatusDoJogo();
                case 6 -> limparJogo();
                case 7 -> terminarJogo();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção inválida, selecione uma das opções do menu");

            }
        }
    }

    private static void comecarJogo(Map<String, String> posicoes) {
        if(nonNull(borda)){
            System.out.println("O jogo já foi iniciado");
            return;
        }
        List<List<Espaco>> espacos = new ArrayList<>();
        for (int i = 0; i < LIMITE_BORDA; i++) {
            espacos.add(new ArrayList<>());
            for (int j = 0; j < LIMITE_BORDA; j++) {
                var configuracaoPosicao = posicoes.get("%s,%s".formatted(i, j));
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

        borda = new Borda(espacos);
        System.out.println("O jogo está pronto para começar");
    }

    private static void inserirNumero() {
        if(isNull(borda)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.print("Insira o numero da coluna: ");
        var coluna = executarAteTerNumeroValido(0, 8);
        System.out.print("Insira o numero da linha: ");
        var linha =  executarAteTerNumeroValido(0,8);
        System.out.printf("Insira o numero da posição escolhida: [%s,%s]\n ",coluna,linha);
        var valor = executarAteTerNumeroValido(1,9);
        if(!borda.alterarValor(coluna,linha,valor)){
            System.out.printf("A posicão [%s,%s] tem um valor fixo\n", coluna,linha);
        }

    }

    private static void removerNumero() {
        if(isNull(borda)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.print("Insira o numero da coluna: ");
        var coluna = executarAteTerNumeroValido(0, 8);
        System.out.print("Insira o numero da linha: ");
        var linha =  executarAteTerNumeroValido(0,8);
        if(!borda.valorLimpo(coluna,linha)){
            System.out.printf("A posicão [%s,%s] tem um valor fixo\n", coluna,linha);
        }
    }

    private static void mostrarJogoAtual() {
        if(isNull(borda)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        var args = new Object[81];
        var argPos = 0;
        for (int i = 0; i < LIMITE_BORDA; i++) {
            for (int j = 0; j < LIMITE_BORDA; j++) {
                var espaco = borda.getEspacos().get(i).get(j);
                var valor = espaco.getAtual() != null ? espaco.getAtual() : " ";

                System.out.printf("Posição [%d,%d]: %s%n", i, j, valor);

                args[argPos++] = " " + valor;
            }
        }

        System.out.println("Seu jogo se encontra da seguinte forma:");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void mostrarStatusDoJogo() {
        if (isNull(borda)) {
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }

        var args = new Object[81];
        var argPos = 0;

        for (int i = 0; i < LIMITE_BORDA; i++) {
            for (int j = 0; j < LIMITE_BORDA; j++) {
                var espaco = borda.getEspacos().get(i).get(j);
                var valor = (espaco.getAtual() != null) ? espaco.getAtual().toString() : " ";

                System.out.printf("Posição [%d,%d]: %s%n", i, j, valor);

                args[argPos++] = " ";
            }
        }
        System.out.println("Seu jogo se encontra da seguinte forma:");
        System.out.printf((BOARD_TEMPLATE) + "\n", args);
    }

    private static void limparJogo() {
        if(isNull(borda)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        System.out.print("Tem certeza que deseja limpar o jogo? ");
        var confirmar = sc.next();
        while (!confirmar.equalsIgnoreCase("sim") && !confirmar.equalsIgnoreCase("não")){
            System.out.print("Digite 'sim' ou 'não': ");
            confirmar = sc.next();
        }
        if(confirmar.equalsIgnoreCase("sim")){
            borda.reiniciar();
        }

    }

    private static void terminarJogo() {
        if(isNull(borda)){
            System.out.println("O jogo ainda não foi iniciado");
            return;
        }
        if(borda.JogoAcabou()){
            System.out.println("Parabéns você terminou o jogo");
            mostrarJogoAtual();
            borda = null;
        }
        else if(borda.temErros()){
            System.out.println("O seu jogo possui erro, verifique sua borda e ajuste");
        }
        else {
            System.out.println("Você ainda precisa preencher espaços!");
        }

    }

    private static int executarAteTerNumeroValido(final int min, int max){
        var atual = sc.nextInt();
        while(atual < min || atual > max){
            System.out.printf("Informe um numero entre %s  e %s\n", min,max);
            atual = sc.nextInt();
        }
        return atual;
    }

}