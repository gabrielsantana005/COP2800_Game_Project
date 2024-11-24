package com.clone.flappy.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class Bird {
    private static final int gravity = -15;
    private static final int movementSpeed = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation birdAnimation;
    private Texture texture;
    private Sound flapSound;
    private float flapVolume = 0.1f; // Sets the volume of the flap SFX to 25%.

    //Constructor
    public Bird(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("FlappyBird.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());

        // Loads the sound effect.
        flapSound = Gdx.audio.newSound(Gdx.files.internal("Flap.wav"));
    }

    public void update(float deltaTime){
        birdAnimation.update(deltaTime);
        if (position.y > 0)
            velocity.add(0, gravity, 0);
        velocity.scl(deltaTime);
        position.add(movementSpeed * deltaTime, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }


        velocity.scl(1/deltaTime);
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return birdAnimation.getFrame();
    }

    public void jump() {
        velocity.y = 250;

        // Plays the flap sound on every jump.
        flapSound.play(flapVolume);
    }

    public Rectangle getBounds() {

        return bounds;
    }

    public void dispose() {
       texture.dispose();

       // Dispose of the sound effect.
        if (flapSound != null) {
            flapSound.dispose();
        }
    }
}
