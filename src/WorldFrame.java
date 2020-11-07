import javax.swing.JFrame;
/**
 * <h1> Window to a Panel </h1>
 * Contains the WorldPanel and allows its visual representation.
 */
public class WorldFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public WorldFrame(World world) {
        setVisible(true);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setTitle("World Frame");
        setContentPane(new WorldPanel(world));
    }
}