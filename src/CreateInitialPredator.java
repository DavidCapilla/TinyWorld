import static org.junit.Assert.*;

import org.junit.Test;

public class CreateInitialPredator {

    @Test
    public void testCreateInitialPredator4Elements() {
        int nRows = 100;
        int nColumns = 100;
        int initialPredatorQuantity = 10;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createInitialPredator(initialPredatorQuantity);
        assertEquals(initialPredatorQuantity, flatWorld.getPredatorPopulation());
    }

}
