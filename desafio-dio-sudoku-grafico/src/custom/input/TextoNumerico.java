package custom.input;

import model.Espaco;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextoNumerico extends JTextField {

    private final Espaco espaco;


    public TextoNumerico(final Espaco espaco) {
        this.espaco = espaco;
        var dimensao = new Dimension(50,50);
        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
        this.setVisible(true);
        this.setFont(new Font("Arial", Font.PLAIN,  20));
        this.setHorizontalAlignment(CENTER);
        this.setDocument(new LimiteTextoNumerico());
        this.setEnabled(!espaco.isFixo());
        if(this.espaco.isFixo()){
            this.setText(espaco.getAtual().toString());
        }
        this.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                mudarEspaco();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mudarEspaco();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                mudarEspaco();
            }

            private void mudarEspaco(){
                if(getText().isEmpty()){
                    espaco.espacoLimpo();
                    return;
                }
                espaco.setAtual(Integer.parseInt(getText()));
            }

        });
    }
}
