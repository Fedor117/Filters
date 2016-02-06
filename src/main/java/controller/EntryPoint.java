package controller;

import view.PicturesFrame;

public class EntryPoint implements Runnable {
    public void run() {
        new PicturesFrame();
    }

    public static void main(String[] args) {
        new EntryPoint().run();
    }
}
