import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class WorldPanel extends JPanel{
    
    private static final long serialVersionUID = 1L;
    private World world;
    
    public WorldPanel(World world) {
        this.world = world;
        //setLayout(new GridLayout(3, 1));
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int N = world.nRows;
        int M = world.nColumns;
        Graphics2D g2d = (Graphics2D) g.create();
        int size = Math.min(getWidth() / M, getHeight() / N);
        
        int y = (getHeight() - (size * N)) / 2;
        for (int horz = 0; horz < N; horz++) {
            int x = (getWidth() - (size * M)) / 2;
            for (int vert = 0; vert < M; vert++) {
                g.drawRect(x, y, size, size);
                x += size;
            }
            y += size;
        }
        g2d.dispose();
    }
}
