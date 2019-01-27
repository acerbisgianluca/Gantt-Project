package com.acerbisgianluca;

import com.acerbisgianluca.exceptions.TaskAlreadyExistsException;
import com.acerbisgianluca.exceptions.TaskNotFoundException;
import java.awt.Color;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TreeItem;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * E' la classe principale che non viene instanziata e che contiene i metodi
 * principali. Gestisce tutta la comunicazione con l'utente raccogliendo tutti i
 * dati sulle attività.
 *
 * @author Gianluca
 */
public class App extends javax.swing.JFrame {

    /**
     * Il modello della tabella.
     */
    private final DefaultTableModel tableModel;
    /**
     * Il modello della lista di possibili dipendenze.
     */
    private final DefaultListModel<String> listModel;
    /**
     * L'oggetto che contiene i metodi per eseguire l'algoritmo.
     */
    private final Algorithm algorithm;
    /**
     * Indica se si sta modificando un {@link com.acerbisgianluca.Task} già
     * esistente.
     */
    private boolean editing;
    /**
     * Il nome precedente del {@link com.acerbisgianluca.Task} che si sta
     * modificando.
     */
    private String lastName;
    /**
     * Formattatore per le date da stringa a oggetto e viceversa.
     */
    private final DateTimeFormatter fmt;

