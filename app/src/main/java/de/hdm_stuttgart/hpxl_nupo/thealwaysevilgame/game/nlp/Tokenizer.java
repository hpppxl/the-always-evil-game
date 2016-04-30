package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nerd on 26/04/16.
 */
public class Tokenizer {
//region Constants
    private static final String TOKEN_SEPARATOR = " ";
//endregion

//region Properties & Members
//endregion

//region Constructors
//endregion

//region Methods
    public static ArrayList<String> tokenize(String string){
        return new ArrayList<>(Arrays.asList(string.split(TOKEN_SEPARATOR)));
    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
