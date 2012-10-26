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
package nl.tjonahen.duiken.deco;

import java.io.Serializable;

/**
 *
 * Configuration holder
 */
public class Config implements Serializable {
    private boolean includeDeepStop;
    
    public Config() {
    }

    public boolean isIncludeDeepStop() {
        return includeDeepStop;
    }

    public void setIncludeDeepStop(final boolean includeDeepStop) {
        this.includeDeepStop = includeDeepStop;
    }
    
    
}
