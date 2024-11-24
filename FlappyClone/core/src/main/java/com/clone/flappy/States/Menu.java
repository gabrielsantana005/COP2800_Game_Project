package com.clone.flappy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.clone.flappy.GameScreen;


public class Menu extends State {
    private Texture background;
    private Texture playButton;

    //Used to center menu button
    private float buttonX;
    private float buttonY;

    // Used for button animation
    private float scale = 1f; //Button scale
    private boolean growing = true; //Is button pulsing
    private float scaleSpeed = 0.3f; //pulse speed

    public Menu(GameStateManager gameStateManager){
        super(gameStateManager);

        //Load textures
        background = new Texture("background-day.png");
        playButton = new Texture("message.png");

        //Buttons center x,y cords
        buttonX = (GameScreen.screenWidth - playButton.getWidth()) / 2f;
        buttonY = (GameScreen.screenHeight - playButton.getHeight()) / 2f;

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

        // Animation for bird
        if (growing) {
            scale += deltaTime * scaleSpeed; // increase scale slowly
            if (scale >= 1.2f) growing = false; // checks if button is too big
        } else {
            scale -= deltaTime * scaleSpeed;  // Decrease slowly
            if (scale <= 1f ) growing = true; // check if button is too small
        }
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        //Draw background
        spriteBatch.draw(background, 0, 0, GameScreen.screenWidth, GameScreen.screenHeight);

        //Draw button
        float buttonWidth = playButton.getWidth() * scale;
        float buttonHeight = playButton.getHeight() * scale;
        spriteBatch.draw(playButton, buttonX - (buttonWidth - playButton.getWidth()) / 2f,
                buttonY - (buttonHeight - playButton.getHeight()) / 2f,
                buttonWidth, buttonHeight);


        spriteBatch.end();
    }


    @Override
    public void dispose(){
        background.dispose();
        playButton.dispose();
    }

}
