package nl.tjonahen.duiken;

import nl.tjonahen.duiken.deco.DecoTable;
import com.codename1.ui.Command;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;

/**
 * Duiken (Dutch for diving) main entry point of the application.
 * 
 * @author Philippe Tjon-A-Hen
 */
public class Duiken {

    private Form current;

    public void init(Object context) {
        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        final Form mainForm = new Form("NOB - Duiktabel");
        final AirConsumptionForm airConsumptionDialog = new AirConsumptionForm();

        Command exitCommand = new Command("Exit") {
            @Override
            public void actionPerformed(ActionEvent ev) {
                Display.getInstance().exitApplication();
            }
        };
        mainForm.setBackCommand(exitCommand);

        mainForm.addCommand(new AirConsumptionCommand(airConsumptionDialog, mainForm));
        
        final DecoTable dt = new DecoTable();

        mainForm.setScrollable(false);
        List l = new List(new DiveListModel(dt));
        l.setRenderer(new DiveListCellRenderer());
        l.setFixedSelection(List.FIXED_NONE);
        l.getStyle().setBgTransparency(0);
        l.addActionListener(new ShowDiveCalculationActionListner(airConsumptionDialog, mainForm));

        mainForm.setLayout(new BorderLayout());
        mainForm.addComponent(BorderLayout.CENTER, l);


        mainForm.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }
}
