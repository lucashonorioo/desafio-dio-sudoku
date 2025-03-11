package custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ReiniciarButton extends JButton {
    public ReiniciarButton(final ActionListener actionListener){
        this.setText("Reiniciar jogo");
        this.addActionListener(actionListener);
    }

}
