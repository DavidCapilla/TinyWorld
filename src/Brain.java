/**
 * <h1> Interface defining the intelligence of the Beings </h1>
 * Decides what has to be the next movement depending on the sensed.
 */
public interface Brain {
    public Movement decideMovement(Sensor sensor);
}
