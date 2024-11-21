package com.clone.flappy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.clone.flappy.GameScreen;


public class Menu extends State {
    private Texture background;
    private Texture playButton;

    public Menu(GameStateManager gameStateManager){
        super(gameStateManager);
        background = new Texture("background-day.png");
        playButton = new Texture("StartButton.png");
    }


    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gameStateManager.set(new Play(gameStateManager));
        }
    }


    @Override
    public void update(float deltaTime) {
        handleInput();
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        spriteBatch.draw(background, 0, 0, GameScreen.screenWidth, GameScreen.screenHeight);
        spriteBatch.draw(playButton, (GameScreen.screenWidth / 2) - (GameScreen.screenHeight / 2),
            GameScreen.screenHeight / 2);

        spriteBatch.end();
    }


    @Override
    public void dispose(){
        background.dispose();
        playButton.dispose();
    }

}
