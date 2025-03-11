package custom.screen;

import custom.button.ReiniciarButton;
import custom.button.TerminarJogoButton;
import custom.button.VerificarStatusJogoButton;
import custom.frame.MainFrame;
import custom.input.TextoNumerico;
import custom.panel.MainPanel;
import custom.panel.SetorSudoku;
import model.Espaco;
import service.BordaService;
import service.EventoEnum;
import service.NotificacaoService;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainScreen {

    private final static Dimension dimensao = new Dimension(600,600);

    private final BordaService bordaService;
    private final NotificacaoService notificacaoService;

    private JButton terminarJogoButton;
    private JButton verificarStatusJogoButton;
    private JButton reiniciarJogoButton;

    public MainScreen(final Map<String,String> jogoConfig) {
        this.bordaService = new BordaService(jogoConfig);
        this.notificacaoService = new NotificacaoService();
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimensao);
        JFrame  mainFrame = new MainFrame(dimensao,mainPanel);
        for(int r = 0; r < 9; r+= 3){
            var endRow = r + 2;
            for(int c = 0; c < 9; c+=3){
                var endCol = c + 2;
                var espacos = getEspacosDoSetor(bordaService.getSpaces(), c, endCol, r, endRow);
                JPanel setor = gerarSecao(espacos);
                mainPanel.add(setor);

            }
        }
        addReiniciarButton(mainPanel);
        addVerificarStatusJogoButton(mainPanel);
        addTerminarJogoButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private List<Espaco> getEspacosDoSetor(final List<List<Espaco>> espacos, final int initCol, final int endCol, final int initRow, final int endRow){
        List<Espaco> setorEspaco = new ArrayList<>();
        for(int r = initRow; r <= endRow; r++){
            for(int c = initCol; c <= endCol; c++){
                setorEspaco.add(espacos.get(c).get(r));
            }
        }
        return setorEspaco;
    }

    private JPanel gerarSecao(final List<Espaco> espacos){
        List<TextoNumerico> fields = new ArrayList<>(espacos.stream().map(TextoNumerico::new).toList());
        fields.forEach(t -> notificacaoService.subscribe(EventoEnum.LIMPAR_ESPACO, t));
        return new SetorSudoku(fields);
    }

    private void addTerminarJogoButton(JPanel mainPanel) {
         terminarJogoButton = new TerminarJogoButton(e -> {
            if(bordaService.terminarJogo()){
                JOptionPane.showMessageDialog(null,"Parabéns você conlcuiu o jogo");
                terminarJogoButton.setEnabled(false);
                verificarStatusJogoButton.setEnabled(false);
                reiniciarJogoButton.setEnabled(false);
            }
            else {
                JOptionPane.showMessageDialog(null,"Seu jogo tem alguma inconsistencia!");
            }
        });
        mainPanel.add(terminarJogoButton);
    }

    private void addVerificarStatusJogoButton(final JPanel mainPanel) {
         verificarStatusJogoButton = new VerificarStatusJogoButton(e -> {
            var temErros = bordaService.temErros();
            var statusJogo = bordaService.getStatus();
            var mensagem = switch (statusJogo){
                case NAO_INICIADO -> "O jogo não foi iniciado";
                case INCOMPLETO -> "O jogo está incompleto";
                case COMPLETO -> "O jogo está completo";
            };
            mensagem += temErros ? " e contém erros " : " e não contém erros";
            JOptionPane.showMessageDialog(null, mensagem);
        });
        mainPanel.add(MainScreen.this.verificarStatusJogoButton);
    }

    private void addReiniciarButton(JPanel mainPanel) {
         reiniciarJogoButton = new ReiniciarButton(e -> {
            var dialogResult = JOptionPane.showConfirmDialog(
                    null,
                    "Deseja reiniciar o jogo? ",
                    "Limpar o jogo",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if(dialogResult == 0){
                bordaService.reiniciar();
                notificacaoService.notify(EventoEnum.LIMPAR_ESPACO);
            }
        });
        mainPanel.add(reiniciarJogoButton);
    }

}
