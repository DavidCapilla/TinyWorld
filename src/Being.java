/**
 * <h1> Abstract class of the populating beings </h1>
 * The Being contains the common traits of all the beings
 * that populate the class World.
 */
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
