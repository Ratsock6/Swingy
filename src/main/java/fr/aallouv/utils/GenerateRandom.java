package fr.aallouv.utils;

import fr.aallouv.App;

import java.util.Random;

public class GenerateRandom {


    public static boolean random(int pourcent) {
        boolean result =  new Random().nextInt(100) <= pourcent;
        App.getApp().getLogger().log("Random done pourcent:" + pourcent + " result:" + result);
        return result;
    }

}
