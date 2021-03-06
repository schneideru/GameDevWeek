package de.hochschuletrier.gdw.ws1314.entity.levelObjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;

import de.hochschuletrier.gdw.commons.gdx.physix.PhysixBody;
import de.hochschuletrier.gdw.commons.gdx.physix.PhysixBodyDef;
import de.hochschuletrier.gdw.commons.gdx.physix.PhysixFixtureDef;
import de.hochschuletrier.gdw.commons.gdx.physix.PhysixManager;
import de.hochschuletrier.gdw.ws1314.entity.EntityType;
import de.hochschuletrier.gdw.ws1314.entity.EventType;
import de.hochschuletrier.gdw.ws1314.entity.ServerEntity;
import de.hochschuletrier.gdw.ws1314.entity.ServerEntityManager;
import de.hochschuletrier.gdw.ws1314.network.NetworkManager;
import de.hochschuletrier.gdw.ws1314.states.DualGamePlayState;


/**
 * 
 * @author yannick
 *
 */
public class ServerBush extends ServerLevelObject
{
    private static final Logger logger = LoggerFactory.getLogger(DualGamePlayState.class);
    
	public ServerBush()
	{
		super();
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
	}

	@Override
	public void beginContact(Contact contact)
	{
            ServerEntity otherEntity = this.identifyContactFixtures(contact);
            
            switch(otherEntity.getEntityType()) {
                case Projectil:
                case SwordAttack:
                    ServerEntityManager.getInstance().removeEntity(this);
                    break;
                default:
                    break;
            }
	}

	@Override
	public void endContact(Contact contact)
	{
	}

	@Override
	public EntityType getEntityType()
	{
		return EntityType.Bush;
	}

	@Override
	public void initPhysics(PhysixManager manager)
	{
            PhysixBody body = new PhysixBodyDef(BodyDef.BodyType.KinematicBody, manager)
                .position(new Vector2(properties.getFloat("x"),properties.getFloat("y")))
                .fixedRotation(false).create();

            body.createFixture(new PhysixFixtureDef(manager)
                .density(0.5f)
                .friction(0.0f)
                .restitution(0.0f)
                .shapeCircle(20));

            body.setGravityScale(0);
            body.addContactListener(this);
            setPhysicsBody(body);
	}

	@Override
	public void dispose(PhysixManager manager) {
		NetworkManager.getInstance().sendEntityEvent(getID(), EventType.DESTROY);
		super.dispose(manager);
	}

	@Override
    public void update(float deltaTime) {
        
    }
}
