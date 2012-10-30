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

import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import nl.tjonahen.duiken.deco.Config;
import nl.tjonahen.duiken.deco.DecoTable;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class DecoTableForm extends Form {
    public static final String NOB_DUIKTABEL = "NOB - Duiktabel";

    private final AirConsumptionForm airConsumptionDialog;
    private List current;
    private FirstDiveCommand firstDiveCommand = null;

    
    public DecoTableForm() {
        super(NOB_DUIKTABEL);
        airConsumptionDialog = new AirConsumptionForm();
        final ConfigForm configForm = new ConfigForm();
        configForm.init(this);

        Command exitCommand = new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (Config.getInstance().isSecondDive()) {
                    Config.getInstance().setSecondDive(false);
                    DecoTable.getInstance().calculate(1.0);
                    refresh();
                    showBack();
                } else {
                    Display.getInstance().exitApplication();
                }
            }
        };
        setBackCommand(exitCommand);
        addCommand(new AirConsumptionCommand(airConsumptionDialog, this));
        addCommand(new ConfigCommand(configForm));

        setScrollable(false);

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        final Container cnt = new Container(new GridLayout(1, 7));
        final Label mdd = new Label("mdd");
        mdd.setRTL(focusScrolling);
        cnt.addComponent(mdd);
        final Label dt = new Label("dt");
        dt.setRTL(true);
        cnt.addComponent(dt);
        final Label deco12 = new Label("12");
        deco12.setRTL(true);
        cnt.addComponent(deco12);
        final Label deco9 = new Label("9");
        deco9.setRTL(true);
        cnt.addComponent(deco9);
        final Label deco6 = new Label("6");
        deco6.setRTL(true);
        cnt.addComponent(deco6);
        final Label deco3 = new Label("3");
        deco3.setRTL(true);
        cnt.addComponent(deco3);
        final Label hg = new Label("hg");
        hg.setRTL(true);
        cnt.addComponent(hg);
//        final Label ovm = new Label("ovm");
        
//        ovm.setRTL(true);
//        final int single = getStyle().getFont().charWidth('9');
//        ovm.setPreferredW(5 * single);
//        cnt.addComponent(ovm);

        addComponent(cnt);
        
        current = getList();
        addComponent(current);

    }
    
    public void refresh() {
        String title = NOB_DUIKTABEL;
        if (Config.getInstance().isSecondDive()) {
            title += " - Herhaling (hf=" + DecoTable.getInstance().getHf() + ")";
            firstDiveCommand = new FirstDiveCommand(this);
            addCommand(firstDiveCommand);
        } else {
            if (firstDiveCommand != null) {
                removeCommand(firstDiveCommand);
                firstDiveCommand = null;
            }
        }
        setTitle(title);
        final List newList = getList();
        replace(current, newList, null);
        current = newList;
        
    }
    
    private List getList() {
        
        final List l = new List(new DiveListModel());
        l.setRenderer(new DiveListCellRenderer());
        l.setFixedSelection(List.FIXED_NONE);
        l.addActionListener(new ShowDiveCalculationActionListner(airConsumptionDialog, this));
        
        return l;
    }

}
