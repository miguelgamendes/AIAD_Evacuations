package movement;

import jadex.bdiv3.annotation.*;
import jadex.bdiv3.runtime.ICapability;
import jadex.extension.envsupport.environment.AbstractEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.micro.annotation.Agent;

/**
 * Created by Jo�o on 14/11/2015.
 */

@Capability
@Plans ({
        @Plan(
                trigger = @Trigger
                        (goals = MovementCapability.Move.class)
                ,
                body = @Body
                        (MoveToLocationPlan.class)
        ),
        @Plan(
                trigger=@Trigger
                        (goals = MovementCapability.WalkAround.class)
                ,
                body=@Body
                        (RandomWalkPlan.class)
        )
})
public class MovementCapability {

    @Agent
    protected ICapability kappa;

    @Belief
    protected AbstractEnvironmentSpace env = (AbstractEnvironmentSpace) kappa.getAgent().getParentAccess().getExtension("2dspace").get();

    protected ISpaceObject myself = env.getAvatar(kappa.getAgent().getComponentDescription(), kappa.getAgent().getModel().getFullName());

    @Goal (excludemode= Goal.ExcludeMode.Never)
    public class WalkAround {
        public WalkAround() {

        }
    }

    @Goal
    public class Move implements IDestinationGoal {

        protected Object destination;

        public Move(Object destination) {
            this.destination = destination;
        }

        public Object getDestination() {
            return destination;
        }
    }

    public AbstractEnvironmentSpace getEnv() {
        return env;
    }

    public ISpaceObject getMyself() {
        return myself;
    }

    public ICapability getCapability() {
        return this.kappa;
    }
}
