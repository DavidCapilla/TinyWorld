
public class Main {
    public static void main (String []args) throws InterruptedException {
        
        FlatWorld flatWorld = new FlatWorld(100, 100);
        flatWorld.createSource(50);
        flatWorld.createPrey(20);
        flatWorld.createPredator(10);
        flatWorld.setTimeToGrowSource(1);
        WorldFrame worldFrame = new WorldFrame(flatWorld);
        
        while(true) {
            Thread.sleep(50);
            flatWorld.update();
            worldFrame.repaint();
        }
    }
}
