import java.util.Random;
/**
 * <h1> Representation of a Brain acting randomly </h1>
 * It is a naive implementation of Brain that uses no sensor
 * information to decide a random movement.
 */
public class RandomBrain implements Brain {
    private Random randomMovementGenerator;
    
    public RandomBrain() {
        randomMovementGenerator = new Random();
    }
    
    @Override
    public Movement decideMovement(Sensor sensor) {
        int x = randomMovementGenerator.nextInt(3) - 1;
        int y = randomMovementGenerator.nextInt(3) - 1;
        return new Movement(x, y);
    }
}
