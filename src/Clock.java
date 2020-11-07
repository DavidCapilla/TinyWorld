/**
 * <h1> Clock in charge of the time </h1>
 * Gets to know how much time has passed from events.
 */
public class Clock {

    public static final long NANOSECONDSINASECOND = 1000000000;
    private long origin;
        
    public Clock() {
        setChronometerOriginToNow();
    }
    
    public void setChronometerOriginToNow() {
        origin = System.nanoTime();
    }
    
    public double getTimeFromOriginInSeconds() {
        return (double) (System.nanoTime() - origin) / NANOSECONDSINASECOND;
    }
    
    public boolean hasPassedMoreTimeThanThatFromOrigin(double timeInSeconds) {
        return System.nanoTime() - origin > (long) (timeInSeconds * NANOSECONDSINASECOND);
    }
}
