package com.mycompany.graph;

import javax.swing.*;

import com.mycompany.graph.util.TSPGen;
import com.mycompany.graph.util.TSPPopulation;
import com.mycompany.graph.util.TSPUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class World extends JPanel {


    private final TSPPopulation population;

    private final AtomicInteger generation;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;


    private World() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        this.population = new TSPPopulation(TSPUtils.CITIES , 1000);
        this.generation = new AtomicInteger(0);
        final Timer timer = new Timer(5, (ActionEvent e) -> {
            this.population.update();
            repaint();
            
        });
        timer.start();
    }


    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
        final Graphics2D g = (Graphics2D) graphics;
        g.setColor(
                Color.CYAN
        );
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawString("gen: " + this.generation.incrementAndGet(), 350, 15);
        g.drawString("shortest path: "+ String.format("%.2f", this.population.getAlpha().calculateDistance()), 500, 15);
        drawBestChromosome(g);
    
    }

    private void drawBestChromosome(final Graphics2D g) {
        final java.util.List<TSPGen> chromosome = this.population.getAlpha().getChrosome();
        g.setColor(Color.WHITE);
        for (int i =0; i<chromosome.size() -1 ; i++) {
            TSPGen gene = chromosome.get(i);
            TSPGen neighbour = chromosome.get(i+1);
            g.drawLine(gene.getX(), gene.getY(), neighbour.getX(), neighbour.getY());
        }
        g.setColor(Color.RED);
        for (final TSPGen gene: chromosome) {
            g.fillOval(gene.getX(), gene.getY(), 5, 5);
        }

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("Genetic Algorithm");
            frame.setResizable(false);
            frame.add(new World(), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}


