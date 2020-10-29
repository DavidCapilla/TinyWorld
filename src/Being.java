
public abstract class Being {

    protected boolean isAlive;
    protected String Type;
    protected boolean readyToReporduce;
    
    public Being() {
        isAlive = true;
        readyToReporduce = false;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public boolean isReadyToReproduce() {
        return readyToReporduce;
    }
}
