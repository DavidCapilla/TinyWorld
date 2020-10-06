
public abstract class Being {
    private int maxEnergy;
    private int currentEnergy;
    private boolean isAlive;
    
    public Being() {
        isAlive = false;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
}
