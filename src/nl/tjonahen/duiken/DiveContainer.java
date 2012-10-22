package nl.tjonahen.duiken;

import com.codename1.ui.Label;

/**
 *
 * Dive container who styles the surfaceAirMinute label in a normal style.
 * @author Philippe Tjon-A-Hen
 */
public class DiveContainer extends AbstractDiveContainer {

    /**
     * 
     * {@inheritDoc } 
     */
    @Override
    protected void setSurfaceAirMinutesLabelStyle(final Label surfaceAirMinutes) {
        surfaceAirMinutes.getStyle().setBgTransparency(0);
        surfaceAirMinutes.getStyle().setAlignment(RIGHT);
    }
}
