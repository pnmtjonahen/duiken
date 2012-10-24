package nl.tjonahen.duiken;

import com.codename1.ui.Label;

/**
 * Dive container who styles the surfaceAirMinutes Label as an error label.
 * @author Philippe Tjon-A-Hen
 */
public class ImpossibleDiveContainer extends AbstractDiveContainer {

    /**
     * {@inheritDoc } 
     */
    @Override
    protected void setSurfaceAirMinutesLabelStyle(final Label surfaceAirMinutes) {
        surfaceAirMinutes.getStyle().setBgColor(0xFF0000);
        surfaceAirMinutes.setRTL(false);
        
//        surfaceAirMinutes.getStyle().setAlignment(RIGHT);
    }
    
}
