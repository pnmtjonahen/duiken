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
    
    
    public static final String INCLUDE_DEEP_STOP = "includeDeepStop";
    public static final String PERSONAL_AIR = "personalAir";
    public static final String CALCULATE_NULL_DIVE = "calculateNullDive";
    private boolean secondDive = false;
    
    private Boolean includeDeepStop;
    private Integer personalAir;
    private Boolean calculateNullDives;
    
    private static Config _instance = null;
    public static synchronized Config getInstance() {
        if (_instance == null) {
           _instance = new Config(); 
        }
        return _instance;
        
    }
    private Config() {
    }

    public boolean isSecondDive() {
        return secondDive;
    }

    public void setSecondDive(boolean secondDive) {
        this.secondDive = secondDive;
    }

    
    public Boolean isIncludeDeepStop() {
        return includeDeepStop;
    }

    public void setIncludeDeepStop(final Boolean includeDeepStop) {
        this.includeDeepStop = includeDeepStop;
    }

    public Integer getPersonalAir() {
        return personalAir;
    }

    public void setPersonalAir(Integer personalAir) {
        this.personalAir = personalAir;
    }

    public Boolean isCalculateNullDives() {
        return calculateNullDives;
    }

    public void setCalculateNullDives(final Boolean calculateNullDives) {
        this.calculateNullDives = calculateNullDives;
    }
    
    
}
