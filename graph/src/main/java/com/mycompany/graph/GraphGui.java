
package com.mycompany.graph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;


public class GraphGui extends JPanel {
    private final AtomicInteger generation;
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    public GraphGui() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        this.generation = new AtomicInteger(0);
        final Timer timer = new Timer(5, (ActionEvent e) -> {

        });
    }
}
