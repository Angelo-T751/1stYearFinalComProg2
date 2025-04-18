package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class NeumorphicUtils {
    public static JPanel createNeumorphicPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                Color background = new Color(235, 235, 245);
                Color shadow = new Color(200, 200, 210);
                Color highlight = new Color(255, 255, 255);

                g2.setColor(background);
                g2.fillRoundRect(0, 0, width, height, 30, 30);

                g2.setColor(shadow);
                g2.fillRoundRect(5, 5, width - 10, height - 10, 30, 30);
                g2.dispose();
            }
        };
        panel.setBackground(new Color(235, 235, 245));
        return panel;
    }
}