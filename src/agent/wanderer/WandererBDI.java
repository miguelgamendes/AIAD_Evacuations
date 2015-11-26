package agent.wanderer;

import agent.BaseBDI;
import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Capability;
import jadex.extension.envsupport.math.Vector2Int;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import movement.MovementCapability;

/**
 * Created by Jo�o on 14/11/2015.
 */

@Agent
public class WandererBDI extends BaseBDI {

    @AgentBody
    public void body() {
        agent.dispatchTopLevelGoal(capability.new WalkAround());
    }
}
