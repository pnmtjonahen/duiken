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
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.list.ListModel;

/**
 * List model for dives
 *
 * @author Philippe Tjon-A-Hen
 */
public class DiveListModel implements ListModel {
    private int selected = 0;

    public Object getItemAt(int index) {
        return DecoTable.getInstance().getDives().get(index);
    }

    public int getSize() {
        return DecoTable.getInstance().getDives().size();
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
