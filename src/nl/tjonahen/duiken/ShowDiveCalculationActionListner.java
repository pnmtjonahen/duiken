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
import nl.tjonahen.duiken.deco.Config;
import nl.tjonahen.duiken.deco.DecoTable;
import nl.tjonahen.duiken.deco.Dive;
import nl.tjonahen.duiken.deco.Stop;

/**
 * ActionListner to handle the selection of a dive to show the calculation.
 * 
 * @author Philippe Tjon-A-Hen
 */
public class ShowDiveCalculationActionListner implements ActionListener {
    private final DecoTableForm previousForm;
    private final AirConsumptionForm airConsumptionForm;
    
    public ShowDiveCalculationActionListner(final AirConsumptionForm airConsumptionForm, final DecoTableForm previousForm) {
        this.previousForm = previousForm;
        this.airConsumptionForm = airConsumptionForm;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        evt.consume();
        String name = "Duik berekening";
        if (Config.getInstance().isSecondDive()) {
            name += " - Herhaling (hf=" + DecoTable.getInstance().getHf() + ")";
        }
        final Form toonDuikBerekeningForm = new Form(name);
        toonDuikBerekeningForm.setTensileDragEnabled(false);
        final List list = (List) evt.getSource();
        final int selectedIndex = list.getModel().getSelectedIndex();
        final Dive duik = (Dive) list.getModel().getItemAt(selectedIndex);
        
        toonDuikBerekeningForm.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        final Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        addHeader(cnt, duik);
        addOvmBerekeningContainer(cnt, duik);
        addTotals(cnt, duik);

        cnt.setScrollableY(true);
        cnt.setTensileDragEnabled(false);
        toonDuikBerekeningForm.addComponent(cnt);
        toonDuikBerekeningForm.setBackCommand(new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                previousForm.showBack();
            }
        });
        toonDuikBerekeningForm.addCommand(new AirConsumptionCommand(airConsumptionForm, toonDuikBerekeningForm));
        if (Config.getInstance().isSecondDive()) {
            toonDuikBerekeningForm.addCommand(new FirstDiveCommand(previousForm));
        } else {
            toonDuikBerekeningForm.addCommand(new SurfaceTimeCommand(toonDuikBerekeningForm, previousForm, duik.getHg()));
        }

        toonDuikBerekeningForm.show();
    }

    private void addOvmBerekeningContainer(final Container dialog, final Dive dive) {
        int rows = 4;
        if (dive.getResultSurfaceAirMinutes().getSafetyStop().isValid()) {
            rows++;
        }
        int diep = dive.getResultSurfaceAirMinutes().getDeepStops().size();
        if (diep > 0) {
            rows += diep;
            rows++;
        }

        int deco = dive.getResultSurfaceAirMinutes().getDecoStops().size();
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
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getDescend().getAveragePressure())));
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getDescend().getTime())));
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getDescend().calc())));

        cnt.addComponent(new Label("Bodem"));
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getBottom().getAveragePressure())));
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getBottom().getTime())));
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getBottom().calc())));

        cnt.addComponent(new Label("Opstijging"));
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getAscend().getAveragePressure())));
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getAscend().getTime())));
        cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getAscend().calc())));
        if (dive.getResultSurfaceAirMinutes().getSafetyStop().isValid()) {
            cnt.addComponent(new Label("VStop"));
            cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getSafetyStop().getAveragePressure())));
            cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getSafetyStop().getTime())));
            cnt.addComponent(new Label(Dive.getDisplayValue(dive.getResultSurfaceAirMinutes().getSafetyStop().calc())));
        }
        if (diep > 0) {
            cnt.addComponent(new Label("DiepS"));
            cnt.addComponent(new Label(""));
            cnt.addComponent(new Label(""));
            cnt.addComponent(new Label(""));

            for (Stop stop : dive.getResultSurfaceAirMinutes().getDeepStops()) {
                cnt.addComponent(new Label(Dive.getDisplayValue(stop.getSurfaceAirMinutes().getDepth())));
                cnt.addComponent(new Label(Dive.getDisplayValue(stop.getSurfaceAirMinutes().getAveragePressure())));
                cnt.addComponent(new Label(Dive.getDisplayValue(stop.getSurfaceAirMinutes().getTime())));
                cnt.addComponent(new Label(Dive.getDisplayValue(stop.getSurfaceAirMinutes().calc())));

            }

        }
        if (deco > 0) {
            cnt.addComponent(new Label("DecoS"));
            cnt.addComponent(new Label(""));
            cnt.addComponent(new Label(""));
            cnt.addComponent(new Label(""));

            for (Stop stop : dive.getResultSurfaceAirMinutes().getDecoStops()) {
                cnt.addComponent(new Label(Dive.getDisplayValue(stop.getSurfaceAirMinutes().getDepth())));
                cnt.addComponent(new Label(Dive.getDisplayValue(stop.getSurfaceAirMinutes().getAveragePressure())));
                cnt.addComponent(new Label(Dive.getDisplayValue(stop.getSurfaceAirMinutes().getTime())));
                cnt.addComponent(new Label(Dive.getDisplayValue(stop.getSurfaceAirMinutes().calc())));

            }

        }

        cnt.addComponent(new Label(""));
        cnt.addComponent(new Label("Totaal"));
        cnt.addComponent(new Label("="));
        cnt.addComponent(new Label(dive.getDisplayValue(dive.getResultSurfaceAirMinutes().total())));
