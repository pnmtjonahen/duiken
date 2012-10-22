package nl.tjonahen.duiken.deco;

/**
 *
 * Surface Air Minutes
 * @author Philippe Tjon-A-Hen
 */
public class SurfaceAirMinutes {

    private final boolean valid;
    private final int depth;
    private final float pAverage;
    private final float time;

    /**
     * 
     * @param averagePressure -
     * @param time -
     * @param depth -
     */
    public SurfaceAirMinutes(final float averagePressure, final float time, final int depth) {
        this.pAverage = averagePressure;
        this.time = time;
        this.depth = depth;
        this.valid = true;
    }
    
    /**
     * 
     */
    public SurfaceAirMinutes() {
        this.pAverage = 0;
        this.time = 0;
        this.depth = 0;
        this.valid = false;
    }

    public float calc() {
        return pAverage * time;
    }

    public int getDepth() {
        return depth;
    }

    public float getAveragePressure() {
        return pAverage;
    }

    public float getTime() {
        return time;
    }

    public boolean isValid() {
        return valid;
    }
    
    
}
