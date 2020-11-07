
public class Main {
    public static void main (String []args) throws InterruptedException {
        
        FlatWorld flatWorld = new FlatWorld(100, 100);
        flatWorld.createSource(500);
        flatWorld.createPrey(100);
        flatWorld.createPredator(50);
        flatWorld.setTimeToGrowSource(0.1);
        WorldFrame worldFrame = new WorldFrame(flatWorld);
        
        while (true) {
            Thread.sleep(50);
            flatWorld.update();
            worldFrame.repaint();
        }
    }
}
