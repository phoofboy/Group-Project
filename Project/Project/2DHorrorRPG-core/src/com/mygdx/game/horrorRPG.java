package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screen.maingameScreen;

public class horrorRPG extends Game {
	public static final float WIDTH = 0;
	public SpriteBatch batch;
	
	@Override
	public void create () {
		setScreen(new maingameScreen());
	}
	
	@Override
	
	public void dispose() {
		super.dispose();
	}
	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	@Override
	public void pause() {
		super.pause();
	}
	@Override
	public void resume() {
		super.resume();
	}
}