import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

public class WorldPanel extends JPanel{
    
    private static final long serialVersionUID = 1L;
    private World world;
    
    public WorldPanel(World world) {
        this.world = world;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int N = world.getNRows();
        int M = world.getNColumns();
        Point location = new Point();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.DARK_GRAY);
        int size = Math.min(getWidth() / M, getHeight() / N);
        
        int y = (getHeight() - (size * N)) / 2;
        for (int horz = 0; horz < N; horz++) {
            int x = (getWidth() - (size * M)) / 2;
            for (int vert = 0; vert < M; vert++) {
                g2d.fillRect(x, y, size, size);
                location.setLocation(horz, vert);
                if (world.getPopulationType(location).equals("Source")) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(x, y, size, size);
                    g2d.setColor(Color.DARK_GRAY);
                } else if (world.getPopulationType(location).equals("Prey")) {
                    g2d.setColor(Color.GREEN);
                    g2d.fillRect(x, y, size, size);
                    g2d.setColor(Color.DARK_GRAY);
                } else if (world.getPopulationType(location).equals("Predator")) {
                    g2d.setColor(Color.RED);
                    g2d.fillRect(x, y, size, size);
                    g2d.setColor(Color.DARK_GRAY);
                }                
                x += size;
            }
            y += size;
        }
        g2d.dispose();
    }
}
