package agent.selfish;

import agent.BaseBDI;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import movement.AStar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joao on 25-11-2015.
 */
@Agent
public class SelfishBDI extends BaseBDI {

    @AgentBody
    public void body() {

        agent.dispatchTopLevelGoal(movementCapability.new Move( closestExit().getProperty("position"))).get();

        movementCapability.getEnv().destroySpaceObject(movementCapability.getMyself().getId());
        System.out.println("Arrived at exit");
    }

    ISpaceObject closestExit() {

        ISpaceObject[] terrains = movementCapability.getEnv().getSpaceObjectsByType("terrain");

        List<ISpaceObject> exits = new ArrayList<>();

        for (ISpaceObject object : terrains) {
            if (object.getProperty("type") == 1) {
                exits.add(object);
            }
        }

        double dist = Double.MAX_VALUE;

        ISpaceObject exit = null;

        for (ISpaceObject currExit : exits) {

            AStar aStar = new AStar();
            List<AStar.Node> path = aStar.Compute(
                    (new AStar.Node(((IVector2) movementCapability.getMyself().getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger(),((IVector2) movementCapability.getMyself().getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger())),
                    new AStar.Node(((IVector2) currExit.getProperty("position")).getXAsInteger(), ((IVector2) currExit.getProperty("position")).getYAsInteger()));


            if (path != null && path.size() < dist) {
                exit = currExit;
                dist = path.size();
            }
        }

        return exit;
    }
}
