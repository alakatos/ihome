package hu.lakati.ihome.util;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MovingAverageTest {
    @Test
    public void testGetAvg() {
        MovingAverage avgCalc = new MovingAverage(100);
        for (int i = 0; i < 100; i++) {
            avgCalc.add(i);
        }
        assertThat(avgCalc.getAvg(), equalTo(49.5d));
        for (int i = 100; i < 150; i++) {
            avgCalc.add(i);
        }
        assertThat(avgCalc.getAvg(), equalTo(99.5d));
        for (int i = 100; i < 200; i++) {
            avgCalc.add(i);
        }
        assertThat(avgCalc.getAvg(), equalTo(149.5));
    }

    @Test
    public void testEmptyAvg() {
        MovingAverage avgCalc = new MovingAverage(100);
        assertThat(avgCalc.getAvg(), equalTo(0d));
    }

    @Test
    public void testUnderAndOverCapacity() {
        MovingAverage avgCalc = new MovingAverage(3);
        
        avgCalc.add(1);
        assertThat(avgCalc.getAvg(), equalTo(1d));
        avgCalc.add(2);
        assertThat(avgCalc.getAvg(), equalTo(1.5));
        avgCalc.add(3);
        assertThat(avgCalc.getAvg(), equalTo(2d));

        avgCalc.add(4);
        assertThat(avgCalc.getAvg(), equalTo(3d));
    }

}
