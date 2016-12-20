package thisisxanderh.turrets.entities.players;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.entities.Entity;
import thisisxanderh.turrets.entities.States;
import thisisxanderh.turrets.entities.buildings.Building;
import thisisxanderh.turrets.entities.buildings.BuildingList;
import thisisxanderh.turrets.entities.buildings.traps.Glue;
import thisisxanderh.turrets.entities.buildings.traps.Spikes;
import thisisxanderh.turrets.entities.buildings.turrets.Cannon;
import thisisxanderh.turrets.entities.buildings.turrets.MachineGun;
import thisisxanderh.turrets.entities.buildings.turrets.Turret;
import thisisxanderh.turrets.entities.enemies.Enemy;
import thisisxanderh.turrets.graphics.LayerList;
import thisisxanderh.turrets.graphics.SpriteCache;
import thisisxanderh.turrets.graphics.SpriteList;
import thisisxanderh.turrets.input.InputManager;
import thisisxanderh.turrets.terrain.Tile;
import thisisxanderh.turrets.ui.UIManager;

public abstract class Player extends Entity {
	private static final float GRAVITY = -25f;
	
	private InputManager input;
	private UIManager ui;
	
	private boolean doubleJumpAvailable = true;
	private float stunnedTimer = 0f;
	
	private List<BuildingList> buildings;
	private int currentBuilding = -2;
	private Building building;
	
	protected float highDamage;
	protected float lowDamage;
	protected float speed;
	
	protected PlayerTypes type = PlayerTypes.HERO;
	
	private OrthographicCamera camera;
	private Viewport viewport;
	
	public Player(SpriteList image) {
		super(image);
		this.addState(States.STANDING, SpriteCache.loadSprite(image));
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
		Vector2 spawn = stage.getSpawn(type);
		this.setX(spawn.x * Tile.SIZE);
		this.setY(spawn.y * Tile.SIZE);
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		super.draw(batch, alpha);
		batch.end();
		ui.draw();
		batch.begin();
	}
	
	@Override
	public void act(float delta) {
		input.act(delta);
		ui.act();
		if (!this.inWorld()) {
			spawn();
		}
		
		GameStage stage = (GameStage) this.getStage();
		if (!stage.getController().isBuildMode() && building != null) {
			deselectBuilding();
		}
		
		if (input.getPause()) {
			stage.getController().togglePause();
			ui.togglePause();
			return;
		}
		
		if (input.getConsole()) {
			ui.toggleConsole();
		}
		
		if (stage.getController().isPaused()) {
			return;
		}
		
		
		stunnedTimer -= delta;
		if (stunnedTimer < 0 && !this.isState(States.POUNDING)) {
			handleInput();
		}
		
		super.act(delta);
		Rectangle bounds = this.getBounds();
    	onGround = false;
    	
        if (stage.getTerrain().overlaps(bounds)) {
        	this.moveToContact();
        }
        
        if (!this.isState(States.FLYING)) {
	        if (onGround) {
	        	if (this.isState(States.POUNDING)) {
	        		stunnedTimer = 0.5f;
	        		setYVelocity(4f);
	        		this.setState(States.STUNNED);
	        	}
	        	
	        	if (!this.isState(States.STUNNED)) {
		        	doubleJumpAvailable = true;
		        }
	        	
	        }
	        
			this.addYVelocity(GRAVITY * delta);
			
			if (building != null) {
				Vector2 position = input.getCursorTile();
				float tileFraction = building.getHeight() / Tile.SIZE;
				
				if (tileFraction < 1 && building instanceof Turret) {
					tileFraction *= Tile.SIZE;
				} else {
					tileFraction = 0;
				}
				building.setX(position.x);
				building.setY(position.y + tileFraction);
				
			}
        }
		
		camera.position.x = this.getX();
		camera.position.y = this.getY();
	}
	
	private void handleInput() {
		TextureRegion texture = sprite.getFrame();
		if (input.getSwitch()) {
			if (this.isState(States.FLYING)) {
				setState(States.STANDING);
			} else {
				setState(States.FLYING);
				deselectBuilding();
			}
			solid = !solid;
			this.setSize(texture.getRegionWidth(), texture.getRegionHeight());
		}

		if (this.isState(States.FLYING)) {
			handleShipInput();
		} else {
			handleFootInput();
			GameStage stage = (GameStage) this.getStage();
			if (stage.getController().isBuildMode()) {
				handleBuildInput();
			} else {
				handlePlayInput();
			}
		}
	}
	
	public void setInput(InputManager input) {
		this.input = input;
	}
	
	public InputManager getInput() {
		return input;
	}
	
	public void setUI(UIManager ui) {
		this.ui = ui;
	}
	
	public UIManager getUI() {
		return ui;
	}
	
	private void handleShipInput() {
		float horizontalSpeed = input.getHorizontal();
		this.setXVelocity(horizontalSpeed * speed * 2);
		float verticalSpeed = input.getVertical();
		this.setYVelocity(verticalSpeed * speed * 2);
	}
	
	private void handlePlayInput() {
		if (!onGround && input.getPound()) {
			this.setYVelocity(-20);
			this.setXVelocity(0);
			doubleJumpAvailable = false;
			this.setState(States.POUNDING);
		}
	}
	
	private void handleBuildInput() {
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
		
		if (input.getBuild() && building != null && building.build()) {
			building = null;
			currentBuilding = -2;
			selectBuilding(currentBuilding);
		}
	}
	
	private void handleFootInput() {
		float horizontalSpeed = input.getHorizontal();
		
		this.setXVelocity(horizontalSpeed * speed);
		
		if (onGround) {
			if (horizontalSpeed == 0) {
				this.setState(States.STANDING);
			} else {
				this.setState(States.WALKING);
			}
			
			if (input.getJump()) {
				this.setState(States.JUMPING);
				this.setYVelocity(13);
			}
		} else {
			if (doubleJumpAvailable && input.getJump()) {
				this.setYVelocity(13);
				doubleJumpAvailable = false;
			}
		}
		boolean facingLeft = getScaleX() < 0;
		facingLeft = input.getFacing(facingLeft);
		float scaleX = Math.abs(getScaleX()) * (facingLeft ? -1 : 1);
		this.setScaleX(scaleX); 
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
	public void collided(Entity other) {
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
		
		float damage = this.isState(States.POUNDING) ? highDamage : lowDamage;
		enemy.damage(damage, this);
		this.setYVelocity(13);
		this.setState(States.JUMPING);
	}
	
}
