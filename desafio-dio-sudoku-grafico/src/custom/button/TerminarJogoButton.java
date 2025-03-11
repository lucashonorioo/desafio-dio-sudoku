package custom.button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TerminarJogoButton extends JButton {
    public TerminarJogoButton(final ActionListener actionListener){
        this.setText("Concluir");
        this.addActionListener(actionListener);
    }
}
