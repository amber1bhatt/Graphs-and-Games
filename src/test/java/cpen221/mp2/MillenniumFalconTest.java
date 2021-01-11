package cpen221.mp2;

import cpen221.mp2.controllers.Kamino;
import cpen221.mp2.spaceship.MillenniumFalcon;
import cpen221.mp2.views.BenchmarkView;
import org.junit.Assert;
import org.junit.Test;

public class MillenniumFalconTest {

    @Test
    public void testHuntGatherMediumMap() {
        Kamino testGalaxy = new Kamino(8870737974647040710L, new MillenniumFalcon(), new BenchmarkView());

        Assert.assertTrue(testGalaxy.huntSucceeded());
        Assert.assertTrue(testGalaxy.gatherSucceeded());
    }

    @Test
    public void testHuntGatherLargeMap() {
        Kamino testGalaxy = new Kamino(7431515110072347536L, new MillenniumFalcon(), new BenchmarkView());

        Assert.assertTrue(testGalaxy.huntSucceeded());
        Assert.assertTrue(testGalaxy.gatherSucceeded());
    }

    @Test
    public void testHuntGather() {

        for (long i = 1L; i < 7431515110072347536L; i += 431515110072347536L) {
            Kamino testGalaxy = new Kamino(i, new MillenniumFalcon(), new BenchmarkView());

            Assert.assertTrue(testGalaxy.huntSucceeded());
            Assert.assertTrue(testGalaxy.gatherSucceeded());
        }
    }

}
