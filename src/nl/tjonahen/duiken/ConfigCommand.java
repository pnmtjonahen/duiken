package nl.tjonahen.duiken;

import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
class ConfigCommand extends Command {

    private final ConfigForm configForm;
    public ConfigCommand(final ConfigForm configForm) {
        super("Instellingen");
        this.configForm = configForm;
    }

    @Override
    public void actionPerformed(final ActionEvent evt) {
        evt.consume();
        
        
        configForm.show();
        
    }
    
    
    
}
