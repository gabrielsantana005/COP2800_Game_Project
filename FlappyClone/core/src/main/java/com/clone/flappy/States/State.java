package com.clone.flappy.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera camera;
    protected Vector3 mouse;
    protected GameStateManager gameStateManager;

    protected State (GameStateManager GSM){
        this.gameStateManager = GSM;
        camera = new OrthographicCamera();
        mouse = new Vector3();


    }

    protected abstract void handleInput();
    protected abstract void update(float deltaTime); //Delta time is difference between frames rendered
    public abstract void render(SpriteBatch spriteBatch); //Container for everything rendered to screen
    public abstract void dispose();
}
