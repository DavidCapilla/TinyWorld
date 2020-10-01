import static org.junit.Assert.*;

import org.junit.Test;

public class CreateInitialPreyTest {

    @Test
    public void testCreateInitialPrey5Elements() {
        int nRows = 100;
        int nColumns = 100;
        int initialPreyQuantity = 10;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createInitialPrey(initialPreyQuantity);
        assertEquals(initialPreyQuantity, flatWorld.getPreyPopulation());
    }

}
