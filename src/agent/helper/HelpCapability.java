package agent.helper;

import jadex.bdiv3.annotation.*;
import jadex.bdiv3.runtime.ChangeEvent;
import jadex.bdiv3.runtime.ICapability;
import jadex.commons.future.IFuture;
import jadex.extension.envsupport.environment.AbstractEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.micro.annotation.Agent;
import utils.Utils;

import java.util.List;

/**
 * Created by joao on 03-12-2015.
 */
@Capability
@Plans({
//        @Plan(
//                trigger = @Trigger
//                        (goals = HelpCapability.HelpGoal.class)
//                ,
//                body = @Body
//                        (HelpPlan.class)
//        )
//        @Plan(
//                trigger = @Trigger (
//                        factaddeds = "injured"
//                ),
//                body = @Body (
//                        HelpPlan.class
//                )
//        )
})
public class HelpCapability {

    @Agent
    protected ICapability kappa;

    @Belief
    protected AbstractEnvironmentSpace env = (AbstractEnvironmentSpace) kappa.getAgent().getParentAccess().getExtension("2dspace").get();


    protected ISpaceObject myself = env.getAvatar(kappa.getAgent().getComponentDescription(), kappa.getAgent().getModel().getFullName());

//    @Belief
//    protected ISpaceObject injuredAgent = (ISpaceObject) myself.getProperty("injuredAgent");

    @Belief
    protected List<ISpaceObject> injured = Utils.injuredAgents;

    
    public ICapability getCapability() {
        return this.kappa;
    }

    public ISpaceObject getMyself() {
        return myself;
    }

    @Plan (trigger = @Trigger(factaddeds = "injured"))
    public IFuture<Void> body() {

        System.out.println("Triggered HelpPlan!");

        if (this.injured.size() != 0) {
            System.out.println("Helping " + this.myself.getProperty("injuredAgent"));
        }

        return IFuture.DONE;
    }
}

