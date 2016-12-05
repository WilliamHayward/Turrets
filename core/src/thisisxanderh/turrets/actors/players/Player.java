package thisisxanderh.turrets.actors.players;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import thisisxanderh.turrets.actors.buildings.Building;
import thisisxanderh.turrets.actors.buildings.BuildingList;
import thisisxanderh.turrets.actors.buildings.traps.Glue;
import thisisxanderh.turrets.actors.buildings.traps.Spikes;
import thisisxanderh.turrets.actors.buildings.turrets.Cannon;
import thisisxanderh.turrets.actors.buildings.turrets.MachineGun;
import thisisxanderh.turrets.actors.buildings.turrets.Turret;
import thisisxanderh.turrets.actors.enemies.Enemy;
import thisisxanderh.turrets.core.Coordinate;
import thisisxanderh.turrets.core.GameActor;
import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.input.DeviceList;
import thisisxanderh.turrets.input.InputManager;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Player extends GameActor {
	private static final float GRAVITY = -25f;
	private InputManager input;
	private boolean facingLeft = false;
	private boolean doubleJumpAvailable = true;
	private boolean groundPound = false;
	private float stunnedTimer = 0f;
	protected Color color = null;
	private List<BuildingList> buildings;
	private int currentBuilding = -2;
	private Building building;
	private boolean shipMode = false;

	protected float highDamage;
	protected float lowDamage;
	protected float speed;
	
	protected Texture standing;
	protected Texture ship;
	
	protected PlayerTypes type = PlayerTypes.HERO;
	
	public Player(SpriteList image) {
		super(image);
		standing = SpriteCache.loadSprite(image);
		solid = true;
		layer = LayerList.PLAYER;
		buildings = new ArrayList<>();
		buildings.add(BuildingList.MACHINE_GUN);
		buildings.add(BuildingList.CANNON);
		buildings.add(BuildingList.GLUE);
		buildings.add(BuildingList.SPIKES);
	}
	
	public void spawn() {
		GameStage stage = (GameStage) this.getStage();
		Coordinate spawn = stage.getSpawn(type);
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
        }
        
        if (!shipMode) {
	        if (onGround) {
	        	if (groundPound) {
	        		stunnedTimer = 0.5f;
	        		setYVelocity(4f);
	        	}
	        	doubleJumpAvailable = true;
	        	groundPound = false;
	        }
	        
			this.addYVelocity(GRAVITY * delta);
			
			if (building != null) {
				
				Coordinate position = input.getCursorTile();
				float tileFraction = building.getHeight() / Tile.SIZE;
				
				if (tileFraction < 1 && building instanceof Turret) {
					tileFraction *= Tile.SIZE;
				} else {
					tileFraction = 0;
				}
				building.setX(position.getX());
				building.setY(position.getY() + tileFraction);
				
			}
        }
		
		this.getStage().getCamera().position.x = this.getX();
		this.getStage().getCamera().position.y = this.getY();
	}
	
	public void setInput(InputManager input) {
		this.input = input;
	}
	
	private void handleInput() {
		input.update();
		
		if (input.getSwitch()) {
			if (shipMode) {
				this.texture = standing;
				this.setRotation(0);
			} else {
				this.texture = ship;
				deselectBuilding();
			}
			shipMode = !shipMode;
			solid = !solid;
			this.setSize(texture.getWidth(), texture.getHeight());
		}
		
		if (shipMode) {
			this.handleShipInput();
		} else {
			this.handleFootInput();
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)) {
			if (input.getDevice() == DeviceList.KEYBOARD) {
				input.setController(Controllers.getControllers().first());
			} else {
				input.setKeyboard();
			}
		}
		
	}
	
	private void handleShipInput() {
		float horizontalSpeed = input.getHorizontal();
		this.setXVelocity(horizontalSpeed * speed * 2);
		float verticalSpeed = input.getVertical();
		this.setYVelocity(verticalSpeed * speed * 2);
	}
	
	private void handleFootInput() {
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
		
		if (input.getExit()) {
			deselectBuilding();
		}
		
		if (input.getBuild() && building != null) {
			if (building.build()) {
				building = null;
				currentBuilding = -2;
				selectBuilding(currentBuilding);
			}
		}
		
		facingLeft = input.getFacing(facingLeft);
	}
	
	private void deselectBuilding() {
		if (building != null) {
			building.remove();
		}
		building = null;
		currentBuilding = -2;
	}
	
	public void selectBuilding(int newBuilding) {
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
		if (other instanceof Enemy) {
			collideEnemy((Enemy) other);
		}
	}
	
	private void collideEnemy(Enemy enemy) {
		Rectangle bounds = getBounds();
		if (this.getYVelocity() > 0) {
			return;
		}
		
		if (bounds.getY() - this.getYVelocity() <= enemy.getY() + enemy.getHeight()) {
			return;
		}
		
		float damage = groundPound ? highDamage : lowDamage;
		enemy.damage(damage, this);
		this.setYVelocity(13);
		groundPound = false;
	}
	
}
