import static org.junit.Assert.*;

import org.junit.Test;

public class PreyTest {

    @Test
    public void isSourceFoodHasToBeTrue() {
        Being prey = new Prey();
        Being source = new Source();
        assertTrue(((Consumer) prey).isFood(source));
    }
    
    @Test
    public void isPreyFoodHasToBeFalse() {
        Being prey = new Prey();
        Being otherPrey = new Prey();
        assertFalse(((Consumer) prey).isFood(otherPrey));
    }
    
    @Test
    public void isPredatorFoodHasToBeFalse() {
        Being prey = new Prey();
        Being predator = new Predator();
        assertFalse(((Consumer) prey).isFood(predator));
    }

}
