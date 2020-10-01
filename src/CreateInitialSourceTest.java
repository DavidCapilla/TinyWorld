import static org.junit.Assert.*;

import org.junit.Test;

public class CreateInitialSourceTest {

    @Test
    public void testCreateInitialSource10ElementsCompareToElementsInLocationSource() {
        int nRows = 100;
        int nColumns = 100;
        int initialSourceQuantity = 10;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createInitialSource(initialSourceQuantity);
        assertEquals(initialSourceQuantity, flatWorld.getSourcePopulation());
    }   
    
    @Test
    public void testCreateInitialSource10ElementsCompareToElementsInRegionPopulation() {
        int nRows = 100;
        int nColumns = 100;
        int initialSourceQuantity = 10;
        int initialSourceCounter = 0;
        FlatWorld flatWorld = new FlatWorld(nRows, nColumns);
        flatWorld.createInitialSource(initialSourceQuantity);
        
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                if(flatWorld.regionPopulation[i][j] == 1)
                    initialSourceCounter++;
            }
        }
        assertEquals(initialSourceQuantity, initialSourceCounter);
    }   
}