package movement;

import jadex.bridge.service.types.clock.IClockService;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.List;

/**
 * Created by Jo�o on 14/11/2015.
 */
public class MoveTask extends AbstractTask {
    //-------- constants --------

    /** The destination property. */
    public static final String PROPERTY_TYPENAME = "move";
    /** The destination property. */
    public static final String PROPERTY_DESTINATION = "destination";
    /** The scope property. */
    public static final String PROPERTY_SCOPE = "scope";
    /** The speed property of the moving object (units per second). */
    public static final String PROPERTY_SPEED = "speed";
    /** The vision property of the moving object (radius in units). */
    public static final String PROPERTY_VISION = "vision";

    private List<AStar.Node> path;

    private Vector2Double tmpLocation;


    @Override
    public void execute(IEnvironmentSpace space, ISpaceObject obj, long progress, IClockService clock) {


        path = (List<AStar.Node>) getProperty("path");

        IVector2 dest = (IVector2) getProperty(PROPERTY_DESTINATION);
        IVector2 iDest = new Vector2Int(path.get(0).x,path.get(0).y);

        double speed = ((Number) obj.getProperty(PROPERTY_SPEED)).doubleValue();
        double maxdist = progress * speed;


        IVector2 loc = (IVector2) obj.getProperty(Space2D.PROPERTY_POSITION);

        IVector2 newloc = ((Space2D) space).getDistance(loc, iDest).getAsDouble() <= maxdist
                ? iDest :
                iDest.copy().subtract(loc).normalize().multiply(maxdist).add(loc);

        ((Space2D) space).setPosition(obj.getId(), newloc);

        if (newloc.equals(dest)) {
            setFinished(space,obj,true);

        } else if (newloc == iDest) {
            path.remove(0);
        }


    }
}
