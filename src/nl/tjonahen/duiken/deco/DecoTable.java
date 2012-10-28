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

import com.codename1.util.MathUtil;

/**
 * The static dciem based NOB deco table.
 *
 * @author Philippe Tjon-A-Hen
 */
public class DecoTable {
    private double hf;
    private DecoTable() {}
    
    private static DecoTable _instance;
    
    public static synchronized DecoTable getInstance() {
        if (_instance == null) {
            _instance = new DecoTable();
            _instance.calculate(1.0);
        }
        return _instance;
    }
    
    private Dive[] dives;

    public Dive[] getDives() {
        return dives;
    }

    public double getHf() {
        return hf;
    }
    
    public void calculate(final double hf) {
        this.hf = hf;
        dives = new Dive[table.length];
        for (int i = 0; i < table.length; i++) {
            final int mdd = table[i][0];
            final int dt;
            final int deco12;
            final int deco9;
            final int deco6;
            final int deco3;

            if (Config.getInstance().isSecondDive() 
                    && !Config.getInstance().isCalculateNullDives().booleanValue() 
                    && isNullDive(table[i][2], table[i][3], table[i][4], table[i][5]) ) {
                dt = nulDive(mdd, hf);
                if (dt > 0) { 
                    deco12 =  0;
                    deco9 =  0;
                    deco6 =  0;
                    deco3 =  0;
                    final ResultSurfaceAirMinutes  total = calculateSurfaceAirMinutes(mdd,
                                dt,
                                deco12,
                                deco9,
                                deco6,
                                deco3);

                    dives[i] = new Dive();
                    dives[i].setMaxmimumDiveDepthdd(mdd);
                    dives[i].setDiveTime(dt);
                    dives[i].setDeco12(deco12);
                    dives[i].setDeco9(deco9);
                    dives[i].setDeco6(deco6);
                    dives[i].setDeco3(deco3);
                    dives[i].setResultSurfaceAirMinutes(total);
                    dives[i].setHg(getHg().charAt(i));
                }
            } else {
                dt = (int) Math.ceil(table[i][1]/hf);
                deco12 =  (int) Math.ceil(table[i][2]*hf);
                deco9 =  (int) Math.ceil(table[i][3]*hf);
                deco6 =  (int) Math.ceil(table[i][4]*hf);
                deco3 =  (int) Math.ceil(table[i][5]*hf);
                final ResultSurfaceAirMinutes  total = calculateSurfaceAirMinutes(mdd,
                            dt,
                            deco12,
                            deco9,
                            deco6,
                            deco3);

                dives[i] = new Dive();
                dives[i].setMaxmimumDiveDepthdd(mdd);
                dives[i].setDiveTime(dt);
                dives[i].setDeco12(deco12);
                dives[i].setDeco9(deco9);
                dives[i].setDeco6(deco6);
                dives[i].setDeco3(deco3);
                dives[i].setResultSurfaceAirMinutes(total);
                dives[i].setHg(getHg().charAt(i));
            }            
        }
    }
    
     private boolean isNullDive(final int deco12, final int deco9, final int deco6, final int deco3) {
         return deco12 == 0 && deco9 == 0 && deco6 == 0 && deco3 == 0;
     }   
    /**
     * Calculates the surface air minutes for a single dive
     * @param maximumDiveDepth maximum dive depth
     * @param diveTime dive time
     * @param deco12 deco stop in minutes at 12 meters
     * @param deco9 deco stop in minutes at 9 meters
     * @param deco6 deco stop in minutes at 6 meters
     * @param deco3 deco stop in minutes at 3 meters
     * @return 
     */
    public ResultSurfaceAirMinutes calculateSurfaceAirMinutes(int maximumDiveDepth, int diveTime, int deco12, int deco9, int deco6, int deco3) {
        ResultSurfaceAirMinutes r = new ResultSurfaceAirMinutes();
        r.setDescend(afdaling(maximumDiveDepth));
        r.setSafetyStop(veiligheidsstop(maximumDiveDepth, diveTime, deco12, deco9, deco6, deco3));

        r = addDecostops(r, deco12, deco9, deco6, deco3);

        if (maximumDiveDepth > 20 && Config.getInstance().isIncludeDeepStop().booleanValue()) {
            r = addDieptestops(r, maximumDiveDepth, deco12, deco9, deco6, deco3);
        }
        float bodemtijd = diveTime - r.getDescend().getTime() - (float)(r.getDeepStops().size() * 2);
        int diepte = maximumDiveDepth;
        for (Stop stop : r.getDeepStops()) {
            float tijd = (diepte - stop.getDepth()) / 10F;
            bodemtijd -= tijd;
            diepte = stop.getDepth();
        }

        r.setBodem(bodem(maximumDiveDepth, bodemtijd));
        r.setAscend(opstijging(maximumDiveDepth));
        return r;

    }