    /**
     * Creates new form App
     */
    public App() {
        initComponents();
        tableModel = (DefaultTableModel) table.getModel();
        listModel = new DefaultListModel<>();
        listDependencies.setModel(listModel);
        algorithm = new Algorithm();
        editing = false;
        this.fmt = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
        cleanFields(true);
        setRowSorter();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        lblAddTask = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblDuration = new javax.swing.JLabel();
        spnDuration = new javax.swing.JSpinner();
        lblStartDate = new javax.swing.JLabel();
        spnDate = new javax.swing.JSpinner();
        lblDependencies = new javax.swing.JLabel();
        listScrollPane = new javax.swing.JScrollPane();
        listDependencies = new javax.swing.JList<>();
        btnAdd = new javax.swing.JButton();
        separator = new javax.swing.JSeparator();
        tableScrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnDeleteAll = new javax.swing.JButton();
        btnDeleteTask = new javax.swing.JButton();
        lblOutput = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gantt Project");
        setResizable(false);

        lblAddTask.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblAddTask.setText("Aggiungi una nuova attività");

        lblName.setText("Nome");

        lblDuration.setText("Durata");

        spnDuration.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        lblStartDate.setText("Data di inizio");

        spnDate.setModel(new javax.swing.SpinnerDateModel());

        lblDependencies.setText("Precedenze");

        listDependencies.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        listScrollPane.setViewportView(listDependencies);

        btnAdd.setText("Aggiungi");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome", "Durata", "Early Start", "Early Finish", "Late Start", "Late Finish"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setDragEnabled(true);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        tableScrollPane.setViewportView(table);

        btnDeleteAll.setText("Elimina tutto");
        btnDeleteAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteAllActionPerformed(evt);
            }
        });

        btnDeleteTask.setText("Elimina attività");
        btnDeleteTask.setEnabled(false);
        btnDeleteTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTaskActionPerformed(evt);
            }
        });

        lblOutput.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(btnDeleteAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteTask)
                        .addGap(18, 18, 18)
                        .addComponent(lblOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(410, 410, 410))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAddTask)
                            .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDuration)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spnDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblStartDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDependencies)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))))
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAddTask)
                .addGap(10, 10, 10)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblName)
                            .addComponent(lblDuration)
                            .addComponent(spnDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblStartDate)
                            .addComponent(lblDependencies)
                            .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tableScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAdd)
                            .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDeleteAll)
                        .addComponent(btnDeleteTask)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Contiene l'algoritmo e i check per aggiungere/modificare un
     * {@link com.acerbisgianluca.Task}.
     *
     * @param evt L'evento generato al click del bottone per
     * aggiungere/modificare un {@link com.acerbisgianluca.Task}.
     */
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Task newESEF, newLSLF, tESEF, tLSLF;
        try {
            if (editing) {

                String name = txtName.getText().trim();
                if (name.equals("")) {
                    showMessage("Inserire il nome.", true);
                    return;
                }
                if (!this.lastName.equals(name)) {
                    taskExists(name);
                }

                newLSLF = algorithm.getTaskByName(this.lastName, false);
                newESEF = algorithm.getTaskByName(this.lastName, true);

                Date date = (Date) spnDate.getModel().getValue();
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                int duration = (int) spnDuration.getModel().getValue();
                duration = duration < 1 ? 1 : duration;

                int[] dependenciesIds = listDependencies.getSelectedIndices();
                for (int i : dependenciesIds) {
                    if (i == 0 || listModel.getElementAt(i).equals(this.lastName)) {
                        continue;
                    }
                    
                    if(algorithm.getTaskByName(listModel.getElementAt(i), true).getDependencies().contains(newESEF)){
                        showMessage("Rilevato ciclo fra 2 attività.", true);
                        return;
                    }

                    if (!newESEF.getDependencies().contains((tESEF = algorithm.getTaskByName(listModel.getElementAt(i), true)))) {
                        newESEF.addDependency(tESEF);
                        newLSLF.addDependency((tLSLF = algorithm.getTaskByName(listModel.getElementAt(i), false)));

                        tESEF.addParent(newESEF);
                        tLSLF.addParent(newLSLF);
                    }
                }
                
                newLSLF.update(name, localDate, duration);
                newESEF.update(name, localDate, duration);
                showResult();

                for (int i = 0; i < listModel.size(); i++) {
                    if (listModel.getElementAt(i).equals(this.lastName)) {
                        listModel.set(i, name);
                        break;
                    }
                }
                
                for (int i = 1; i < listModel.size(); i++) {
                    if (!listDependencies.isSelectedIndex(i)) {
                        if (newESEF.getDependencies().contains((tESEF = algorithm.getTaskByName(listModel.getElementAt(i), true)))) {
                            newESEF.removeDependency(tESEF);
                            newLSLF.removeDependency((tLSLF = algorithm.getTaskByName(listModel.getElementAt(i), false)));

                            tESEF.removeParent(newESEF);
                            tLSLF.removeParent(newLSLF);
                        }
                    }
                }

                cleanFields(false);
                realTimeRun();
                return;
            }

            String name = txtName.getText().trim();
            if (name.equals("")) {
                showMessage("Inserire il nome.", true);
                return;
            }
            taskExists(name);

            Date date = (Date) spnDate.getModel().getValue();
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int duration = (int) spnDuration.getModel().getValue();
            duration = duration < 1 ? 1 : duration;

            newESEF = new Task(name, localDate, duration);
            newLSLF = new Task(name, localDate, duration);

            int[] dependenciesIds = listDependencies.getSelectedIndices();
            for (int i : dependenciesIds) {
                if (i == 0) {
                    continue;
                }

                tESEF = algorithm.getTaskByName(listModel.getElementAt(i), true);
                tLSLF = algorithm.getTaskByName(listModel.getElementAt(i), false);

                newESEF.addDependency(tESEF);
                newLSLF.addDependency(tLSLF);

                tESEF.addParent(newESEF);
                tLSLF.addParent(newLSLF);

            }

            algorithm.addTask(newESEF, true);
            algorithm.addTask(newLSLF, false);

            listModel.addElement(name);

            tableModel.addRow(new Object[]{name, duration, newESEF.getStart().format(this.fmt), newESEF.getEnd().format(this.fmt), newLSLF.getStart().format(this.fmt), newLSLF.getEnd().format(this.fmt)});
            cleanFields(false);
            realTimeRun();
        } catch (TaskNotFoundException | TaskAlreadyExistsException ex) {
            showMessage(ex.getMessage(), true);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    /**
     * Elimina tutti i {@link com.acerbisgianluca.Task}, resetta la tabella e i
     * campi.
     *
     * @param evt L'evento generato al click sul bottone per eliminare tutti i
     * {@link com.acerbisgianluca.Task}.
     */
    private void btnDeleteAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteAllActionPerformed
        if (algorithm.getTasksESEF().isEmpty()) {
            showMessage("Non è stata ancora inserita alcuna attività.", true);
            return;
        }
        algorithm.resetLists();
        tableModel.setRowCount(0);
        listModel.clear();
        cleanFields(true);
    }//GEN-LAST:event_btnDeleteAllActionPerformed

    /**
     * Carica nel form i dati del {@link com.acerbisgianluca.Task} selezionato.
     *
     * @param evt L'evento generato al click su di una riga della tabella.
     */
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        editing = true;
        lblAddTask.setText("Modifica l'attività selezionata");
        btnAdd.setText("Modifica");
        btnDeleteTask.setEnabled(true);

        try {
            int row = table.getSelectedRow();
            String name = (String) table.getValueAt(row, 0);
            this.lastName = name;
            txtName.setText(name);
            spnDuration.setValue((int) table.getValueAt(row, 1));
            spnDate.setValue(java.sql.Date.valueOf(algorithm.getTaskByName(name, true).getDefaultDate()));
            Task tLSLF = algorithm.getTaskByName(name, false);
            List<Integer> selectedIndeces = new ArrayList<>();
            for (int i = 0; i < listModel.size(); i++) {
                for (int j = 0; j < tLSLF.getDependencies().size(); j++) {
                    if (tLSLF.getDependencies().get(j).getName().equals(listModel.getElementAt(i))) {
                        selectedIndeces.add(i);
                    }
                }
            }
            int[] selectedIndicesArr = new int[selectedIndeces.size()];
            int i = 0;
            for (Integer n : selectedIndeces) {
                selectedIndicesArr[i] = n;
                i++;
            }
            listDependencies.clearSelection();
            if (selectedIndeces.isEmpty()) {
                listDependencies.setSelectedIndex(0);
            } else {
                listDependencies.setSelectedIndices(selectedIndicesArr);
            }
        } catch (TaskNotFoundException ex) {
            showMessage(ex.getMessage(), true);
        }
    }//GEN-LAST:event_tableMouseClicked

    /**
     * Elimina l'attività dalle liste, dalla tabella, dalla lista di dipendenze
     * e da tutte le attività ad essa collegate.
     *
     * @param evt L'evento generato al click sul bottone per eliminare
     * un'attività.
     */
    private void btnDeleteTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTaskActionPerformed
        int row = table.getSelectedRow();
        String name = (String) table.getValueAt(row, 0);
        Task tESEF, tLSLF;
        try {
            tESEF = algorithm.getTaskByName(name, true);
            tLSLF = algorithm.getTaskByName(name, false);

            tESEF.getDependencies().forEach((t) -> {
                t.removeParent(tESEF);
            });
            tESEF.getParents().forEach((t) -> {
                t.removeDependency(tESEF);
            });
            tLSLF.getDependencies().forEach((t) -> {
                t.removeParent(tLSLF);
            });
            tLSLF.getParents().forEach((t) -> {
                t.removeDependency(tLSLF);
            });

            algorithm.removeFromLists(tESEF, tLSLF);
        } catch (TaskNotFoundException ex) {
            showMessage(ex.getMessage(), true);
        }

        tableModel.removeRow(row);
        listModel.removeElement(name);

        cleanFields(false);
        realTimeRun();
    }//GEN-LAST:event_btnDeleteTaskActionPerformed
    
    /**
     * Crea le righe all'interno della tabella indicando nome, durata, data di
     * inizio ES, data di fine EF, data di inizio LS e data di fine LF di ogni
     * attività.
     */
    public void showResult() {
        List<Task> esef = algorithm.getTasksESEF();
        List<Task> lslf = algorithm.getTasksLSLF();
        Task t, t1;
        tableModel.setRowCount(0);
        for (int i = 0; i < esef.size(); i++) {
            t = esef.get(i);
            t1 = lslf.get(i);
            tableModel.addRow(new Object[]{t.getName(), t.getDuration(), t.getStart().format(this.fmt), t.getEnd().format(this.fmt), t1.getStart().format(this.fmt), t1.getEnd().format(this.fmt)});
        }
    }

    /**
     * Pulisce tutti i campi del form.
     *
     * @param starting Vero se l'applicazione è appena stata avviata, altrimenti
     * false.
     */
    private void cleanFields(boolean starting) {
        txtName.setText("");
        spnDuration.setValue(1);
        spnDate.setValue(new Date());
        showMessage("", false);
        if (starting) {
            listModel.addElement("Nessuna");
        }
        listDependencies.setSelectedIndex(0);
        if (editing) {
            editing = false;
            lblAddTask.setText("Aggiungi una nuova attività");
            btnAdd.setText("Aggiungi");
            btnDeleteTask.setEnabled(false);
        }
    }

    /**
     * Controlla se un {@link com.acerbisgianluca.Task} esiste già.
     *
     * @param name Il nome del nuovo {@link com.acerbisgianluca.Task}.
     * @throws TaskAlreadyExistsException Se il {@link com.acerbisgianluca.Task}
     * esiste già lancia la l'eccezione.
     */
    private void taskExists(String name) throws TaskAlreadyExistsException {
        for (Task t : algorithm.getTasksESEF()) {
            if (t.getName().equalsIgnoreCase(name)) {
                throw new TaskAlreadyExistsException("Un'attività con lo stesso nome esiste già.");
            }
        }
    }

    /**
     * Riesegue l'algoritmo sull'aggiunta o la modifica di un
     * {@link com.acerbisgianluca.Task}.
     */
    private void realTimeRun() {
        algorithm.resetForRunning();
        int totalDuration = algorithm.run();
        showMessage("La durata totale del ciclo di attività è di " + totalDuration + " giorni.", false);
        showResult();
    }

    /**
     * Imposta il comparatore per l'ordinamento delle colonne della tabella che
     * contengono una data.
     */
    private void setRowSorter() {
        TableRowSorter tableRowSorter = new TableRowSorter(tableModel);
        DateComparator dateComparator = new DateComparator();
        tableRowSorter.setComparator(2, dateComparator);
        tableRowSorter.setComparator(3, dateComparator);
        tableRowSorter.setComparator(4, dateComparator);
        tableRowSorter.setComparator(5, dateComparator);
        table.setRowSorter(tableRowSorter);
    }

    /**
     * Mostra il messaggio passato come argomento nella label posta sotto la
     * tabella.
     *
     * @param message Il messaggio da mostrare.
     * @param error Vero se è un errore, falso altrimenti.
     */
    private void showMessage(String message, boolean error) {
        if (error) {
            lblOutput.setForeground(Color.red);
        } else {
            lblOutput.setForeground(Color.black);
        }

        lblOutput.setText(message);
    }

    /**
     * Inizializza l'interfaccia grafica ed imposta il titolo.
     *
     * @param args I parametri passati all'interno della linea di comando.
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            App app = new App();
            URL url = App.class.getResource("/resources/img/logo.png");
            ImageIcon img = new ImageIcon(url);
            app.setIconImage(img.getImage());
            app.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDeleteAll;
    private javax.swing.JButton btnDeleteTask;
    private javax.swing.JLabel lblAddTask;
    private javax.swing.JLabel lblDependencies;
    private javax.swing.JLabel lblDuration;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOutput;
    private javax.swing.JLabel lblStartDate;
    private javax.swing.JList<String> listDependencies;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JPanel panel;
    private javax.swing.JSeparator separator;
    private javax.swing.JSpinner spnDate;
    private javax.swing.JSpinner spnDuration;
    private javax.swing.JTable table;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
