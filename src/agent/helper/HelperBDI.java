package agent.helper;

import agent.BaseBDI;
import jadex.bdiv3.annotation.Capability;
import jadex.commons.future.DelegationResultListener;
import jadex.commons.future.Future;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joao on 03-12-2015.
 */
@Agent
public class HelperBDI extends BaseBDI {

    @Capability
    protected HelpCapability helpCapability = new HelpCapability();

    @AgentBody
    public void body() {

        Future future = new Future();
        DelegationResultListener listener = new DelegationResultListener(future,true);

        //      Task Properties
        Map properties = new HashMap();

        Object mtaskid = helpCapability.env.createObjectTask("look",properties,helpCapability.getMyself().getId());
        helpCapability.env.addTaskListener(mtaskid,helpCapability.getMyself().getId(),listener);

        agent.dispatchTopLevelGoal(movementCapability.new WalkAround());

        future.get();
    }

}
