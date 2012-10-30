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
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.GridLayout;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class DiveContainerGridLayout extends GridLayout {
    private final Label surfaceAirMinutes;
    public DiveContainerGridLayout(final Label surfaceAirMinutes) {
        super(1,8);
        this.surfaceAirMinutes = surfaceAirMinutes;
    }

    @Override
    public Dimension getPreferredSize(final Container parent) {
        return new Dimension(Display.getInstance().getDisplayWidth()-100, 20);
    }
    
}
