package custom.panel;

import custom.input.TextoNumerico;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class SetorSudoku extends JPanel {

    public SetorSudoku(final List<TextoNumerico> textoFields){
        var dimensao = new Dimension(170,170);
        this.setSize(dimensao);
        this.setPreferredSize(dimensao);
        this.setBorder(new LineBorder(Color.black, 2 , true));
        this.setVisible(true);
        textoFields.forEach(this::add);
    }
}
