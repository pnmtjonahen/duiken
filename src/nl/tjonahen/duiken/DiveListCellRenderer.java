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

import nl.tjonahen.duiken.deco.Dive;
import com.codename1.ui.Component;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.list.ListCellRenderer;

/**
 * ListCelRenderer for a single dive
 * @author Philippe Tjon-A-Hen
 *
 */
public class DiveListCellRenderer implements ListCellRenderer {

    private DiveContainer okContainer = new DiveContainer();
    private ImpossibleDiveContainer nietOkContainer = new ImpossibleDiveContainer();
    private Label focus = new Label("");

    public DiveListCellRenderer() {
        focus.getStyle().setBgTransparency(128);
        focus.getStyle().setBgColor(0xFF0000);
    }

    public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
        final Dive duik = (Dive) value;
        if (duik.getResultSurfaceAirMinutes().getBottom().getTime() < 0.0) {
            nietOkContainer.setDuik(duik);
            return nietOkContainer;

        }
        okContainer.setDuik(duik);
        return okContainer;
    }

    public Component getListFocusComponent(List list) {
        return focus;
    }
}
