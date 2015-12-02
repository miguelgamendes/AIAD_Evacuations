package agent.selfish;

import agent.BaseBDI;
<<<<<<< HEAD
import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Capability;
import jadex.extension.envsupport.math.Vector2Int;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import movement.MovementCapability;
=======
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.math.IVector2;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;

import java.util.ArrayList;
import java.util.List;
>>>>>>> 09d2a284f1c18c500b5dd626ad4b081679cb6197

/**
 * Created by joao on 25-11-2015.
 */
<<<<<<< HEAD

@Agent
public class SelfishBDI extends BaseBDI {


=======
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
>>>>>>> 09d2a284f1c18c500b5dd626ad4b081679cb6197
}
