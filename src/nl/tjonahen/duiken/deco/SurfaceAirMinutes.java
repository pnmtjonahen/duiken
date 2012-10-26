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

/**
 *
 * Surface Air Minutes
 * @author Philippe Tjon-A-Hen
 */
public class SurfaceAirMinutes {

    private final boolean valid;
    private final int depth;
    private final float pAverage;
    private final float time;

    /**
     * 
     * @param averagePressure -
     * @param time -
     * @param depth -
     */
    public SurfaceAirMinutes(final float averagePressure, final float time, final int depth) {
        this.pAverage = averagePressure;
        this.time = time;
        this.depth = depth;
        this.valid = true;
    }
    
    /**
     * 
     */
    public SurfaceAirMinutes() {
        this.pAverage = 0;
        this.time = 0;
        this.depth = 0;
        this.valid = false;
    }

    public float calc() {
        return pAverage * time;
    }

    public int getDepth() {
        return depth;
    }

    public float getAveragePressure() {
        return pAverage;
    }

    public float getTime() {
        return time;
    }

    public boolean isValid() {
        return valid;
    }
    
    
}
