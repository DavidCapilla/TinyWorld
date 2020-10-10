import java.util.Random;

public class RandomBrain extends Brain {
    private Random randomMovementGenerator;
    
    public RandomBrain() {
        randomMovementGenerator = new Random();
    }
    
    @Override
    public Movement decideDirectionOfMovement(Sensor sensor) {
        int x = randomMovementGenerator.nextInt(3) - 1;
        int y = randomMovementGenerator.nextInt(3) - 1;
        return new Movement(x, y);
    }
}
