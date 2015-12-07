package agent.wanderer;

import agent.BaseBDI;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;

/**
 * Created by Jo�o on 14/11/2015.
 */

@Agent
public class WandererBDI extends BaseBDI {

    @AgentBody
    public void body() {
        agent.dispatchTopLevelGoal(movementCapability.new WalkAround());
    }
}
