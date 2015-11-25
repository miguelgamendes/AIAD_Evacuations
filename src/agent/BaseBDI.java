package agent;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Capability;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import map.EvacScene;
import movement.MovementCapability;

/**
 * Created by joao on 23-11-2015.
 */
@Agent
public abstract class BaseBDI  {
    @Agent
    protected BDIAgent agent;

    @Capability
    protected MovementCapability capability = new MovementCapability();

    public BaseBDI(){

    }

    @AgentBody
    public void body() {
        agent.dispatchTopLevelGoal(capability.new WalkAround());
    }

    public MovementCapability getMovementCapability() {
        return capability;
    }
}
