/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.tjonahen.duiken;

import nl.tjonahen.duiken.deco.DecoTable;
import nl.tjonahen.duiken.deco.ResultSurfaceAirMinutes;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author ordina
 */
public class DecoTabelTest {

    @Test
    public void testOvm() {
        DecoTable decoTabel = new DecoTable();

        ResultSurfaceAirMinutes result = decoTabel.calculateSurfaceAirMinutes(9, 300, 0, 0, 0, 0);
        
        Assert.assertEquals(0.9F, result.getDescend().getTime());
        Assert.assertEquals(1.45F, result.getDescend().getAveragePressure());
        Assert.assertEquals(1.3F, result.getDescend().calc());

        Assert.assertEquals(0.9F, result.getAscend().getTime());
        Assert.assertEquals(1.45F, result.getAscend().getAveragePressure());
        Assert.assertEquals(1.3F, result.getAscend().calc());
        
        Assert.assertEquals(299.1F, result.getBottom().getTime());
        Assert.assertEquals(1.9F, result.getBottom().getAveragePressure());
        Assert.assertEquals(568.29F, result.getBottom().calc());
        
        Assert.assertEquals(578.9F, result.total());
    }
}
