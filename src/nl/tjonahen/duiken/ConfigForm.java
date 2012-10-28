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
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import nl.tjonahen.duiken.deco.Config;
import nl.tjonahen.duiken.deco.DecoTable;

/**
 *
 * @author Philippe Tjon-A-Hen
 */
class ConfigForm extends Form {
    private CheckBox deepStop;
    private CheckBox calculateNullDive;
    private TextField personal;
            
    public void init(final DecoTableForm mainForm) {
        setLayout(new BorderLayout());
        final Container cn = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        deepStop = new CheckBox("Gebruik Diep Stop");
        cn.addComponent(deepStop);

        calculateNullDive = new CheckBox("Berekende nultijd duik (herhalings duik)");
        cn.addComponent(calculateNullDive);
        
        personal = new TextField("");
        personal.setConstraint(TextField.NUMERIC);
        Container input = new Container(new BoxLayout(BoxLayout.X_AXIS));
        
        input.addComponent(personal);
        input.addComponent(new Label("liter/minute"));
        cn.addComponent(input);
        
        addComponent(BorderLayout.NORTH, cn);
        addComponent(BorderLayout.SOUTH, new Button(new Command("Ok") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                Config.getInstance().setIncludeDeepStop(deepStop.isSelected());
                Storage.getInstance().writeObject(Config.INCLUDE_DEEP_STOP, Config.getInstance().isIncludeDeepStop());
                final String value = personal.getText();
                final Integer verbruik = Integer.valueOf(value);
                Config.getInstance().setPersonalAir(verbruik);
                Storage.getInstance().writeObject(Config.PERSONAL_AIR, verbruik);
                Config.getInstance().setCalculateNullDives(calculateNullDive.isSelected());
                Storage.getInstance().writeObject(Config.CALCULATE_NULL_DIVE, Config.getInstance().isCalculateNullDives());
                DecoTable.getInstance().calculate(DecoTable.getInstance().getHf());
                mainForm.refresh();
                mainForm.showBack();
            }
            
        }));
        
        setBackCommand(new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                mainForm.showBack();
            }
        });
        
    }

    @Override
    protected void actionCommand(Command cmd) {
        super.actionCommand(cmd);
    }

    public void updateConfig() {
        final Config config = Config.getInstance();
        deepStop.setSelected(config.isIncludeDeepStop());
        personal.setText("" + config.getPersonalAir());
        calculateNullDive.setSelected(config.isCalculateNullDives());
    }
    
}
