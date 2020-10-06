import static org.junit.Assert.*;

import org.junit.Test;

public class CreateInitialPreyTest {

    @Test
    public void testCreateInitialPrey5ElementsCompareToElementsInLocationPrey() {
        int nRows = 100;
        int nColumns = 100;
        int initialPreyQuantity = 5;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createPrey(initialPreyQuantity);
        assertEquals(initialPreyQuantity, flatWorld.getPreyPopulation());
    }
    
    @Test
    public void testCreateInitialPrey5ElementsCompareToElementsInRegionPopulation() {
        int nRows = 100;
        int nColumns = 100;
        int initialPreyQuantity = 5;
        int initialPreyCounter = 0;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createSource(initialPreyQuantity);
        
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                if(flatWorld.regionPopulation[i][j] == 1)
                    initialPreyCounter++;
            }
        }
    assertEquals(initialPreyQuantity, initialPreyCounter);
    }
}
