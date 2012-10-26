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

import com.codename1.io.Storage;
import com.codename1.ui.Command;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import nl.tjonahen.duiken.deco.Config;
import nl.tjonahen.duiken.deco.DecoTable;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
public class DecoTableForm extends Form {

    private Config config;
    private final AirConsumptionForm airConsumptionDialog;
    private List current;

    public DecoTableForm() {
        super("NOB - Duiktabel");
        setupConfig();
        airConsumptionDialog = new AirConsumptionForm();
        final ConfigForm configForm = new ConfigForm();
        configForm.init(this);

        Command exitCommand = new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                Display.getInstance().exitApplication();
            }
        };
        setBackCommand(exitCommand);
        addCommand(new AirConsumptionCommand(airConsumptionDialog, this));
        addCommand(new ConfigCommand(configForm));

        setScrollable(false);


        setLayout(new BorderLayout());
        current = getList();
        addComponent(BorderLayout.CENTER, current);

    }
    
    public void refresh() {
        final List newList = getList();
        replace(current, newList, null);
        current = newList;
    }
    
    private List getList() {
        final DecoTable dt = new DecoTable();
        final List l = new List(new DiveListModel(dt));
        l.setRenderer(new DiveListCellRenderer());
        l.setFixedSelection(List.FIXED_NONE);
        l.addActionListener(new ShowDiveCalculationActionListner(airConsumptionDialog, this));
        return l;
    }

    private void setupConfig() {
        final Storage storage = Storage.getInstance();

        config = Config.getInstance();
        config.setIncludeDeepStop(false);
        config.setPersonalAir(new Integer(16));

        Object object = storage.readObject(Config.INCLUDE_DEEP_STOP);
        if (object == null) {
            storage.writeObject(Config.INCLUDE_DEEP_STOP, config.isIncludeDeepStop());
            storage.flushStorageCache();
        } else {
            config.setIncludeDeepStop((Boolean) object);
            
        }
        object = storage.readObject(Config.PERSONAL_AIR);
        if (object == null) {
            storage.writeObject(Config.PERSONAL_AIR, config.getPersonalAir());
            storage.flushStorageCache();
        } else {
            config.setPersonalAir((Integer) object);
            
        }
    }
}
