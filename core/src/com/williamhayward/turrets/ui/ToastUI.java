package com.williamhayward.turrets.ui;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.williamhayward.turrets.graphics.Toast;

public class ToastUI extends UserInterface {
	private ArrayList<Toast> loaf = new ArrayList<>();
	public ToastUI(Skin skin) {
		
	}
	
	public void toast(Toast bread) {
		bread.setToaster(this);
		if (loaf.size() == 0) {
			this.addActor(bread);
		}
		loaf.add(bread);
	}
	
	public void toastNext() {
		loaf.remove(0);
		if (loaf.size() > 0) {
			Toast bread = loaf.get(0);
			this.addActor(bread);
		}
	}
	
	public void eject(boolean immediate) {
		if (loaf.size() > 0) {
			Toast current = loaf.get(0);
			if (immediate) {
				current.remove();
				loaf.clear();
			} else {
				current.rush();
				loaf.clear();
				loaf.add(current);
			}
		}
	}
}
