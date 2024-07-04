package com.mycompany.converter;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConverterMainUi extends javax.swing.JFrame {

    private File[] selectedFiles;
    private List<JProgressBar> progressBars;
    private List<JButton> cancelButtons;

    public ConverterMainUi() {
        initComponents();
        progressBars = new ArrayList<>();
        cancelButtons = new ArrayList<>();
    }

    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        filePanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Select files");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        filePanel.setLayout(new javax.swing.BoxLayout(filePanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(filePanel);

        jButton2.setText("Convert selected files");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setMultiSelectionEnabled(true); // Enable multiple file selection
        int returnValue = jFileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Files have been selected
            File[] files = jFileChooser.getSelectedFiles();
            selectedFiles = files;
            displaySelectedFiles(files);
        } else {
            // No file was selected
        }
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedFiles == null || selectedFiles.length == 0) {
            JOptionPane.showMessageDialog(null, "No files selected", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (File file : selectedFiles) {
            String outputFilePath = "./output/todoc_" + file.getName().replaceAll("\\s+", "_") + ".docx";
            addConversionTask(file, outputFilePath);
        }
    }

    private void addConversionTask(File inputFile, String outputFilePath) {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                JProgressBar progressBar = new JProgressBar(0, 100);
                JButton cancelButton = new JButton("Cancel");

                progressBar.setStringPainted(true);
                progressBar.setValue(0);

                filePanel.add(new JLabel("Converting " + inputFile.getName()));
                filePanel.add(progressBar);
                filePanel.add(cancelButton);
                filePanel.revalidate();
                filePanel.repaint();

                progressBars.add(progressBar);
                cancelButtons.add(cancelButton);

                cancelButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        cancel(true);
                    }
                });

                // Perform PDF to DOCX conversion
                PdfDocument doc = new PdfDocument();
                doc.loadFromFile(inputFile.getAbsolutePath());

                int totalPages = doc.getPages().getCount();
                for (int i = 0; i < totalPages; i++) {
                    if (isCancelled()) {
                        JOptionPane.showMessageDialog(null, "Cancled ", "Info", JOptionPane.INFORMATION_MESSAGE);
                        break;
                        }
                    Thread.sleep(1000);
                    doc.saveToFile(outputFilePath, FileFormat.DOCX);
                    int progress = (int) (((i + 1.0) / totalPages) * 100);
                    publish(progress);
                }

                doc.close();

                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                progressBars.get(progressBars.size() - 1).setValue(progress);
            }

            @Override
            protected void done() {
                JOptionPane.showMessageDialog(null, "Conversion completed for " + inputFile.getName(), "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        worker.execute();
    }

    private void displaySelectedFiles(File[] files) {
        filePanel.removeAll();
        progressBars.clear();
        cancelButtons.clear();

        for (File file : files) {
            filePanel.add(new JLabel(file.getName()));
        }

        filePanel.revalidate();
        filePanel.repaint();
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConverterMainUi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConverterMainUi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel filePanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration                   
}
