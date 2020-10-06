
public abstract class Being {
    private int maxEnergy;
    private int currentEnergy;
    private boolean isAlive;
    protected String Type;
    
    public Being() {
        isAlive = false;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
}
