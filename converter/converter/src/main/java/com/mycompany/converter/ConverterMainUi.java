package com.mycompany.converter;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConverterMainUi extends javax.swing.JFrame {

    // File array to store the selected file by the user
    private File[] selectedFiles;
    // progress bar list to show the progress for each task 
    private List<JProgressBar> progressBars;
    // for each task there is cancle button whihc is handled by this list;
    private List<JButton> cancelButtons;
    // for each task each swing worker work
    private List<SwingWorker<Void, Integer>> workers;

    public ConverterMainUi() {
        initComponents();
        progressBars = new ArrayList<>();
        cancelButtons = new ArrayList<>();
        workers = new ArrayList<>();
    }

    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        filePanel = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Select files");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        filePanel.setLayout(new javax.swing.BoxLayout(filePanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(filePanel);

        jButton2.setText("Convert selected files");
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));

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
        jFileChooser.setMultiSelectionEnabled(true);
        int returnValue = jFileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] files = jFileChooser.getSelectedFiles();
            selectedFiles = files;
            displaySelectedFiles(files);
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
        JProgressBar progressBar = new JProgressBar(0, 100);
        JButton cancelButton = new JButton("Cancel");

        progressBar.setStringPainted(true);
        progressBar.setValue(0);

        // addging each item for each task eg: proressBar, cancleButton and label with the file name;
        SwingUtilities.invokeLater(() -> {
            filePanel.add(new JLabel("Converting " + inputFile.getName()));
            filePanel.add(progressBar);
            filePanel.add(cancelButton);
            filePanel.revalidate();
            filePanel.repaint();

            progressBars.add(progressBar);
            cancelButtons.add(cancelButton);
        });

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                PdfDocument doc = new PdfDocument();
                doc.loadFromFile(inputFile.getAbsolutePath());

                int totalPages = doc.getPages().getCount();
                for (int i = 0; i < totalPages; i++) {
                    if (isCancelled()) {
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "Conversion canceled for " + inputFile.getName(), "Info", JOptionPane.INFORMATION_MESSAGE));
                        break;
                    }
                    // Simulate the page conversion progress
                    Thread.sleep(500); // Simulate conversion time
                    int progress = (int) (((i + 1.0) / totalPages) * 100);
                    publish(progress);
                }

                if (!isCancelled()) {
                    doc.saveToFile(outputFilePath, FileFormat.DOCX);
                }
                doc.close();

                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int progress = chunks.get(chunks.size() - 1);
                progressBar.setValue(progress);
            }

            @Override
            protected void done() {
                try {
                    get();
                    if (!isCancelled()) {
                        JOptionPane.showMessageDialog(null, "Conversion completed for " + inputFile.getName(), "Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception e) {
                    if (isCancelled()) {
                        JOptionPane.showMessageDialog(null, "Conversion canceled for " + inputFile.getName(), "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Conversion failed for " + inputFile.getName(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };

        workers.add(worker);
        // cancle button to cancle task in the worker threads 
        cancelButton.addActionListener(evt -> {
            worker.cancel(true);
            SwingUtilities.invokeLater(() -> {
                progressBar.setValue(0);
                JOptionPane.showMessageDialog(null, "Conversion canceled for " + inputFile.getName(), "Info", JOptionPane.INFORMATION_MESSAGE);
            });
        });

        worker.execute();
    }

    private void displaySelectedFiles(File[] files) {
        // clear all the things or keep default before starting
        filePanel.removeAll();
        progressBars.clear();
        cancelButtons.clear();
        workers.clear();

        for (File file : files) {
            filePanel.add(new JLabel(file.getName()));
        }

        filePanel.revalidate();
        filePanel.repaint();
    }

    public static void main(String args[]) {
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

        java.awt.EventQueue.invokeLater(() -> new ConverterMainUi().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel filePanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration
}
