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

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.SelectionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.ListCellRenderer;
import com.codename1.ui.list.ListModel;
import nl.tjonahen.duiken.deco.Config;
import nl.tjonahen.duiken.deco.DecoTable;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
class SurfaceTimeCommand extends Command {
    private final Form selectForm;
    private final char hg;
    public SurfaceTimeCommand(final Form toonDuikBerekeningForm, final DecoTableForm previousForm, char hg) {
        super("Herhalings duik");
        this.hg = hg;
        selectForm = new Form("Oppervlakte interval");
        selectForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        selectForm.setBackCommand(new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                toonDuikBerekeningForm.showBack();
            }
        });

        setup(previousForm);
    }

    private void setup(final DecoTableForm previousForm) {
            final List l = new List(new ListModel() {
            private int selected;
            public Object getItemAt(int index) {
                return intervals[index];
            }
            public int getSize() {
                return intervals.length;
            }
            public int getSelectedIndex() {
                return selected;
            }
            public void setSelectedIndex(int index) {
                selected = index;
            }
            public void addDataChangedListener(DataChangedListener l) { }
            public void removeDataChangedListener(DataChangedListener l) { }
            public void addSelectionListener(SelectionListener l) {       }
            public void removeSelectionListener(SelectionListener l) { }
            public void addItem(Object item) {  }
            public void removeItem(int index) {            }
        });


        final Label focus = new Label("");
        focus.setUIID("Focus");

        final Label label = new Label("");
        l.setRenderer(new ListCellRenderer() {
           public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
                final Interval interval = (Interval) value;
                label.setText(interval.getLabel());
                return label;
            }
            public Component getListFocusComponent(List list) {
                return focus;
            }
        });
        l.setFixedSelection(List.FIXED_NONE);
        l.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                final List list = (List) evt.getSource();
                final int selectedIndex = list.getModel().getSelectedIndex();
                final Interval interval = (Interval) list.getModel().getItemAt(selectedIndex);
                
                int iHg = (int)hg - (int)'A';
                int oi = interval.getIndex();
                double hf = DecoTable.getInstance().getHf(iHg, oi);
                if (hf > 0.0) {
                    Config.getInstance().setSecondDive(true);
                    DecoTable.getInstance().calculate(hf);
                    previousForm.refresh();
                    previousForm.showBack();
                } else {
                    final Dialog d = new Dialog("Fout");
                    d.setLayout(new BorderLayout());
                    d.addComponent(BorderLayout.NORTH, new Label("Herhalings duik is niet mogelijk!"));
                    d.addComponent(BorderLayout.SOUTH, new Button(new Command("Ok")));
                    d.setDialogType(Dialog.TYPE_ERROR);
                    d.show();
                }
            }
        });
        selectForm.addComponent(l);

    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        evt.consume();
        selectForm.show();
    }
    
    private static class Interval {
        private final int index;
        private final String label;

        public Interval(int index, String label) {
            this.index = index;
            this.label = label;
        }

        public int getIndex() {
            return index;
        }

        public String getLabel() {
            return label;
        }
        
        
    }
    private static Interval[] intervals = {
        new Interval( 0, " 0:15 <-  0:29"),
        new Interval( 1, " 0:30 <-  0:59"),
        new Interval( 2, " 1:00 <-  1:29"),
        new Interval( 3, " 1:30 <-  1:59"),
        new Interval( 4, " 2:00 <-  2:59"),
        new Interval( 5, " 3:00 <-  3:59"),
        new Interval( 6, " 4:00 <-  5:59"),
        new Interval( 7, " 6:00 <-  8:59"),
        new Interval( 8, " 9:00 <- 11:59"),
        new Interval( 9, "12:00 <- 14:59"),
        new Interval(10, "15:00 <- 18:00")
    };
    
    
}
