/*
 * Copyright (C) 2012 Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.duiken;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
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
    private Label hg = new Label("");

    /**
     * Constructor
     */
    public AbstractDiveContainer() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container cnt = new Container(new GridLayout(1, 8));
        
        
        int single = getStyle().getFont().charWidth('9');
//        maximumDiveDepth.setPreferredSize(new Dimension(3 * single, 14));
//        diveTime.setPreferredSize(new Dimension(4 * single, 14));
//        deco12.setPreferredSize(new Dimension(4 * single, 14));
//        deco9.setPreferredSize(new Dimension(4 * single, 14));
//        deco6.setPreferredSize(new Dimension(4 * single, 14));
//        deco3.setPreferredSize(new Dimension(4 * single, 14));
//        hg.setPreferredW(4 * single);
        maximumDiveDepth.setRTL(true);
        diveTime.setRTL(true);
        deco12.setRTL(true);
        deco9.setRTL(true);
        deco6.setRTL(true);
        deco3.setRTL(true);
        hg.setRTL(true);


        setSurfaceAirMinutesLabelStyle(surfaceAirMinutes);
        surfaceAirMinutes.setPreferredW(5 * single);
        surfaceAirMinutes.setRTL(true);
        cnt.addComponent(maximumDiveDepth);
        cnt.addComponent(diveTime);
        cnt.addComponent(deco12);
        cnt.addComponent(deco9);
        cnt.addComponent(deco6);
        cnt.addComponent(deco3);
        cnt.addComponent(hg);
        cnt.addComponent(surfaceAirMinutes);
        addComponent(cnt);

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
        hg.setText("" + dive.getHg());
    }
}
