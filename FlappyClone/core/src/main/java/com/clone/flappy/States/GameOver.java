package com.clone.flappy.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.clone.flappy.GameScreen;

public class GameOver extends State {
    private Texture background, gameOverText, statsBox, medal;
    private float scale = 1f;
    private int currentScore, highScore;

    //Game over text
    private float txtX;
    private float txtY;

    //Stats box
    private float statsBoxX;
    private float statsBoxY;

    //Medal
    private float medalX;
    private float medalY;


    public GameOver(GameStateManager gameStateManager) {
        super(gameStateManager);

        //Textures
        background = new Texture("background-day.png");
        gameOverText = new Texture("gameover.png");
        statsBox = new Texture("StatsBox.png");

        //Logic for medal Texture output
        if (currentScore >= 50) {
            medal = new Texture("platinumCoin.png");
        } else if (currentScore >= 25) {
            medal = new Texture("goldCoin.png");
        } else if (currentScore >= 10) {
            medal = new Texture("silverMedal.png");
        } else {
            medal = new Texture("bronzeCoin.png");
        }

        //Player highscore
        Preferences prefs = Gdx.app.getPreferences("FlappyBirdHighScores");
        highScore = prefs.getInteger("highScore", 0);
        if (currentScore > highScore) {
            highScore = currentScore;
            prefs.putInteger("highScore", highScore);
            prefs.flush();
        }

        //Scale txt
        //Int at end used for moving txt by pixels (NO TOUCHY!!!)
        txtX = (GameScreen.screenWidth / 2f) - (gameOverText.getWidth() / 2f) - 80;
        txtY = (GameScreen.screenHeight / 2f) - (gameOverText.getHeight() / 2f)  - 100;

        //Scale Stats box
        statsBoxX = (GameScreen.screenWidth / 2f) - (statsBox.getWidth() / 2f) - 80;
        statsBoxY = (GameScreen.screenHeight / 2f) - (statsBox.getHeight() / 2f) - 201;

        //Scale medal
        medalX = (GameScreen.screenWidth / 2f) - (medal.getWidth() / 2f) - 112;
        medalY = (GameScreen.screenHeight / 2f) - (medal.getHeight() / 2f) - 205;


    }


    @Override
    public void handleInput() {

    }

    @Override
    public void update(float deltaTime) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();

        //Draw background
        spriteBatch.draw(background, 0, 0, GameScreen.screenWidth, GameScreen.screenHeight);

        //Gameover text
        float txtWidth = gameOverText.getWidth() * scale;
        float txtHeight = gameOverText.getHeight() * scale;
        spriteBatch.draw(gameOverText, txtX, txtY, txtWidth, txtHeight);

        //Stats box
        float statsBoxWidth = statsBox.getWidth() * scale;
        float statsBoxHeight = statsBox.getHeight() * scale;
        spriteBatch.draw(statsBox, statsBoxX, statsBoxY, statsBoxWidth, statsBoxHeight);

        //medal
        float medalWidth = medal.getWidth() * scale;
        float medalHeight = medal.getHeight() * scale;
        spriteBatch.draw(medal, medalX, medalY, medalWidth, medalHeight);


        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameOverText.dispose();
        statsBox.dispose();

    }
}
