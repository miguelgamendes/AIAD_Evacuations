package agent;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Capability;
import jadex.micro.annotation.Agent;
import movement.MovementCapability;

/**
 * Created by joao on 23-11-2015.
 */
@Agent
public abstract class BaseBDI  {
    @Agent
    protected BDIAgent agent;

    @Capability
    protected MovementCapability movementCapability = new MovementCapability();

    public BaseBDI(){

    }



    public MovementCapability getMovementCapability() {
        return movementCapability;
    }
}
