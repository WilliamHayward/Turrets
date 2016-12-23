package com.williamhayward.turrets.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.williamhayward.turrets.entities.players.Player;
import com.williamhayward.turrets.graphics.SpriteCache;
import com.williamhayward.turrets.graphics.SpriteList;

public class PlayUI extends UserInterface {
	private Label money;
	private Player player;
	public PlayUI(Player player, Skin skin) {
		this.player = player;
		Image coin = new Image(SpriteCache.loadSprite(SpriteList.COIN_HIGH).getFrame());
		coin.setPosition(5, 5);
		this.addActor(coin);
		
		money = new Label("", skin);
		money.setPosition(coin.getWidth() + 10, coin.getY() + coin.getHeight() / 2f - money.getHeight() / 2f);
		this.addActor(money);
	}
	
	@Override
	public void act(float delta) {
		money.setText(String.valueOf(player.getMoney()));
	}

}
