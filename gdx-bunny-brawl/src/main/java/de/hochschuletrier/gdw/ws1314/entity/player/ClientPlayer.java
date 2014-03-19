package de.hochschuletrier.gdw.ws1314.entity.player;

import de.hochschuletrier.gdw.ws1314.basic.PlayerInfo;
import de.hochschuletrier.gdw.ws1314.entity.ClientEntity;
import de.hochschuletrier.gdw.ws1314.entity.EntityType;
import de.hochschuletrier.gdw.ws1314.entity.player.kit.PlayerKit;
import de.hochschuletrier.gdw.ws1314.input.FacingDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ElFapo
 *
 */

public class ClientPlayer extends ClientEntity
{
    private static final Logger logger = LoggerFactory.getLogger(ClientPlayer.class);

    private int 			eggCount;
    private float 			currentHealth;
    private float			currentArmor;
    private FacingDirection facingDirection;
    private PlayerKit		playerKit;
    private PlayerInfo		playerInfo;
    
    public int 				getEggCount() 		{ return eggCount; }
    public float 			getCurrentHealth() 	{ return currentHealth; }
    public float 			getCurrentArmor() 	{ return currentArmor; }
    public FacingDirection getFacingDirection() { return facingDirection; }
    public PlayerKit 		getPlayerKit() 		{ return playerKit; }
    public PlayerInfo 		getPlayerInfo() 	{ return playerInfo; }
    public EntityType getEntityType() 			{ return playerKit.getEntityType(); }
    
    public void setCurrentHealth(float currentHealth) 				{ this.currentHealth = currentHealth; }
    public void setEggCount(int eggCount) 							{ this.eggCount = eggCount; }
    public void setCurrentArmor(float currentArmor) 				{ this.currentArmor = currentArmor; }
    public void setFacingDirection(FacingDirection facingDirection) { this.facingDirection = facingDirection; }
    public void setPlayerKit(PlayerKit playerKit) 					{ this.playerKit = playerKit; }
    public void setPlayerInfo(PlayerInfo playerInfo)				{ this.playerInfo = playerInfo; }
    
    public void enable() {}
    public void disable() {}
    public void dispose() {}
    
    public void update(float delta) 
    {
        logger.info("Meine Position x:{} y:{}",getPosition().x,getPosition().y);
    }
    
    @Override
    public void render() 
    {
    }

}
