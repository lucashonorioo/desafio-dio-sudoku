package custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class VerificarStatusJogoButton extends JButton {

    public VerificarStatusJogoButton(final ActionListener actionListener){
        this.setText("Verificar jogo");
        this.addActionListener(actionListener);
    }
}
