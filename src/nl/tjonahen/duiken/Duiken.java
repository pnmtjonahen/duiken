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

import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;
import java.io.IOException;

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
        final DecoTableForm mainForm = new DecoTableForm();
        mainForm.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }
    
    private void showSplashAnimation() {
        Form splash = new Form();
        splash.setUIID("Splash");
        BorderLayout border = new BorderLayout();
        border.setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);
        splash.setLayout(border);
        splash.setScrollable(false);
        Label title = new Label("Duiken");
        title.setUIID("Splash");
        Label subtitle = new Label("By Philippe");
        subtitle.setUIID("Splash");
        splash.addComponent(BorderLayout.NORTH, title);
        splash.addComponent(BorderLayout.SOUTH, subtitle);
        final Image image = res.getImage("dipnoi1.png");
        
        Label beaker = new Label(image.scaledHeight(320));
        beaker.getStyle().setBgTransparency(0);
        splash.addComponent(BorderLayout.CENTER, beaker);
        
        
        splash.show();
        new UITimer(new Runnable() {
            public void run() {
                showMainUI();
            }
        }).schedule(2500, false, splash);
    }    
}
