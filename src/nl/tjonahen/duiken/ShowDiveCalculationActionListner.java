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
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import nl.tjonahen.duiken.deco.Dive;
import nl.tjonahen.duiken.deco.Stop;

/**
 * ActionListner to handle the selection of a dive to show the calculation.
 * 
 * @author Philippe Tjon-A-Hen
 */
public class ShowDiveCalculationActionListner implements ActionListener {
    private Form previousForm;
    private AirConsumptionForm airConsumptionForm;
    
    public ShowDiveCalculationActionListner(final AirConsumptionForm airConsumptionForm, final Form previousForm) {
        this.previousForm = previousForm;
        this.airConsumptionForm = airConsumptionForm;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        evt.consume();
        final Form toonDuikBerekeningForm = new Form("Duik berekening");
        final List list = (List) evt.getSource();
        final int selectedIndex = list.getModel().getSelectedIndex();
        final Dive duik = (Dive) list.getModel().getItemAt(selectedIndex);
        
        toonDuikBerekeningForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        final Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        addHeader(cnt, duik);
        addOvmBerekeningContainer(cnt, duik);
        addTotals(cnt, duik);

        cnt.setScrollableY(true);
        toonDuikBerekeningForm.addComponent(cnt);
        toonDuikBerekeningForm.setBackCommand(new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                previousForm.showBack();
            }
        });
        toonDuikBerekeningForm.addCommand(new AirConsumptionCommand(airConsumptionForm, toonDuikBerekeningForm));

        toonDuikBerekeningForm.show();
    }

    private void addOvmBerekeningContainer(final Container dialog, final Dive duik) {
        int rows = 4;
        if (duik.getResultSurfaceAirMinutes().getSafetyStop().isValid()) {
            rows++;
        }
        int diep = duik.getResultSurfaceAirMinutes().getDeepStops().size();
        if (diep > 0) {
            rows += diep;
            rows++;
        }

        int deco = duik.getResultSurfaceAirMinutes().getDecoStops().size();
        if (deco > 0) {
            rows += deco;
            rows++;
        }

        final Container cnt = new Container(new GridLayout(rows, 4));
//        cnt.getStyle().setBgTransparency(255);
        cnt.addComponent(new Label(""));
        cnt.addComponent(new Label("pGem *"));
        cnt.addComponent(new Label("tijd ="));
        cnt.addComponent(new Label("ovm"));

        cnt.addComponent(new Label("Afdaling"));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getDescend().getAveragePressure())));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getDescend().getTime())));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getDescend().calc())));

        cnt.addComponent(new Label("Bodem"));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getBottom().getAveragePressure())));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getBottom().getTime())));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getBottom().calc())));

        cnt.addComponent(new Label("Opstijging"));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getAscend().getAveragePressure())));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getAscend().getTime())));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getAscend().calc())));
        if (duik.getResultSurfaceAirMinutes().getSafetyStop().isValid()) {
            cnt.addComponent(new Label("VStop"));
            cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getSafetyStop().getAveragePressure())));
            cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getSafetyStop().getTime())));
            cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getSafetyStop().calc())));
        }
        if (diep > 0) {
            cnt.addComponent(new Label("DiepS"));
            cnt.addComponent(new Label(""));
            cnt.addComponent(new Label(""));
            cnt.addComponent(new Label(""));

            for (Stop stop : duik.getResultSurfaceAirMinutes().getDeepStops()) {
                cnt.addComponent(new Label(duik.getDisplayValue(stop.getSurfaceAirMinutes().getDepth())));
                cnt.addComponent(new Label(duik.getDisplayValue(stop.getSurfaceAirMinutes().getAveragePressure())));
                cnt.addComponent(new Label(duik.getDisplayValue(stop.getSurfaceAirMinutes().getTime())));
                cnt.addComponent(new Label(duik.getDisplayValue(stop.getSurfaceAirMinutes().calc())));

            }

        }
        if (deco > 0) {
            cnt.addComponent(new Label("DecoS"));
            cnt.addComponent(new Label(""));
            cnt.addComponent(new Label(""));
            cnt.addComponent(new Label(""));

            for (Stop stop : duik.getResultSurfaceAirMinutes().getDecoStops()) {
                cnt.addComponent(new Label(duik.getDisplayValue(stop.getSurfaceAirMinutes().getDepth())));
                cnt.addComponent(new Label(duik.getDisplayValue(stop.getSurfaceAirMinutes().getAveragePressure())));
                cnt.addComponent(new Label(duik.getDisplayValue(stop.getSurfaceAirMinutes().getTime())));
                cnt.addComponent(new Label(duik.getDisplayValue(stop.getSurfaceAirMinutes().calc())));

            }

        }

        cnt.addComponent(new Label(""));
        cnt.addComponent(new Label("Totaal"));
        cnt.addComponent(new Label("="));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().total())));
//        dialog.getStyle().setBgTransparency(255);
        dialog.addComponent(cnt);
    }
    
    private void addTotals(final Container dialog, final Dive duik) {
        String calc = duik.getDisplayValue(duik.getDiveTime()) 
                + " - " + duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getDescend().getTime());
        int diepte = duik.getMaximumDiveDepth();
        for (Stop stop : duik.getResultSurfaceAirMinutes().getDeepStops()) {
            float tijd = (diepte - stop.getDepth()) / 10F;
            calc += " - " + duik.getDisplayValue(tijd) + " - 2"; 
            diepte = stop.getDepth();
        }
        dialog.addComponent(new Label("bt brk. = " + calc + "  "));
        Container cnt = new Container(new GridLayout(3, 3));
        
        cnt.addComponent(new Label("bt"));
        cnt.addComponent(new Label("="));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().getBottom().getTime())));

        cnt.addComponent(new Label("totale dt"));
        cnt.addComponent(new Label("="));
        cnt.addComponent(new Label(duik.getDisplayValue(duik.getResultSurfaceAirMinutes().totalTime())));
        cnt.addComponent(new Label(""));
        cnt.addComponent(new Label(""));
        cnt.addComponent(new Label(""));
        dialog.addComponent(cnt);
    }

    private void addHeader(final Container cnt, final Dive duik) {
        final Container header = new Container(new GridLayout(2, 7));
        
        header.addComponent(new Label("mdd"));
        header.addComponent(new Label("dt"));
        header.addComponent(new Label("12"));
        header.addComponent(new Label("9"));
        header.addComponent(new Label("6"));
        header.addComponent(new Label("3"));
        header.addComponent(new Label("hg"));
        

        header.addComponent(new Label(duik.getDisplayValue(duik.getMaximumDiveDepth())));
        header.addComponent(new Label(duik.getDisplayValue(duik.getDiveTime())));
        header.addComponent(new Label(duik.getDisplayValue(duik.getDeco12())));
        header.addComponent(new Label(duik.getDisplayValue(duik.getDeco9())));
        header.addComponent(new Label(duik.getDisplayValue(duik.getDeco6())));
        header.addComponent(new Label(duik.getDisplayValue(duik.getDeco3())));
        header.addComponent(new Label(duik.getDisplayValue(duik.getHg())));
                
        
        cnt.addComponent(header);
    }
}
