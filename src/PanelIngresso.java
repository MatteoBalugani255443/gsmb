import javax.swing.*;
import java.awt.*;


/**
 * PanelIngresso è la classe che funge da ingresso all'avvio del programma.
 */
public class PanelIngresso extends JPanel {


    public PanelIngresso(GSMBFrame frame_principale) {
        super();


        setBackground(Color.yellow);
        JLabel titolo = new JLabel("GESTORE SPEDIZIONI MATTEO BALUGANI");
        JButton pulsante_da_ingresso_a_accesso = new JButton("ACCEDI");
        pulsante_da_ingresso_a_accesso.addActionListener(e -> frame_principale.toCard("Panel accesso"));
        add(titolo);
        add(pulsante_da_ingresso_a_accesso);
    }

}