    private ResultSurfaceAirMinutes addDieptestops(final ResultSurfaceAirMinutes r, int mdd, int deco12, int deco9, int deco6, int deco3) {
        final int laatsteStop = bepaalStop(deco12, deco9, deco6, deco3);

        int huidigeDiepte = mdd;

        while ((huidigeDiepte - laatsteStop) > 10) {
            int dieptestop = (huidigeDiepte + laatsteStop) / 2;
            r.addDeepteStop(dieptestop, bodem(dieptestop, 2));
            huidigeDiepte = dieptestop;
        }
        return r;

    }

    private int bepaalStop(final int deco12, final int deco9, final int deco6, final int deco3) {
        if (deco12 != 0) {
            return 12;
        }
        if (deco9 != 0) {
            return 9;
        }
        if (deco6 != 0) {
            return 6;
        }
        if (deco3 != 0) {
            return 3;
        }

        return 6;
    }

    private ResultSurfaceAirMinutes addDecostops(final ResultSurfaceAirMinutes r, final int deco12, final int deco9, final int deco6, final int deco3) {
        if (deco12 != 0) {
            r.addDecoStop(12, bodem(12, deco12));
        }
        if (deco9 != 0) {
            r.addDecoStop(9, bodem(9, deco9));
        }
        if (deco6 != 0) {
            r.addDecoStop(6, bodem(6, deco6));
        }
        if (deco3 != 0) {
            r.addDecoStop(3, bodem(3, deco3));
        }

        return r;
    }

    private SurfaceAirMinutes veiligheidsstop(final int mdd, final int dt, final int deco12, final int deco9, final int deco6, final int deco3) {
        if (mdd < 8) {
            return new SurfaceAirMinutes();
        }
        if (deco12 == 0 && deco9 == 0 && deco6 == 0 && deco3 == 0) {
            return bodem(6, 5);
        }
        return new SurfaceAirMinutes();
    }

    private SurfaceAirMinutes afdaling(final int mdd) {
        return opstijging(mdd);
    }

    private SurfaceAirMinutes opstijging(final int mdd) {
        return new SurfaceAirMinutes(((1.0F + ((float)mdd / 10.0F + 1.0F))) / 2.0F, ((float)mdd / 10.0F), mdd);
    }

    private SurfaceAirMinutes bodem(final int mdd, final float dt) {
        return new SurfaceAirMinutes((((float)mdd / 10.0F) + 1.0F), dt, mdd);
    }


    public String[] getHeader() {
        return header;
    }
    public String getHg() {
        return herhalings;
    }
    
