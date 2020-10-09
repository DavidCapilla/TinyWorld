import static org.junit.Assert.*;

import org.junit.Test;

public class PredatorTest {

    @Test
    public void isSourceFoodHasToBeFale() {
        Being predator = new Predator();
        Being source = new Source();
        assertFalse(((Consumer) predator).isFood(source));
    }
    
    @Test
    public void isPreyFoodHasToBeTrue() {
        Being predator = new Predator();
        Being prey = new Prey();
        assertTrue(((Consumer) predator).isFood(prey));
    }
    
    @Test
    public void isPredatorFoodHasToBeFalse() {
        Being predator = new Predator();
        Being otherPredator = new Predator();
        assertFalse(((Consumer) predator).isFood(otherPredator));
    }

}
