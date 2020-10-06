
public class Clock {

    public static final long NANOSECONDSINASECOND = 1000000000;
    private long timeToGrowSource;
    private long lastTimeSourceGenerated;
    
    public void setTimeToGrowSource (double timeInSeconds) {
        timeToGrowSource = (long) (timeInSeconds * NANOSECONDSINASECOND);
    }
    
    public void setTimeSourceGeneratedToNow() {
        lastTimeSourceGenerated = System.nanoTime();
    }
    
    public boolean isTimeToGrowSource() {
        return System.nanoTime() - lastTimeSourceGenerated > timeToGrowSource;
    }
}
