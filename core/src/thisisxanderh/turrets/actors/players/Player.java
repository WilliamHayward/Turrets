package thisisxanderh.turrets.actors.players;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.actors.buildings.Building;
import thisisxanderh.turrets.actors.buildings.BuildingList;
import thisisxanderh.turrets.actors.buildings.traps.Glue;
import thisisxanderh.turrets.actors.buildings.traps.Spikes;
import thisisxanderh.turrets.actors.buildings.turrets.Cannon;
import thisisxanderh.turrets.actors.buildings.turrets.MachineGun;
import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameActor;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.input.DeviceList;
import thisisxanderh.turrets.input.InputManager;
import thisisxanderh.turrets.terrain.Tile;
import javafx.scene.paint.Color;

public abstract class Player extends GameActor {
	private float speed = 10.5f;
	private static final float GRAVITY = -25f;
	private InputManager input;
	private boolean facingLeft = false;
	private boolean doubleJumpAvailable = true;
	private boolean groundPound = false;
	private float stunnedTimer = 0f;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	protected Color color = null;
	private List<BuildingList> buildings;
	private int currentBuilding = -2;
	private Building building;
	
	public Player(OrthographicCamera camera, SpriteList image) {
		super(SpriteList.PLAYER_BLUE_STANDING);
		this.camera = camera;
		input = new InputManager(camera);
		layer = LayerList.PLAYER;
		buildings = new ArrayList<>();
		buildings.add(BuildingList.MACHINE_GUN);
		buildings.add(BuildingList.CANNON);
		buildings.add(BuildingList.GLUE);
		buildings.add(BuildingList.SPIKES);
	}
	
	public void spawn() {
		GameStage stage = (GameStage) this.getStage();
		Coordinate spawn = stage.getSpawn(color);
		this.setX(spawn.getX() * Tile.SIZE);
		this.setY(spawn.getY() * Tile.SIZE);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		SpriteBatch spriteBatch = (SpriteBatch) batch;
		spriteBatch.draw(texture, getX(), getY(), 0, 0, getWidth(), getHeight(),
				1, 1, getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), facingLeft, false);
	}
	@Override
	public void act(float delta) {
		if (!this.inWorld()) {
			spawn();
		}
		
		stunnedTimer -= delta;
		if (stunnedTimer < 0 && !groundPound) {
			handleInput();
		}
		
		super.act(delta);
		GameStage stage = (GameStage) this.getStage();
		Rectangle bounds = this.getBounds();
    	onGround = false;
        if (stage.getTerrain().overlaps(bounds)) {
        	this.moveToContact();
        } else {
        }
        
        if (onGround) {
        	if (groundPound) {
        		stunnedTimer = 0.5f;
        		setYVelocity(4f);
        	}
        	doubleJumpAvailable = true;
        	groundPound = false;
        }

		/*for (GameActor other: stage.getGameActors()) {
			
			if (!other.collides()) {
				continue;
			}
			if (!this.equals(other)) {
				if (bounds.overlaps(other.getBounds())) {
					this.collided(other);
					other.collided(this);
				}
			}
		}*/
        
		this.addYVelocity(GRAVITY * delta);
		if (building != null) {
			Coordinate position = input.getCursorTile();
			building.setX(position.getX());
			building.setY(position.getY());
		}
		
		stage.getViewport().getCamera().position.x = this.getX();
		stage.getViewport().getCamera().position.y = this.getY();
	}
	
	private void handleInput() {
		input.update();
		float horizontalSpeed = input.getHorizontal();
		this.setXVelocity(horizontalSpeed * speed);
		
		if (onGround) {
			if (input.getJump()) {
				this.setYVelocity(13);
			}
		} else {
			if (doubleJumpAvailable) {
				if (input.getJump()) {
					this.setYVelocity(13);
					doubleJumpAvailable = false;
				}
			}
			if (input.getPound()) {
				this.setYVelocity(-20);
				this.setXVelocity(0);
				doubleJumpAvailable = false;
				groundPound = true;
			}
		}
		int hotkey = input.getHotkey();
		int newBuilding = currentBuilding;
		if (hotkey!= -1) {
			newBuilding = hotkey;
		} else if (input.getNext()) {
			newBuilding++;
		} else if (input.getPrev()) {
			newBuilding--;
		}
		if (newBuilding == -1) {
			newBuilding = buildings.size() - 1;
		} else if (newBuilding == buildings.size()) {
			newBuilding = 0;
		}
		
		if (newBuilding != currentBuilding) {
			selectBuilding(newBuilding);
		}
		
		if (input.getBuild() && building != null) {
			Coordinate position = input.getCursorTile();
			if (building.build(position.getX(), position.getY(), (GameStage) this.getStage())) {
				building = null;
				currentBuilding = -2;
				selectBuilding(currentBuilding);
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
			if (input.getDevice() == DeviceList.KEYBOARD) {
				input.setController(Controllers.getControllers().first());
			} else {
				input.setKeyboard();
			}
		}
		
		facingLeft = input.getFacing(facingLeft);
	}
	
	private void selectBuilding(int newBuilding) {
		if (building != null) {
			building.remove();
		}
		if (newBuilding == -2) {
			building = null;
			return;
		}
		BuildingList buildingID = buildings.get(newBuilding);
		switch (buildingID) {
			case CANNON:
				building = new Cannon();
				break;
			case GLUE:
				building = new Glue();
				break;
			case MACHINE_GUN:
				building = new MachineGun();
				break;
			case SPIKES:
				building = new Spikes();
				break;
			default:
				break;
			
		}
		
		currentBuilding = newBuilding;
		if (building != null) {
			this.getStage().addActor(building);
		}
	}
	
	@Override
	public void collided(GameActor other) {
		Rectangle bounds = getBounds();
		if (other instanceof Enemy) {
			if (this.getYVelocity() < 0) {
				if (bounds.getY() - this.getYVelocity() > other.getY() + other.getHeight()) {
					float damage = groundPound ? 5f : 2f;
					other.damage(damage, this);
					this.setYVelocity(13);
					groundPound = false;
				}
			}
		}
	}
	
}