    private static final String[] header = {"MDD", "DT", "12", "9", "6", "3", "OVM"};
    private static final int[][] table = {
        {9, 300, 0, 0, 0, 0},
        {9, 330, 0, 0, 0, 3},
        {9, 360, 0, 0, 0, 5},
        {12, 150, 0, 0, 0, 0},
        {12, 180, 0, 0, 0, 5},
        {15, 75, 0, 0, 0, 0},
        {15, 100, 0, 0, 0, 5},
        {15, 120, 0, 0, 0, 10},
        {15, 125, 0, 0, 0, 13},
        {15, 130, 0, 0, 0, 16},
        {15, 140, 0, 0, 0, 21},
        {18, 50, 0, 0, 0, 0},
        {18, 60, 0, 0, 0, 5},
        {18, 80, 0, 0, 0, 10},
        {18, 90, 0, 0, 0, 16},
        {18, 100, 0, 0, 0, 24},
        {18, 110, 0, 0, 0, 30},
        {18, 120, 0, 0, 0, 36},
        {21, 35, 0, 0, 0, 0},
        {21, 40, 0, 0, 0, 5},
        {21, 50, 0, 0, 0, 10},
        {21, 60, 0, 0, 0, 12},
        {21, 70, 0, 0, 3, 17},
        {21, 80, 0, 0, 4, 25},
        {21, 90, 0, 0, 5, 32},
        {21, 100, 0, 0, 6, 39},
        {24, 25, 0, 0, 0, 0},
        {24, 30, 0, 0, 0, 5},
        {24, 40, 0, 0, 0, 11},
        {24, 50, 0, 0, 4, 11},
        {24, 55, 0, 0, 5, 15},
        {24, 60, 0, 0, 6, 21},
        {24, 65, 0, 0, 7, 25},
        {24, 70, 0, 0, 7, 30},
        {24, 75, 0, 0, 8, 24},
        {24, 80, 0, 0, 9, 37},
        {27, 20, 0, 0, 0, 0},
        {27, 25, 0, 0, 0, 7},
        {27, 30, 0, 0, 2, 9},
        {27, 40, 0, 0, 6, 10},
        {27, 45, 0, 0, 7, 14},
        {27, 50, 0, 0, 8, 20},
        {27, 55, 0, 0, 9, 26},
        {27, 60, 0, 2, 8, 31},
        {30, 15, 0, 0, 0, 0},
        {30, 20, 0, 0, 0, 8},
        {30, 25, 0, 0, 3, 9},
        {30, 30, 0, 0, 5, 10},
        {30, 35, 0, 0, 7, 11},
        {30, 40, 0, 0, 9, 16},
        {30, 45, 0, 3, 8, 23},
        {30, 50, 0, 4, 8, 29},
        {30, 55, 0, 5, 9, 34},
        {33, 12, 0, 0, 0, 0},
        {33, 15, 0, 0, 0, 5},
        {33, 20, 0, 0, 3, 9},
        {33, 25, 0, 0, 6, 10},
        {33, 30, 0, 0, 9, 10},
        {33, 35, 0, 3, 8, 16},
        {33, 40, 0, 5, 8, 24},
        {33, 45, 0, 6, 9, 31},
        {33, 50, 0, 7, 9, 38},
        {33, 55, 0, 8, 10, 44},
        {36, 10, 0, 0, 0, 0},
        {36, 15, 0, 0, 0, 10},
        {36, 20, 0, 0, 5, 10},
        {36, 25, 0, 0, 9, 10},
        {36, 30, 0, 4, 8, 14},
        {36, 35, 0, 6, 8, 24},
        {36, 40, 0, 8, 8, 32},
        {36, 45, 3, 8, 10, 38},
        {36, 50, 4, 7, 10, 40},
        {39, 8, 0, 0, 0, 0},
        {39, 10, 0, 0, 0, 5},
        {39, 15, 0, 0, 4, 8},
        {39, 20, 0, 0, 8, 10},
        {39, 25, 0, 5, 7, 11},
        {39, 30, 0, 7, 8, 22},
        {39, 35, 3, 6, 9, 30},
        {39, 40, 4, 7, 9, 39},
        {39, 45, 6, 7, 10, 47},
        {42, 7, 0, 0, 0, 0},
        {42, 10, 0, 0, 0, 7},
        {42, 15, 0, 0, 6, 9},
        {42, 20, 0, 4, 7, 10},
        {42, 25, 0, 7, 8, 17},
        {42, 30, 4, 6, 8, 28},
        {42, 35, 5, 7, 9, 37},
        {42, 40, 7, 7, 10, 46},
        {45, 7, 0, 0, 0, 0},
        {45, 10, 0, 0, 0, 9},
        {45, 15, 0, 0, 8, 9},
        {45, 20, 0, 6, 7, 11},
        {45, 25, 4, 5, 8, 23},
        {45, 35, 6, 6, 9, 34},
        {48, 6, 0, 0, 0, 0},
        {48, 10, 0, 0, 0, 11},
        {48, 15, 0, 4, 6, 10},
        {48, 20, 0, 8, 8, 14},
        {48, 25, 6, 6, 8, 29},
        {51, 6, 0, 0, 0, 0},
        {51, 10, 0, 0, 5, 8},
        {51, 15, 0, 5, 7, 10},
        {51, 20, 5, 5, 8, 20},
        {54, 5, 0, 0, 0, 0},
        {54, 10, 0, 0, 6, 9},
        {54, 15, 0, 7, 7, 11},
        {54, 20, 6, 6, 8, 25},};
    private static final String herhalings = "MNOJMGIKKLMFGIJKLMEFGHJKMNEFGHIJJKLMDEFHIJKLDEFGHIJKLCDFGHIJKMNCEFGIJKMNBCEGHJKMNBDFGIKLNBDFHJKBDGHKBDGIBDEHJ"; 
    
