
public abstract class Consumer extends Being{
    protected double maxEnergy;
    protected double currentEnergy;
    protected double lifeSpan;
    private Clock internalClock;
    protected Brain brain;
    protected Sensor sensor;
        
    public Consumer() {
        internalClock = new Clock();
    }
    
    protected void eat(Being other) {
        if (isFood(other)) {
            other.isAlive = false;
            other.readyToReporduce = false;
            currentEnergy = maxEnergy;
            readyToReporduce = true;
            internalClock.setChronometerOriginToNow();    
        }
    }
    
    protected abstract boolean isFood(Being other);

    protected void consumeEnergy() {
        currentEnergy -= maxEnergy/lifeSpan * internalClock.getTimeFromOriginInSeconds();
        internalClock.setChronometerOriginToNow();
        if (currentEnergy <= 0)
            isAlive = false;
    }
    
    public Movement getDecidedMovement() {
        return brain.decideMovement(sensor);
    }
     
}
