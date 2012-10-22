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
    private Form main;
    private AirConsumptionForm verbruik;
   
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
        verbruik.show();
    }
    
    
}
