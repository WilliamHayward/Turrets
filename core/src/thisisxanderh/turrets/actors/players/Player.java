package thisisxanderh.turrets.actors.players;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

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
import thisisxanderh.turrets.input.Input;
import thisisxanderh.turrets.input.InputManager;
import thisisxanderh.turrets.terrain.Tile;

public abstract class Player extends GameActor {
	private static final float GRAVITY = -25f;
	private Input input;
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
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
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
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 3.0f;
		
		viewport = new ScreenViewport(camera);
		
		viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public Viewport getViewport() {
		return viewport;
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
		batch.end();
		input.draw();
		batch.begin();
	}
	
	@Override
	public void act(float delta) {
		input.act(delta);
		if (!this.inWorld()) {
			spawn();
		}
		
		GameStage stage = (GameStage) this.getStage();
		if (!stage.getController().isBuildMode() && building != null) {
			deselectBuilding();
		}
		
		if (input.getManager().getPause()) {
			stage.getController().togglePause();
			input.togglePause();
			return;
		}
		
		if (input.getManager().getConsole()) {
			input.toggleConsole();
		}
		
		if (stage.getController().isPaused()) {
			return;
		}
		
		
		stunnedTimer -= delta;
		if (stunnedTimer < 0 && !groundPound) {
			handleInput();
		}
		
		super.act(delta);
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
		
		camera.position.x = this.getX();
		camera.position.y = this.getY();
	}
	
	public void setInput(Input input) {
		this.input = input;
	}
	
	private void handleInput() {
		InputManager manager = input.getManager();
		
		if (manager.getSwitch()) {
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
			handleShipInput(manager);
		} else {
			handleFootInput(manager);
			GameStage stage = (GameStage) this.getStage();
			if (stage.getController().isBuildMode()) {
				handleBuildInput(manager);
			} else {
				handlePlayInput(manager);
			}
		}
	}
	
	public Input getInput() {
		return input;
	}
	
	private void handleShipInput(InputManager manager) {
		float horizontalSpeed = manager.getHorizontal();
		this.setXVelocity(horizontalSpeed * speed * 2);
		float verticalSpeed = manager.getVertical();
		this.setYVelocity(verticalSpeed * speed * 2);
	}
	
	private void handlePlayInput(InputManager manager) {
		if (!onGround && manager.getPound()) {
			this.setYVelocity(-20);
			this.setXVelocity(0);
			doubleJumpAvailable = false;
			groundPound = true;
		}
	}
	
	private void handleBuildInput(InputManager manager) {
		int hotkey = manager.getHotkey();
		int newBuilding = currentBuilding;
		if (hotkey!= -1) {
			newBuilding = hotkey;
		} else if (manager.getNext()) {
			newBuilding++;
		} else if (manager.getPrev()) {
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
		
		if (manager.getExit()) {
			deselectBuilding();
		}
		
		if (manager.getBuild() && building != null && building.build()) {
			building = null;
			currentBuilding = -2;
			selectBuilding(currentBuilding);
		}
	}
	
	private void handleFootInput(InputManager manager) {
		float horizontalSpeed = manager.getHorizontal();
		this.setXVelocity(horizontalSpeed * speed);
		
		if (onGround) {
			if (manager.getJump()) {
				this.setYVelocity(13);
			}
		} else {
			if (doubleJumpAvailable && manager.getJump()) {
				this.setYVelocity(13);
				doubleJumpAvailable = false;
			}
		}
		
		facingLeft = manager.getFacing(facingLeft);
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
