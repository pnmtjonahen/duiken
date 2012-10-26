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
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;

/**
 * Air consumption form to calculate the personal air consumption per dive cylinder.
 * @author Philippe Tjon-A-Hen
 */
public class AirConsumptionForm extends Form {

    private Form mainForm;
    private TextField input;
    private Label l6200;
    private Label l6232;
    private Label l7200;
    private Label l7232;
    private Label l10200;
    private Label l10232;
    private Label l12200;
    private Label l12232;
    private Label l14200;
    private Label l14232;
    private Label l15200;
    private Label l15232;
    private Label l20200;
    private Label l20232;
    

    public AirConsumptionForm() {
        super("Verbruik");
        Container main = new Container(new BoxLayout(BoxLayout.Y_AXIS));
//        main.getStyle().setBgTransparency(255);
        
        Container cnt = new Container(new GridLayout(1,3));
        input = new TextField("16");
        input.setConstraint(TextField.NUMERIC);
        
        cnt.addComponent(input);
        cnt.addComponent(new Label("liter/minute"));
        cnt.addComponent(new Button(new Command("Bereken") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                calculate();
            }
            
        }));
        
        main.addComponent(cnt);

        cnt = new Container(new GridLayout(8, 3));
        
        l6200 = new Label("");
        l6232 = new Label("");
        l7200 = new Label("");
        l7232 = new Label("");
        l10200 = new Label("");
        l10232 = new Label("");
        l12200 = new Label("");
        l12232 = new Label("");
        l14200 = new Label("");
        l14232 = new Label("");
        l15200 = new Label("");
        l15232 = new Label("");
        l20200 = new Label("");
        l20232 = new Label("");

        cnt.addComponent(new Label("liters"));
        cnt.addComponent(new Label("200-50 bar"));
        cnt.addComponent(new Label("232-50 bar"));

        cnt.addComponent(new Label("6"));
        cnt.addComponent(l6200);
        cnt.addComponent(l6232);

        cnt.addComponent(new Label("7"));
        cnt.addComponent(l7200);
        cnt.addComponent(l7232);

        cnt.addComponent(new Label("10"));
        cnt.addComponent(l10200);
        cnt.addComponent(l10232);

        cnt.addComponent(new Label("12"));
        cnt.addComponent(l12200);
        cnt.addComponent(l12232);

        cnt.addComponent(new Label("14"));
        cnt.addComponent(l14200);
        cnt.addComponent(l14232);

        cnt.addComponent(new Label("15"));
        cnt.addComponent(l15200);
        cnt.addComponent(l15232);

        cnt.addComponent(new Label("20"));
        cnt.addComponent(l20200);
        cnt.addComponent(l20232);

        main.addComponent(cnt);

        addComponent(main);
        setScrollableY(true);
        setBackCommand(new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                mainForm.showBack();
            }
        });
        calculate();
    }

    /**
     * 
     * @param form the previous form
     */
    public void setBackForm(final Form form) {
        this.mainForm = form;
    }

    /**
     * Recalculate and update the values on screen.
     */
    public final void calculate() {
        final String value = input.getText();
        int verbruik = Integer.valueOf(value);
        
        l6200.setText("" + (6*(200-50))/verbruik);
        l6232.setText("" + (6*(232-50))/verbruik);
        l7200.setText("" + (7*(200-50))/verbruik);
        l7232.setText("" + (7*(232-50))/verbruik);
        l10200.setText("" + (10*(200-50))/verbruik);
        l10232.setText("" + (10*(232-50))/verbruik);
        l12200.setText("" + (12*(200-50))/verbruik);
        l12232.setText("" + (12*(232-50))/verbruik);
        l14200.setText("" + (14*(200-50))/verbruik);
        l14232.setText("" + (14*(232-50))/verbruik);
        l15200.setText("" + (15*(200-50))/verbruik);
        l15232.setText("" + (15*(232-50))/verbruik);
        l20200.setText("" + (20*(200-50))/verbruik);
        l20232.setText("" + (20*(232-50))/verbruik);
        
    }
}