//        dialog.getStyle().setBgTransparency(255);
        dialog.addComponent(cnt);
    }
    
    private void addTotals(final Container dialog, final Dive duik) {
        String calc = Dive.getDisplayValue(duik.getDiveTime()) 
                + " - " + Dive.getDisplayValue(duik.getResultSurfaceAirMinutes().getDescend().getTime());
        int diepte = duik.getMaximumDiveDepth();
        for (Stop stop : duik.getResultSurfaceAirMinutes().getDeepStops()) {
            float tijd = (diepte - stop.getDepth()) / 10F;
            calc += " - " + Dive.getDisplayValue(tijd) + " - 2"; 
            diepte = stop.getDepth();
        }
        dialog.addComponent(new Label("bt brk. = " + calc + "  "));
        Container cnt = new Container(new GridLayout(3, 3));
        
        cnt.addComponent(new Label("bt"));
        cnt.addComponent(new Label("="));
        cnt.addComponent(new Label(Dive.getDisplayValue(duik.getResultSurfaceAirMinutes().getBottom().getTime())));

        cnt.addComponent(new Label("totale dt"));
        cnt.addComponent(new Label("="));
        cnt.addComponent(new Label(Dive.getDisplayValue(duik.getResultSurfaceAirMinutes().totalTime())));
        cnt.addComponent(new Label(""));
        cnt.addComponent(new Label(""));
        cnt.addComponent(new Label(""));
        dialog.addComponent(cnt);
    }

    private void addHeader(final Container cnt, final Dive dive) {
        final Container header = new Container(new GridLayout(2, 7));
        
        header.addComponent(new Label("mdd"));
        header.addComponent(new Label("dt"));
        header.addComponent(new Label("12"));
        header.addComponent(new Label("9"));
        header.addComponent(new Label("6"));
        header.addComponent(new Label("3"));
        header.addComponent(new Label("hg"));
        

        header.addComponent(new Label(Dive.getDisplayValue(dive.getMaximumDiveDepth())));
        header.addComponent(new Label(Dive.getDisplayValue(dive.getDiveTime())));
        header.addComponent(new Label(Dive.getDisplayValue(dive.getDeco12())));
        header.addComponent(new Label(Dive.getDisplayValue(dive.getDeco9())));
        header.addComponent(new Label(Dive.getDisplayValue(dive.getDeco6())));
        header.addComponent(new Label(Dive.getDisplayValue(dive.getDeco3())));
        header.addComponent(new Label(Dive.getDisplayValue(dive.getHg())));
                
        
        cnt.addComponent(header);
    }
}
