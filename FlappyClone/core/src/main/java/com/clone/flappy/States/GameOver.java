package com.clone.flappy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.clone.flappy.GameScreen;
import com.badlogic.gdx.audio.Sound;
import com.clone.flappy.States.GameStateManager;
import com.clone.flappy.States.Menu;
import com.clone.flappy.States.State;

public class GameOver extends State {
    private Texture background, gameOverText, statsBox, medal;
    private float scale = 1f;
    private int currentScore, highScore;
    private BitmapFont scoreFont;
    private Sound deathSound;
    private float deathVolume = 0.1f;

    // UI Element positions
    private float centerX;
    private float centerY;

    // Vertical spacing between elements
    private final float VERTICAL_SPACING = 50f; // Reduced spacing

    public GameOver(GameStateManager gameStateManager, int score) {
        super(gameStateManager);
        this.currentScore = score;

        // Initialize camera
        camera.setToOrtho(false, GameScreen.screenWidth, GameScreen.screenHeight);

        // Calculate center of screen based on camera
        centerX = camera.viewportWidth / 2f;
        centerY = camera.viewportHeight / 2f;

        // Load textures
        background = new Texture("background-day.png");
        statsBox = new Texture("box.png");
        gameOverText = new Texture("youDied.png");

        // Loads death sound effect.
        deathSound = Gdx.audio.newSound(Gdx.files.internal("YouDied.wav"));

        // Plays death SFX when the GameOver screen is displayed.
        deathSound.play(deathVolume);

        // Initialize font
        scoreFont = new BitmapFont();
        scoreFont.setColor(Color.WHITE);
        scoreFont.getData().setScale(2f); // Make the font bigger

        // Handle high score
        Preferences prefs = Gdx.app.getPreferences("FlappyBirdHighScores");
        highScore = prefs.getInteger("highScore", 0);
        if (currentScore > highScore) {
            highScore = currentScore;
            prefs.putInteger("highScore", highScore);
            prefs.flush();
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gameStateManager.set(new Menu(gameStateManager));
        }
    }

    @Override
    public void update(float deltaTime) {
        handleInput();
        camera.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        // Set up camera projection
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        // Draw background
        spriteBatch.draw(background, 0, 0, GameScreen.screenWidth, GameScreen.screenHeight);

        //Draw Game over text
        float gameOverX = centerX - (gameOverText.getWidth() * scale / 2f);
        float gameOverY = centerY - (gameOverText.getHeight() * scale / 2f) + 200;
        spriteBatch.draw(gameOverText, gameOverX, gameOverY,
                gameOverText.getWidth() * scale,
                gameOverText.getHeight() * scale);


        // Draw stats box
        float statsBoxX = centerX - (statsBox.getWidth() * scale / 2f);
        float statsBoxY = centerY - (statsBox.getHeight() * scale / 2f);
        spriteBatch.draw(statsBox, statsBoxX, statsBoxY,
                statsBox.getWidth() * scale,
                statsBox.getHeight() * scale);

        // Draw scores
        String scoreText = "Score: " + currentScore;
        String highScoreText = "Best: " + highScore;

        // Position scores inside the stats box
        float scoreX = statsBoxX + 200; // Adjust these values to position the text
        float scoreY = statsBoxY + statsBox.getHeight() - 50; // where you want inside the box
        float highScoreY = scoreY - 50; // Space between score and high score

        scoreFont.draw(spriteBatch, scoreText, scoreX, scoreY);
        scoreFont.draw(spriteBatch, highScoreText, scoreX, highScoreY);

        spriteBatch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameOverText.dispose();
        statsBox.dispose();
        scoreFont.dispose(); // Don't forget to dispose the font
    }
}