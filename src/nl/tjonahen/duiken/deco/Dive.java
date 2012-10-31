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
 * A single dive, with if calculated the result of the SurfaceAirMinute calculation
 *
 * @author Philippe Tjon-A-Hen
 */
public class Dive {

    private int maximumDiveDepth;
    private int diveTime;
    private int deco12;
    private int deco9;
    private int deco6;
    private int deco3;
    private ResultSurfaceAirMinutes resultSurfaceAirMinutes;
    private char hg;

    public int getMaximumDiveDepth() {
        return maximumDiveDepth;
    }

    public void setMaxmimumDiveDepthdd(final int maximumDiveDepth) {
        this.maximumDiveDepth = maximumDiveDepth;
    }

    public int getDiveTime() {
        return diveTime;
    }

    public void setDiveTime(final int diveTime) {
        this.diveTime = diveTime;
    }

    public int getDeco12() {
        return deco12;
    }

    public void setDeco12(final int deco12) {
        this.deco12 = deco12;
    }

    public int getDeco9() {
        return deco9;
    }

    public void setDeco9(final int deco9) {
        this.deco9 = deco9;
    }

    public int getDeco6() {
        return deco6;
    }

    public void setDeco6(final int deco6) {
        this.deco6 = deco6;
    }

    public int getDeco3() {
        return deco3;
    }

    public void setDeco3(final int deco3) {
        this.deco3 = deco3;
    }

    public char getHg() {
        return hg;
    }

    public void setHg(char hg) {
        this.hg = hg;
    }

    public ResultSurfaceAirMinutes getResultSurfaceAirMinutes() {
        return resultSurfaceAirMinutes;
    }

    public void setResultSurfaceAirMinutes(final ResultSurfaceAirMinutes resultSurfaceAirMinutes) {
        this.resultSurfaceAirMinutes = resultSurfaceAirMinutes;
    }

    /**
     * 
     * @param value -
     * @return the value in a displayable way (0 zero is not displayed)
     */
    public static String getDisplayValue(final int value) {
        if (value == 0) {
            return "";
        } else {
//            final String v = "   " + value;
            return "" + value;
        }
    }

    /**
     * 
     * @param value -
     * @return the value in a displayable way (2 digits float)
     */
    public static String getDisplayValue(final float value) {
        return "" + Math.floor(value*100f + 0.5f) / 100f;
    }
    /**
     * 
     * @param value -
     * @return the value in a displayable way (0 zero is not displayed)
     */
    public static String getDisplayValue(final char value) {
        return "" + value;
    }
}
