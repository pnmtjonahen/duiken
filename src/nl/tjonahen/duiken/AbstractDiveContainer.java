package nl.tjonahen.duiken;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import nl.tjonahen.duiken.deco.Dive;

/**
 * Abstract DiveContain to show a single dive row.
 * @author Philippe Tjon-A-Hen
 */
public abstract class AbstractDiveContainer extends Container {

    private Label maximumDiveDepth = new Label("");
    private Label diveTime = new Label("");
    private Label deco12 = new Label("");
    private Label deco9 = new Label("");
    private Label deco6 = new Label("");
    private Label deco3 = new Label("");
    private Label surfaceAirMinutes = new Label("");

    /**
     * Constructor
     */
    public AbstractDiveContainer() {
        setLayout(new BorderLayout());
        Container cnt = new Container(new BoxLayout(BoxLayout.X_AXIS));
        int single = getStyle().getFont().charWidth('9');
        maximumDiveDepth.setPreferredSize(new Dimension(3 * single, 14));
        maximumDiveDepth.getStyle().setBgTransparency(0);

        diveTime.setPreferredSize(new Dimension(4 * single, 14));
        diveTime.getStyle().setBgTransparency(0);

        deco12.setPreferredSize(new Dimension(2 * single, 14));
        deco12.getStyle().setBgTransparency(0);

        deco9.setPreferredSize(new Dimension(2 * single, 14));
        deco9.getStyle().setBgTransparency(0);

        deco6.setPreferredSize(new Dimension(2 * single, 14));
        deco6.getStyle().setBgTransparency(0);

        deco3.setPreferredSize(new Dimension(3 * single, 14));
        deco3.getStyle().setBgTransparency(0);
        setSurfaceAirMinutesLabelStyle(surfaceAirMinutes);
        
        cnt.addComponent(maximumDiveDepth);
        cnt.addComponent(diveTime);
        cnt.addComponent(deco12);
        cnt.addComponent(deco9);
        cnt.addComponent(deco6);
        cnt.addComponent(deco3);
        cnt.addComponent(surfaceAirMinutes);
        addComponent(BorderLayout.CENTER, cnt);

    }
    
    /**
     * 
     * @param surfaceAirMinutesLabel the label to style
     */
    protected abstract void setSurfaceAirMinutesLabelStyle(final Label surfaceAirMinutesLabel);
    
    /**
     * 
     * @param dive the dive data to show.
     */
    public void setDuik(final Dive dive) {
        maximumDiveDepth.setText("" + dive.getMaximumDiveDepth());
        diveTime.setText("" + dive.getDiveTime());
        deco12.setText(dive.getDisplayValue(dive.getDeco12()));
        deco9.setText(dive.getDisplayValue(dive.getDeco9()));
        deco6.setText(dive.getDisplayValue(dive.getDeco6()));
        deco3.setText(dive.getDisplayValue(dive.getDeco3()));
        if (dive.getResultSurfaceAirMinutes().getBottom().getTime() < 0.0) {
            surfaceAirMinutes.setText("Error");
        } else {
            surfaceAirMinutes.setText(dive.getDisplayValue(dive.getResultSurfaceAirMinutes().total()));
        }
    }
}
