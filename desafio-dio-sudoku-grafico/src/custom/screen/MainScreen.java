package custom.screen;

import custom.button.ReiniciarButton;
import custom.button.TerminarJogoButton;
import custom.button.VerificarStatusJogoButton;
import custom.frame.MainFrame;
import custom.panel.MainPanel;
import service.BordaService;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainScreen {

    private final static Dimension dimensao = new Dimension(600,400);

    private final BordaService bordaService;

    private JButton terminarJogoButton;
    private JButton verificarStatusJogoButton;
    private JButton reiniciarJogoButton;

    public MainScreen(final Map<String,String> jogoConfig) {
        this.bordaService = new BordaService(jogoConfig);
    }

    public void buildMainScreen(){
        JPanel mainPanel = new MainPanel(dimensao);
        JFrame  mainFrame = new MainFrame(dimensao,mainPanel);
        addReiniciarButton(mainPanel);
        addVerificarStatusJogoButton(mainPanel);
        addTerminarJogoButton(mainPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
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

    private void addVerificarStatusJogoButton(JPanel mainPanel) {
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
        mainPanel.add(verificarStatusJogoButton);
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
            }
        });
        mainPanel.add(reiniciarJogoButton);
    }

}
