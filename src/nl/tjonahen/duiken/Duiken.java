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
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;
import java.io.IOException;
import nl.tjonahen.duiken.deco.Config;

/**
 * Duiken (Dutch for diving) main entry point of the application.
 * 
 * @author Philippe Tjon-A-Hen
 */
public class Duiken {

    private Form current;
    private Resources res;

    public void init(Object context) {
        try {
            res = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(res.getTheme(res.getThemeResourceNames()[0]));
            if (Display.getInstance().canForceOrientation()) {
                Display.getInstance().lockOrientation(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        showSplashAnimation();
    }
    private void  showMainUI() {
        if (current != null) {
            current.show();
            return;
        }
        setupConfig();
        final DecoTableForm mainForm = new DecoTableForm();
        mainForm.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }
    private void setupConfig() {
        final Storage storage = Storage.getInstance();

        Config config = Config.getInstance();
        config.setIncludeDeepStop(Boolean.FALSE);
        config.setPersonalAir(new Integer(16));
        config.setCalculateNullDives(Boolean.FALSE);
        
        Object object = storage.readObject(Config.INCLUDE_DEEP_STOP);
        if (object == null) {
            storage.writeObject(Config.INCLUDE_DEEP_STOP, config.isIncludeDeepStop());
        } else {
            config.setIncludeDeepStop((Boolean) object);
            
        }
        object = storage.readObject(Config.PERSONAL_AIR);
        if (object == null) {
            storage.writeObject(Config.PERSONAL_AIR, config.getPersonalAir());
        } else {
            config.setPersonalAir((Integer) object);
        }
        
        object = storage.readObject(Config.CALCULATE_NULL_DIVE);
        if (object == null) {
            storage.writeObject(Config.CALCULATE_NULL_DIVE, config.isCalculateNullDives());
        } else {
            config.setCalculateNullDives((Boolean)object);
        }
        storage.flushStorageCache();
    }
    
    
    private void showSplashAnimation() {
        Form splash = new Form();
        BorderLayout border = new BorderLayout();
//        border.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        splash.setLayout(border);
        splash.setScrollable(false);
        Label title = new Label("Duiken");
        Label subtitle = new Label("Door Philippe");
        splash.addComponent(BorderLayout.NORTH, title);
        splash.addComponent(BorderLayout.SOUTH, subtitle);
        final Image image = res.getImage("dipnoi.png");
        
        Label beaker = new Label(image.scaledWidth(Display.getInstance().getDisplayWidth() - 10));
        final Container cnt = new Container(new BorderLayout());
        
        final Container plan = new Container(new GridLayout(1, 3));
        
        plan.addComponent(new Label(""));
        plan.addComponent(new Label("Plan je duik"));
        plan.addComponent(new Label(""));
        cnt.addComponent(BorderLayout.NORTH, plan);

        cnt.addComponent(BorderLayout.CENTER, beaker);
        
        final Container duik = new Container(new GridLayout(1, 3));
        duik.addComponent(new Label(""));
        duik.addComponent(new Label("Duik je plan"));
        duik.addComponent(new Label(""));
        cnt.addComponent(BorderLayout.SOUTH, duik);

        splash.addComponent(BorderLayout.CENTER, cnt);
        
        
        splash.show();
        new UITimer(new Runnable() {
            public void run() {
                showMainUI();
            }
        }).schedule(2500, false, splash);
    }    
}
