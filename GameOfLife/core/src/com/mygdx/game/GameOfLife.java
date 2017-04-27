package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;

public class GameOfLife extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	//Sprite Square = new Sprite(new Texture(myShape));
	//OrthographicCamera camera = new OrthographicCamera();

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
        /*float squareHeight = camera.viewportWidth/8;
		float squareWidth = camera.viewportWidth/8;
		Square.setSize(squareWidth, squareHeight);
        batch.begin();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Square.setPosition((x * squareWidth), (y * squareHeight));
                Square.draw(batch);
            }
        }
        batch.end();*/
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
