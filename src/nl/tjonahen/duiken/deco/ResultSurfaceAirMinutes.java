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

import java.util.ArrayList;
import java.util.List;

/**
 * Result of one dive Surface air minute calculation.
 * 
 * @author Philippe Tjon-A-Hen
 */
public class ResultSurfaceAirMinutes {

    private SurfaceAirMinutes descend;
    private SurfaceAirMinutes ascend;
    private SurfaceAirMinutes bottom;
    private SurfaceAirMinutes safetyStop;
    private List<Stop> deepStops = new ArrayList<Stop>();
    private List<Stop> decoStops = new ArrayList<Stop>();

    /**
     * 
     * @return  the total of surface air minutes for this calculation
     */
    public float total() {
        float tmp = descend.calc() + bottom.calc() + ascend.calc() + safetyStop.calc();

        for (final Stop stopdeep : deepStops) {
            tmp += stopdeep.getSurfaceAirMinutes().calc();
        }
        for (final Stop stopdeco : decoStops) {
            tmp += stopdeco.getSurfaceAirMinutes().calc();
        }
        return tmp;
    }

    /**
     * 
     * @return the total dive time for this calculation
     */
    public float totalTime() {
        float tmp = descend.getTime() + bottom.getTime() + ascend.getTime() + safetyStop.getTime();

        for (final Stop stopdeep : deepStops) {
            tmp += stopdeep.getSurfaceAirMinutes().getTime();
        }
        for (final Stop stopdeco : decoStops) {
            tmp += stopdeco.getSurfaceAirMinutes().getTime();
        }
        return tmp;
    }

    /**
     * Adds a deep stop to the calculation
     * @param maximumDiveDepth
     * @param surfaceAirMinutes 
     */
    public void addDeepteStop(final int maximumDiveDepth, final SurfaceAirMinutes surfaceAirMinutes) {
        deepStops.add(new Stop(maximumDiveDepth, surfaceAirMinutes));
    }

    /**
     * Adds a deco stop to the calculation
     * @param maximumDiveDepth
     * @param surfaceAirMinutes 
     */
    public void addDecoStop(final int maximumDiveDepth, final SurfaceAirMinutes surfaceAirMinutes) {
        decoStops.add(new Stop(maximumDiveDepth, surfaceAirMinutes));
    }

    /**
     * 
     * @return the surface air minutes of the descend 
     */
    public SurfaceAirMinutes getDescend() {
        return descend;
    }

    /**
     * 
     * @return the surface air minutes of the ascend
     */
    public SurfaceAirMinutes getAscend() {
        return ascend;
    }

    /**
     * 
     * @return the surface air minutes of the bottom
     * 
     */
    public SurfaceAirMinutes getBottom() {
        return bottom;
    }

    /**
     * 
     * @param desend the desend surface air minutes
     */
    public void setDescend(final SurfaceAirMinutes desend) {
        this.descend = desend;
    }

    /**
     * 
     * @param ascend the ascend surface air minutes
     */
    public void setAscend(SurfaceAirMinutes ascend) {
        this.ascend = ascend;
    }

    public void setBodem(SurfaceAirMinutes bodem) {
        this.bottom = bodem;
    }

    /**
     * 
     * @return the surface air minutes of the safety stop.
     */
    public SurfaceAirMinutes getSafetyStop() {
        return safetyStop;
    }

    /**
     * 
     * @return the list of deep stop
     */
    public List<Stop> getDeepStops() {
        return deepStops;
    }

    /**
     * 
     * @return the list of deco stops 
     */
    public List<Stop> getDecoStops() {
        return decoStops;
    }

    /**
     * 
     * @param safetyStop the surface air minutes of the safety stop
     */
    public void setSafetyStop(SurfaceAirMinutes safetyStop) {
        this.safetyStop = safetyStop;
    }
    
}
