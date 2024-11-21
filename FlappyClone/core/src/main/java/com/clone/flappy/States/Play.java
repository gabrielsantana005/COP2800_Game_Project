package com.clone.flappy.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.clone.flappy.GameScreen;
import com.clone.flappy.sprites.Bird;
import com.clone.flappy.sprites.Pipe;

public class Play extends State{
    private static final int pipeSpacing = 125;
    private static final int pipeCount = 4;
    private static final int ground_Y_Offset = -50;

    private Bird flappyBird;
    private Texture backGround;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    //Pipe array
    private Array<Pipe> pipes;


    public Play(GameStateManager gameStateManager){
        super(gameStateManager);
        flappyBird = new Bird(50, 100);
        camera.setToOrtho(false, GameScreen.screenWidth / 2, GameScreen.screenHeight / 2);
        backGround = new Texture("background-day.png");
        ground = new Texture("base.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, ground_Y_Offset);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), ground_Y_Offset);

        pipes = new Array<Pipe>();
        for (int i = 1; i <= pipeCount; i++) {
            pipes.add(new Pipe(i * (pipeSpacing + Pipe.pipeWidth)));
        }

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
            if (camera.position.x - (camera.viewportWidth / 2) > pipe.getPosTopPipe().x + pipe.getTopPipe().getWidth()){
                pipe.reposition(pipe.getPosTopPipe().x + ((Pipe.pipeWidth + pipeSpacing) * pipeCount));
            }
        }
        if (flappyBird.getPosition().y <= ground.getHeight() + ground_Y_Offset)
            gameStateManager.set(new Play(gameStateManager));
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
            if (pipe.collides(flappyBird.getBounds())) {
                gameStateManager.set(new Play(gameStateManager));
            }
        }

        //Draw Ground
        spriteBatch.draw(ground, groundPos1.x, groundPos1.y);
        spriteBatch.draw(ground, groundPos2.x, groundPos2.y);

        spriteBatch.end();
    }

    public void updateGround() {
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }

    @Override
    public void dispose() {
        backGround.dispose();
        flappyBird.dispose();
        ground.dispose();
        for(Pipe pipe : pipes) {
            pipe.dispose();
        }
    }

}
