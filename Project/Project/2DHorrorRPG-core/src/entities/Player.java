package entities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;



public class Player extends Sprite implements InputProcessor {
	public enum State { RUNNING, STANDING };
	public State currentState;
	public State previousState;
	private TextureRegion playerStand;
	private Animation playerRun;
	private float stateTimer;
	private boolean runningRight;
	
	// the movement velocity 
	private Vector2 velocity = new Vector2();
	private boolean canMove;
	// player speed 
	private float speed = 60 * 2f;
	//private float gravity = 60 * 1.8f;
	private TiledMapTileLayer collisionLayer;
	private String blockedKey = "blocked";
	private Animation up, down, left, right;
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		this.collisionLayer = collisionLayer;
		} 
	@Override
	public void draw(Batch batch) {
			update(Gdx.graphics.getDeltaTime());
			super.draw(batch);
			} 
	public void update(float delta) {
		// clamp velocity
		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < -speed)
			velocity.y = -speed;

		// save old position
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;
		// move on x
		setX(getX() + velocity.x * delta * 2f);

		if(velocity.x < 0) // going left
			collisionX = collidesLeft();
		else if(velocity.x > 0) // going right
			collisionX = collidesRight();
		// react to x collision
		if(collisionX) {
			setX(oldX);
			velocity.x = 0;
		}
		// move on y
		setY(getY() + velocity.y * delta * 2f);

		if(velocity.y < 0) // going down
			canMove = collisionY = collidesBottom();
		else if(velocity.y > 0) // going up
			collisionY = collidesTop();
		// react to y collision
		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
				System.out.println("x veloc: " + velocity.x + " y veloc: " + velocity.y);
	}
	public Vector2 getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}
	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			velocity.y = speed;
			break;
		case Keys.A:
			velocity.x = -speed;
			break;
		case Keys.S:
			velocity.y = -speed;
			break;
		case Keys.D:
			velocity.x = speed;
			break;
		}
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
			case Keys.A:
			velocity.x = 0;
			break;
		}
		switch(keycode) {
			case Keys.D:
			velocity.x = 0;
			break;
		}
		switch(keycode) {
			case Keys.W:
			velocity.y = 0;
			break;
		}
		switch(keycode) {
			case Keys.S:
			velocity.y = 0;
			break;
		}
		return true;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	private boolean isCellBlocked(float x, float y) {
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}
public boolean collidesRight() {
	for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
		if(isCellBlocked(getX() + getWidth(), getY() + step))
			return true;
	return false;
}

public boolean collidesLeft() {
	for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
		if(isCellBlocked(getX(), getY() + step))
			return true;
	return false;
}

public boolean collidesTop() {
	for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
		if(isCellBlocked(getX() + step, getY() + getHeight()))
			return true;
	return false;

}

public boolean collidesBottom() {
	for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
		if(isCellBlocked(getX() + step, getY()))
			return true;
	return false;
	}
}
