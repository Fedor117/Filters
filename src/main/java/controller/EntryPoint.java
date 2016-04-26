package controller;

import view.FilterFrame;

public class EntryPoint {

    public static void main(String[] args) {
        new FilterFrame(new FilterController());
    }

}
