package thisisxanderh.turrets.graphics;

/**
 * The ordering of this enum is important. The first item will render below all others,
 * and the last item will render above
 * 
 * TODO: Considering using this as a filter for collisions between entities as well
 */
public enum LayerList {
	DEFAULT,
	BULLET,
	BUILDING,
	ENEMY,
	PLAYER,
}
