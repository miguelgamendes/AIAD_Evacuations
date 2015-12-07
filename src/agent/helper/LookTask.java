package agent.helper;

import jadex.bridge.service.types.clock.IClockService;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector1Int;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.Set;

/**
 * Created by joao on 03-12-2015.
 */
public class LookTask extends AbstractTask {



    @Override
    public void start(ISpaceObject obj) {
        System.out.println("Looking!");
    }

    @Override
    public void execute(IEnvironmentSpace space, ISpaceObject obj, long progress, IClockService clock) {
        Set<ISpaceObject> nearObjects = ((Space2D) space).getNearObjects((IVector2) obj.getProperty("position"), new Vector1Int(20));

        for (ISpaceObject object : nearObjects) {
            if (object.getType() .equals("wanderer") && (Boolean) object.getProperty("injured") && !(Boolean) object.getProperty("carried")) {
                //System.out.println(object + " needs my help!");
                obj.setProperty("injuredAgent",object);
            }

        }


    }
}
