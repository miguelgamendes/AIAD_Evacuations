package agent.helper;

import jadex.bdiv3.annotation.*;
import jadex.bdiv3.runtime.ChangeEvent;
import jadex.bdiv3.runtime.IPlan;
import jadex.commons.future.IFuture;

/**
 * Created by joao on 03-12-2015.
 */
@Plan
public class HelpPlan {

    @PlanCapability
    protected HelpCapability capa;

    @PlanAPI
    protected IPlan rplan;

    @PlanBody
    public IFuture<Void> body(ChangeEvent event) {

        System.out.println("Triggered HelpPlan!\nEvent = " + event.getType());

//        if (capa.injuredAgent != null)
//            System.out.println("Helping " + capa.injuredAgent);

        if (capa.injured.size() != 0) {
            System.out.println("Helping " + capa.myself.getProperty("injuredAgent"));
        }

        return IFuture.DONE;
    }

}
