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

import nl.tjonahen.duiken.deco.DecoTable;
import nl.tjonahen.duiken.deco.ResultSurfaceAirMinutes;
import nl.tjonahen.duiken.deco.Dive;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.list.ListModel;

/**
 * List model for dives
 *
 * @author Philippe Tjon-A-Hen
 */
public class DiveListModel implements ListModel {

    private final Dive[] duiken;
    private int selected = 0;

    public DiveListModel(final DecoTable dt) {
        final int[][] table = dt.getTable();
        duiken = new Dive[table.length];
        for (int i = 0; i < table.length; i++) {
            final ResultSurfaceAirMinutes total = dt.calculateSurfaceAirMinutes(table[i][0],
                    table[i][1],
                    table[i][2],
                    table[i][3],
                    table[i][4],
                    table[i][5]);
            duiken[i] = new Dive();
            duiken[i].setMaxmimumDiveDepthdd(table[i][0]);
            duiken[i].setDiveTime(table[i][1]);
            duiken[i].setDeco12(table[i][2]);
            duiken[i].setDeco9(table[i][3]);
            duiken[i].setDeco6(table[i][4]);
            duiken[i].setDeco3(table[i][5]);
            duiken[i].setResultSurfaceAirMinutes(total);
        }
    }

    public Object getItemAt(int index) {
        return duiken[index];
    }

    public int getSize() {
        return duiken.length;
    }

    public int getSelectedIndex() {
        return selected;
    }

    public void setSelectedIndex(int index) {
        selected = index;
    }

    public void addDataChangedListener(DataChangedListener l) {
    }

    public void removeDataChangedListener(DataChangedListener l) {
    }

    public void addSelectionListener(SelectionListener l) {
    }

    public void removeSelectionListener(SelectionListener l) {
    }

    public void addItem(Object item) {
    }

    public void removeItem(int index) {
    }
}
