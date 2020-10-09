
public abstract class Consumer extends Being{
    private double maxEnergy;
    private double currentEnergy;
    private double lifeSpan;
    private Clock internalClock;
    
    public void eat(Being other) {
        other.isAlive = false;
        currentEnergy = maxEnergy;
        internalClock.setChronometerOriginToNow();
    }
    
    public void consumeEnergy() {
        currentEnergy -= maxEnergy/lifeSpan * internalClock.getTimeFromOriginInSeconds();
        internalClock.setChronometerOriginToNow();
    }
}
