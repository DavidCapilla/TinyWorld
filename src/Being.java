
public abstract class Being {
    private double maxEnergy;
    private double currentEnergy;
    private double lifeSpan;
    private boolean isAlive;
    private Clock internalClock;
    protected String Type;
    
    public Being() {
        isAlive = true;
    }
    
    public void consumeEnergy() {
        currentEnergy -= maxEnergy/lifeSpan * internalClock.getTimeFromOriginInSeconds();
        internalClock.setChronometerOriginToNow();
    }
    
    public boolean isAlive() {
        return isAlive;
    }
}
