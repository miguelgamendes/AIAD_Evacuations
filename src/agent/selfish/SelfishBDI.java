package agent.selfish;

import agent.BaseBDI;

import jadex.bdiv3.BDIAgent;
import jadex.bdiv3.annotation.Capability;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import movement.MovementCapability;

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

        double dist = Double.MAX_VALUE;

        ISpaceObject exit = null;
        Space2D space = (Space2D)capability.getEnv();
        for (ISpaceObject currExit : exits) {
            double currDist = space.getDistance((IVector2) currExit.getProperty("position"), (IVector2) capability.getMyself().getProperty("position")).getAsDouble();
            if (currDist < dist) {
                exit = currExit;
                dist = currDist;
            }
        }

        agent.dispatchTopLevelGoal(capability.new Move( exit.getProperty("position"))).get();

        System.out.println("Arrived at exit");
    }
}
