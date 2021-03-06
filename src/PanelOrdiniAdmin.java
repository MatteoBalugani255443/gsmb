import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * PanelOrdiniAdmin è la classe che mostra e che permette di modificare all'admin la tabella contenente le spedizioni di tutti gli utenti.
 */
public class PanelOrdiniAdmin extends JPanel {
    private final PopupModificaAdmin popModificaAdmin;
    private final JTable table;
    String filename = "OrdiniClientiCriptati.txt";  //il file dove vengono salvati tutti gli ordini
    private final ListSelectionModel selezioneModel;
    private int selectedrow;
    private VettoreOrdini ordini_totali;

    //il jtextfield username nascosto manca perchè il nome è scritto nel codice
    public PanelOrdiniAdmin(GSMBFrame frame_principale) {
        super();

        ordini_totali = new VettoreOrdini(filename);
        if (ordini_totali.size() == 0) {

        }
        JButton aggiorna = new JButton("aggiorna");
        JButton pulsante_da_ordini_admin_a_accesso = new JButton("logout");
        String nome = VettoreCredenziali.CREDENZIALI_ADMIN.toUsername();
        JLabel scritta_benvenuto = new JLabel("Benvenuto Admin " + nome);


        TableOrdiniUser dataModel = new TableOrdiniUser();
        table = new JTable(dataModel);
        JScrollPane scrollpane = new JScrollPane();//non gli piace sa mando dentro table
        table.add(scrollpane);
       /* table.setBounds(0,0,500,500);
        table.setSize(450, 100);*/
        popModificaAdmin = new PopupModificaAdmin(table);
        selezioneModel = table.getSelectionModel();

        pulsante_da_ordini_admin_a_accesso.addActionListener(e -> frame_principale.toCard("Panel accesso"));
        aggiorna.addActionListener(e -> {

            ordini_totali = new VettoreOrdini("OrdiniClientiCriptati.txt");
            table.updateUI();
            for (SpedizioneNormale spedizioneNormale : ordini_totali) {
                System.out.println(spedizioneNormale.toStato()); //se cè un solo elemento nel vettore va in errore, ma non dovrebbe essere davvero un problema
            }

        });


        //serve per stabilire dove compare il JPopupMenu
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                {
                    //determina se cliccato col mouse destro
                    if (SwingUtilities.isRightMouseButton(me)) {
                        popModificaAdmin.show(me.getComponent(), me.getX(), me.getY());

                    }
                }
            }

        });


        //determina se clicco su una riga della tabella
        selezioneModel.addListSelectionListener(e -> {

            if (!selezioneModel.isSelectionEmpty() && !e.getValueIsAdjusting()) {

                selectedrow = selezioneModel.getMinSelectionIndex();//selectdrow=indice della riga
                popModificaAdmin.setRow(selectedrow, ordini_totali);//popmodifica ottiene l'indice della riga
                popModificaAdmin.setTasti(table.getValueAt(selectedrow, 2).equals("RIMBORSO EROGATO") || (table.getValueAt(selectedrow, 2).equals("RICEVUTA")), table.getValueAt(selectedrow, 5).equals(""));
                // se la riga che evidenzio con tasto destro equivale a una di quelle condizioni, allora mostro solo il pulsante relativo a quelle condizioni


            }
        });


        add(scritta_benvenuto);
        add(pulsante_da_ordini_admin_a_accesso);
        add(table);
        add(aggiorna);

    }


    public class TableOrdiniUser extends AbstractTableModel {

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public int getRowCount() {
            return ordini_totali.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }


        @Override
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "Destinazione";
                case 1:
                    return "Codice";
                case 2:
                    return "Stato";
                case 3:
                    return "Peso";
                case 4:
                    return "Data";
                case 5:
                    return "Valore assicurato";
            }
            return null;
        }


        public void setValueAt(Object value, int row, int
                col) {
            SpedizioneTabella spedizione = ordini_totali.get(row);

            if (col == 2)  //questo codice mi permette di modificare le celle di quella colonna
                spedizione.modificaStato((String) value);

            fireTableDataChanged();  //salva le modifiche sul vettore
        }


        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SpedizioneTabella spedizione = ordini_totali.get(rowIndex);


            switch (columnIndex) {
                case 0:
                    return spedizione.toDestinazione();
                case 1:
                    return spedizione.toCodice();
                case 2:
                    return spedizione.toStato();
                case 3:
                    return spedizione.toPeso();
                case 4:
                    return spedizione.toData();
                case 5:
                    return spedizione.toValoreAssicurato();
            }
            return "Unknown";
        }
    }


}



