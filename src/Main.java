
public class Main {
    public static void main (String []args) {
        
        FlatWorld flatWorld = new FlatWorld(100, 100);
        flatWorld.createInitialSource(10);
        flatWorld.createInitialPrey(5);
        flatWorld.createInitialPredator(4);
        WorldFrame worldFrame = new WorldFrame(flatWorld);
    }
}