    public double getHf(int hg, int oi) {
        return tabelb[hg][oi];
    }
    private static final double[][] tabelb = {
        // 29   59   1:29  1:59 2:59 3:59 5:59 8:59 11:59 14:59 18:00
        {  1.4, 1.2, 1.1,  1.1, 1.1, 1.1, 1.1, 1.1, 1.0,  1.0,  1.0 } , //a
        {  1.5, 1.3, 1.2,  1.2, 1.1, 1.1, 1.1, 1.1, 1.1,  1.0,  1.0 } , //b 
        {  1.6, 1.4, 1.3,  1.2, 1.2, 1.2, 1.1, 1.1, 1.1,  1.0,  1.0 } , //c 
        {  1.8, 1.5, 1.4,  1.3, 1.3, 1.2, 1.2, 1.1, 1.1,  1.0,  1.0 } , //d 
        {  1.9, 1.6, 1.5,  1.4, 1.3, 1.3, 1.2, 1.2, 1.1,  1.1,  1.0 } , //e 
        {  2.0, 1.7, 1.6,  1.5, 1.4, 1.3, 1.3, 1.2, 1.1,  1.1,  1.0 } , //f 
        {  0,   1.9, 1.7,  1.6, 1.5, 1.4, 1.3, 1.2, 1.1,  1.1,  1.0 } , //g 
        {  0,     0, 1.9,  1.7, 1.6, 1.5, 1.4, 1.3, 1.1,  1.1,  1.1 } , //h 
        {  0,     0, 2.0,  1.8, 1.7, 1.5, 1.4, 1.3, 1.1,  1.1,  1.1 } , //i 
        {  0,     0,   0,  1.9, 1.8, 1.6, 1.5, 1.3, 1.2,  1.1,  1.1 } , //j 
        {  0,     0,   0,  2.0, 1.9, 1.7, 1.5, 1.3, 1.2,  1.1,  1.1 } , //k 
        {  0,     0,   0,    0, 2.0, 1.7, 1.6, 1.4, 1.2,  1.1,  1.1 } , //l 
        {  0,     0,   0,    0,   0, 1.8, 1.6, 1.4, 1.2,  1.1,  1.1 } , //m 
        {  0,     0,   0,    0,   0, 1.9, 1.7, 1.4, 1.2,  1.1,  1.1 } , //n 
        {  0,     0,   0,    0,   0, 2.0, 1.7, 1.4, 1.2,  1.1,  1.1 }  //o 
    };
    private int nulDive(final int pMdd, final double pHf) {
        for (int i = 0; i< mdds.length; i++) {
            if (mdds[i] == pMdd) {
                for (int j = 0; j < hfs.length; j++) {
                    if (hfs[j] == pHf) {
                        return tabelc[i][j];
                    }
                }
            }
        }
        return -1;
    }
    private static int[] mdds = {9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45};
    private static double[] hfs = { 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0 };
    private static final int[][] tabelc = {
       // 1.1  1.2  1.3  1.4  1.5  1.6  1.7  1.8  1.9  2.0
        { 272, 250, 230, 214, 200, 187, 176, 166, 157, 150}, // 9
        { 136, 125, 115, 107, 100,  93,  88,  83,  78,  75}, // 12
        {  60,  55,  50,  45,  41,  38,  36,  34,  32,  31}, // 15
        {  40,  35,  31,  29,  27,  26,  24,  23,  22,  21}, // 18
        {  30,  25,  21,  19,  18,  17,  16,  15,  14,  13}, // 21
        {  20,  18,  16,  15,  14,  13,  12,  12,  11,  11}, // 24
        {  16,  14,  12,  11,  11,  10,   9,   9,   8,   8}, // 27
        {  13,  11,  10,   9,   9,   8,   8,   7,   7,   7}, // 30
        {  10,   9,   8,   8,   7,   7,   6,   6,   6,   6}, // 33
        {   8,   7,   7,   6,   6,   6,   5,   5,   5,   5}, // 36
        {   7,   6,   6,   5,   5,   5,   4,   4,   4,   4}, // 39
        {   6,   5,   5,   5,   4,   4,   4,   3,   4,   3}, // 42
        {   5,   5,   4,   4,   4,   3,   3,   3,   3,   3}, // 45
    };
}
