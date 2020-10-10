
public class Predator extends Consumer{
    public Predator() {
        Type = "Predator";
        maxEnergy = 1000;
        currentEnergy = maxEnergy;
        lifeSpan = 60;
        brain = new RandomBrain();
    }

    @Override
    protected boolean isFood(Being other) {
        if (other.Type.equals("Prey"))
            return true;
        else
            return false;
    }
}
