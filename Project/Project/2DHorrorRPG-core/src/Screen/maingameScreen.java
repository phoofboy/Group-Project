package Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import entities.Player;



public class maingameScreen implements Screen {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() /2, 0 );
		camera.update();
		
		renderer.setView(camera);
		renderer.getBatch().begin();
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("1stLevelFloor"));
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("ExitFence"));
		player.draw(renderer.getBatch());
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("Gate"));
		renderer.getBatch().end();
	}
	@Override
	public void show() {
		
		map = new TmxMapLoader().load("maps/2DHaddonfield.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		player = new Player(new Sprite(new Texture("characters/down1.png")), (TiledMapTileLayer) map.getLayers().get(0));
	//	player.setPosition(-50 * player.getCollisionLayer().getTileWidth(), -38* player.getCollisionLayer().getTileHeight());
		Gdx.input.setInputProcessor(player);
	}
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 2f;
		camera.viewportHeight = height / 2f;
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {

	}
	@Override
	public void hide() {
		dispose();
	}
	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		player.getTexture().dispose();
	}

}
