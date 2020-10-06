import static org.junit.Assert.*;

import org.junit.Test;

public class CreateInitialPredatorTest {

    @Test
    public void testCreateInitialPredator4ElementsCompareToElementsInLocationPredator() {
        int nRows = 100;
        int nColumns = 100;
        int initialPredatorQuantity = 4;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createPredator(initialPredatorQuantity);
        assertEquals(initialPredatorQuantity, flatWorld.getPredatorPopulation());
    }

    @Test
    public void testCreateInitialPrey5ElementsCompareToElementsInRegionPopulation() {
        int nRows = 100;
        int nColumns = 100;
        int initialPredatorQuantity = 5;
        int initialPredatorCounter = 0;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createSource(initialPredatorQuantity);
        
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                if(flatWorld.regionPopulation[i][j] == 1)
                    initialPredatorCounter++;
            }
        }
    assertEquals(initialPredatorQuantity, initialPredatorCounter);
    }
}
