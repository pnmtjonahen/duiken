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
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;

/**
 * Command to handle the AirConsumption command.
 * 
 * @author Phhilippe Tjon-A-Hen
 */
public class AirConsumptionCommand extends Command {
    private final Form main;
    private final AirConsumptionForm verbruik;
   
    /**
     * 
     * @param airConsumptionForm the form to show when the user executes this command
     * @param previousForm the form to go to when back is selected
     */
    public AirConsumptionCommand(final AirConsumptionForm airConsumptionForm, final Form previousForm) {
        super("Verbruik");
        this.main = previousForm;
        this.verbruik = airConsumptionForm;
    }

    /**
     * {@inheritDoc } 
     */
    @Override
    public void actionPerformed(ActionEvent ev) {
        verbruik.setBackForm(main);
        verbruik.updateConfig();
        verbruik.calculate();
        verbruik.show();
    }
    
    
}
