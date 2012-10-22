package nl.tjonahen.duiken.deco;

/**
 *  A single stop.
 * @author Philippe Tjon-A-Hen
 */
public class Stop {

    final int depth;
    final SurfaceAirMinutes surfaceAirMinutes;

    public Stop(final int depth, final SurfaceAirMinutes surfaceAirMinutes) {
        this.depth = depth;
        this.surfaceAirMinutes = surfaceAirMinutes;
    }

    public int getDepth() {
        return depth;
    }

    public SurfaceAirMinutes getSurfaceAirMinutes() {
        return surfaceAirMinutes;
    }
    
}
