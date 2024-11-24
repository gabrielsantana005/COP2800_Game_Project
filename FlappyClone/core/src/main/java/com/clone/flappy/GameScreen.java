package com.clone.flappy;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.clone.flappy.States.GameStateManager;
import com.clone.flappy.States.Menu;

import static com.badlogic.gdx.Gdx.gl20;

public class GameScreen extends ApplicationAdapter {
    //Screen size variable, change these to make render box bigger or smaller
    public static final int screenWidth = 480;
    public static final int screenHeight = 800;

    private GameStateManager gameStateManager;
    private SpriteBatch spriteBatch;
    private Music music;

    SpriteBatch batch;

    @Override
    public void create(){
        batch = new SpriteBatch();
        gameStateManager = new GameStateManager();
        music = Gdx.audio.newMusic (Gdx.files.internal("DefaultMusic.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gameStateManager.push(new Menu(gameStateManager));
    }


    @Override
    public void render(){

        Gdx.gl.glClear(gl20.GL_COLOR_BUFFER_BIT);
        gameStateManager.update(Gdx.graphics.getDeltaTime());
        gameStateManager.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }
}