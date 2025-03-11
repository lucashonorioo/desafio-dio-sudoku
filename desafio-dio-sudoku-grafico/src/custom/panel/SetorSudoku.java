package custom.panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SetorSudoku extends JPanel {

    public SetorSudoku(){
        var dimensao = new Dimension(170,170);
        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
        this.setBorder(new LineBorder(Color.black, 2 , true));
        this.setVisible(true);
    }
}
