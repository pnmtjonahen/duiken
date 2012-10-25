package nl.tjonahen.duiken;

import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import nl.tjonahen.duiken.deco.Config;

/**
 *
 * @author Philippe Tjon-A-Hen
 */
class ConfigForm extends Form {
    private CheckBox cb;

    public void init(final Config config, final DecoTableForm mainForm) {

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        cb = new CheckBox("Gebruik Diep Stop");
        cb.setSelected(config.isIncludeDeepStop());
        addComponent(cb);
        addComponent(new Button(new Command("Ok") {

            @Override
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                config.setIncludeDeepStop(cb.isSelected());
                Storage.getInstance().writeObject("includeDeepStop", config.isIncludeDeepStop());
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
    
}
