import org.junit.Test;
import sensor.Sensor;

import java.io.IOException;

public class SensorTest {
    @Test
    public void testCapture() throws IOException, InterruptedException {
        Sensor sensor = new Sensor();

        String capture = sensor.capture();
        System.out.println(capture.length());

        sensor.close();
    }
}
