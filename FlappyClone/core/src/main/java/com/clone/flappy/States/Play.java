package com.clone.flappy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.clone.flappy.GameScreen;
import com.clone.flappy.sprites.Bird;
import com.clone.flappy.sprites.Pipe;
import com.badlogic.gdx.audio.Sound;

public class Play extends State{
    private static final int pipeSpacing = 125;
    private static final int pipeCount = 4;
    private static final int ground_Y_Offset = -50;

    private Bird flappyBird;
    private Texture backGround;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Sound scoreSound;

    //Pipe array
    private Array<Pipe> pipes;

    //Score
    private int score;
    private Texture[] numTextures;


    public Play(GameStateManager gameStateManager){
        super(gameStateManager);
        flappyBird = new Bird(50, 100);
        camera.setToOrtho(false, GameScreen.screenWidth / 2, GameScreen.screenHeight / 2);

        backGround = new Texture("background-day.png");
        ground = new Texture("base.png");

        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, ground_Y_Offset);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), ground_Y_Offset);

        score = 0;

        pipes = new Array<Pipe>();
        for (int i = 1; i <= pipeCount; i++) {
            pipes.add(new Pipe(i * (pipeSpacing + Pipe.pipeWidth)));
        }

        //Load number textures
        numTextures = new Texture[10];
        for(int i = 0; i < 10; i++){
            numTextures[i] = new Texture(i + ".png");
        }

        //Loads the score sound effect.
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("Point.wav"));

    }


    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            flappyBird.jump();
        }
    }


    @Override
    protected void update(float deltaTime) {
        handleInput();
        updateGround();
        flappyBird.update(deltaTime);
        camera.position.x = flappyBird.getPosition().x + 80;

        for (Pipe pipe : pipes){
            //Increments score if pipe is passed
            if(!pipe.isPassed() && flappyBird.getPosition().x > pipe.getPosTopPipe().x + pipe.getTopPipe().getWidth()){
                pipe.markPassed();
                score++;

                // Plays a sound after everytime the bird crosses a pipe.
                scoreSound.play();
            }

            //Repos pipe when off-screen
            if (camera.position.x - (camera.viewportWidth / 2) > pipe.getPosTopPipe().x + pipe.getTopPipe().getWidth()){
                pipe.reposition(pipe.getPosTopPipe().x + ((Pipe.pipeWidth + pipeSpacing) * pipeCount));
            }
        }

        //Checks collision
        if (flappyBird.getPosition().y <= ground.getHeight() + ground_Y_Offset){
            gameStateManager.set(new GameOver(gameStateManager));
        }

        camera.update();

    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        //Draw Background and flappy the bird
        spriteBatch.draw(backGround, camera.position.x - (camera.viewportWidth / 2), 0);
        spriteBatch.draw(flappyBird.getTexture(), flappyBird.getPosition().x, flappyBird.getPosition().y);

        //Draw Pipes
        for (Pipe pipe : pipes){
            spriteBatch.draw(pipe.getTopPipe(), pipe.getPosTopPipe().x, pipe.getPosTopPipe().y);
            spriteBatch.draw(pipe.getBottomPipe(), pipe.getPosBottomPipe().x, pipe.getPosBottomPipe().y);

            //Set new state if pipe is touched
            if (pipe.collides(flappyBird.getBounds())) {
                gameStateManager.set(new GameOver(gameStateManager));
            }
        }

        //Draw Ground
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);

        //Draw Score
        drawScore(spriteBatch, score, camera.position.x - 15, camera.position.y - -125); // Changes score pos

        spriteBatch.end();
    }


    public void updateGround() {
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }


    public void drawScore(SpriteBatch spriteBatch, int score, float x, float y){
        String scoreString = String.valueOf(score);
        float numberWidth = 20; // Width of each number

        for (int i = 0; i < scoreString.length(); i++) {
            char digit = scoreString.charAt(i);
            int digitValue = Character.getNumericValue(digit);
            spriteBatch.draw(numTextures[digitValue], x + (i * numberWidth), y);
        }

    }


    @Override
    public void dispose() {
        backGround.dispose();
        flappyBird.dispose();
        ground.dispose();

        for(Pipe pipe : pipes) {
            pipe.dispose();
        }

        for(Texture texture : numTextures){
            texture.dispose();
        }

        //Dispose of the score sound.
        scoreSound.dispose();
    }

}