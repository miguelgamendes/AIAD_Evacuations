package movement;

import jadex.bdiv3.annotation.*;
import jadex.bdiv3.runtime.IPlan;
import jadex.bdiv3.runtime.PlanFinishedTaskCondition;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.environment.AbstractEnvironmentSpace;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.commons.future.DelegationResultListener;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jo�o on 14/11/2015.
 */

@Plan
public class MoveToLocationPlan {

    @PlanCapability
    protected MovementCapability capa;

    @PlanAPI
    protected IPlan rplan;

    @PlanReason
    protected IDestinationGoal goal;


    @PlanBody
    public IFuture<Void> body() {

        AbstractEnvironmentSpace env = capa.getEnv();
        ISpaceObject myself = (ISpaceObject) capa.getMyself();

        Vector2Int dest = (Vector2Int) this.goal.getDestination();

//      Task Properties
        Map properties = new HashMap();
        properties.put(MoveTask.PROPERTY_DESTINATION, dest);
        properties.put(MoveTask.PROPERTY_SCOPE, this.capa.getCapability().getAgent().getExternalAccess());
        properties.put(AbstractTask.PROPERTY_CONDITION, new PlanFinishedTaskCondition(this.rplan));

        AStar aStar = new AStar();

        List<AStar.Node> nodes = aStar.Compute(new AStar.Node(((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger(),((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger()),
                new AStar.Node(dest.getXAsInteger(),dest.getYAsInteger()));

        if (nodes == null) {
            return IFuture.DONE;
        } else {
            nodes.remove(0);
            properties.put("path",nodes);
        }

        Future future = new Future();
        DelegationResultListener listener = new DelegationResultListener(future,true);

        Object mtaskid = env.createObjectTask("move",properties,myself.getId());
        env.addTaskListener(mtaskid,myself.getId(),listener);
        future.get();

        return IFuture.DONE;
    }
}