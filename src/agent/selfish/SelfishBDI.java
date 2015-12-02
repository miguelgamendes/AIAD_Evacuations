package agent.selfish;

import agent.BaseBDI;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joao on 25-11-2015.
 */
@Agent
public class SelfishBDI extends BaseBDI {

    @AgentBody
    public void body() {
        ISpaceObject[] terrains = capability.getEnv().getSpaceObjectsByType("terrain");

        List<ISpaceObject> exits = new ArrayList<>();

        for (ISpaceObject object : terrains) {
            if (object.getProperty("type") == 1) {
                exits.add(object);
            }
        }

        agent.dispatchTopLevelGoal(capability.new Move((IVector2) exits.get(0).getProperty("position")));
    }
}
