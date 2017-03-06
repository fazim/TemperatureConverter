package com.example.fauzul.temperatureconverter;

/**
 * Created by Fauzul on 12/8/2016.
 */

public class Converter {
    // converts f to c
    public static float convertFtoC(float f) {
        return ((f - 32) * 5 / 9);
    }

    // converts c  to f
    public static float convertCtoF(float c) {
        return ((c * 9) / 5) + 32;
    }

    //converts f to k
    public static float convertFtoK(float f) {
        return (float) (convertFtoC(f)+273.15);
    }

   //converts c to k
    public static float convertCtoK(float c) {
        return (float)(c+273.15);
    }

    //converts k to c
    public static float convertKtoC(float k) {
        return (float)(k-273.15);
    }

    // converts k to f
    public static float convertKtoF(float k) {
        return convertCtoF(convertKtoC(k));
    }

    // converts r to f
    public static float convertRtoF(float r) {
        return (float)(r-459.67);
    }

    // converts f to r
    public static float convertFtoR(float f) {
        return (float)(f+459.67);
    }

    // converts r to c
    public static float convertRtoC(float r) {
        return convertKtoC(convertRtoK(r));
    }

    // converts c to r
    public static float convertCtoR(float c) {
        return convertKtoR(convertCtoK(c));
    }

    // converts r to k
    public static float convertRtoK(float r) {
        float k = (float) 5/9;
        k = k * r;
        return k;
    }

    // converts k to r
    public static float convertKtoR(float k) {
        float r = (float) 9/5;
        r = r * k;
        return r;
    }


}