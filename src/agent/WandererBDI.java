package agent;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Capability;
import jadex.extension.envsupport.math.Vector2Int;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import movement.MovementCapability;

/**
 * Created by João on 14/11/2015.
 */

@Agent
public class WandererBDI {
    @Agent
    protected BDIAgent agent;

    @Capability
    protected MovementCapability capability = new MovementCapability();

    public WandererBDI(){
        System.out.println("I was created");
    }

    @AgentBody
    public void body() {
        System.out.println("Inside Agent");
        agent.dispatchTopLevelGoal(capability.new WalkAround());
                System.out.println("Dispatched Goal");
    }

    public MovementCapability getMovementCapability() {
        return capability;
    }

}
