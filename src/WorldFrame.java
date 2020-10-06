import javax.swing.JFrame;

public class WorldFrame extends JFrame {

    public WorldFrame(World world) {
        setVisible(true);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setTitle("World Frame");
        setContentPane(new WorldPanel(world));
    }
}