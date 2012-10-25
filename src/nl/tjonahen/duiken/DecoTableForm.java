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

    private final Config config;
    private final AirConsumptionForm airConsumptionDialog;
    private List current;

    public DecoTableForm() {
        super("NOB - Duiktabel");
        airConsumptionDialog = new AirConsumptionForm();
        final Storage storage = Storage.getInstance();
        final Object object = storage.readObject("includeDeepStop");
        config = new Config();
        config.setIncludeDeepStop(false);
        if (object == null) {
            config.setIncludeDeepStop(false);
            storage.writeObject("includeDeepStop", config.isIncludeDeepStop());
            storage.flushStorageCache();
        } else {
            config.setIncludeDeepStop((Boolean) object);
        }
        final ConfigForm configForm = new ConfigForm();
        configForm.init(config, this);

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
        final DecoTable dt = new DecoTable(config);
        final List l = new List(new DiveListModel(dt));
        l.setRenderer(new DiveListCellRenderer());
        l.setFixedSelection(List.FIXED_NONE);
        l.getStyle().setBgTransparency(0);
        l.addActionListener(new ShowDiveCalculationActionListner(airConsumptionDialog, this));
        return l;
    }
}
