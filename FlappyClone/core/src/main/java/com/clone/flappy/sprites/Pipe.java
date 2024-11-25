package com.clone.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public class Pipe {
    private static final int Fluctuation = 130;
    private static final int pipeGap = 100;
    private static final int lowestOpening = 120;
    public  static final int pipeWidth = 50;

    private Texture topPipe , bottomPipe;
    private Vector2 posTopPipe, posBottomPipe;
    private Rectangle topBounds, bottomBounds;
    private Random rand;
    private boolean isPassed;

    //Constructor
    public Pipe(float x) {
        topPipe = new Texture("top-pipe-green.png");
        bottomPipe = new Texture("pipe-green.png");
        rand = new Random();

        posTopPipe = new Vector2(x, rand.nextInt(Fluctuation) + pipeGap + lowestOpening);
        posBottomPipe = new Vector2(x, posTopPipe.y - pipeGap - bottomPipe.getHeight());

        topBounds = new Rectangle(posTopPipe.x, posTopPipe.y, topPipe.getWidth(), topPipe.getHeight());
        bottomBounds = new Rectangle(posBottomPipe.x, posBottomPipe.y,bottomPipe.getWidth(), bottomPipe.getHeight());

        isPassed = false;
    }

    //Getters
    public Texture getTopPipe() {

        return topPipe;
    }

    public Texture getBottomPipe() {
        return bottomPipe;
    }

    public Vector2 getPosTopPipe() {

        return posTopPipe;
    }

    public Vector2 getPosBottomPipe() {

        return posBottomPipe;
    }

    public void reposition (float x) {
        posTopPipe.set(x, rand.nextInt(Fluctuation) + pipeGap + lowestOpening);
        posBottomPipe.set(x, posTopPipe.y - pipeGap - bottomPipe.getHeight());
        topBounds.setPosition(posTopPipe.x, posTopPipe.y);
        bottomBounds.setPosition(posBottomPipe.x, posBottomPipe.y);
        isPassed = false;
    }

    public boolean isPassed(){
        return isPassed;
    }

    public void markPassed(){
        isPassed = true;
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(topBounds) || player.overlaps(bottomBounds);
    }

    public void dispose(){
        topPipe.dispose();
        bottomPipe.dispose();
    }
}
