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

    Vector2Double actualPos;

    @Override
    public void start(ISpaceObject obj) {
        actualPos = (Vector2Double) obj.getProperty(Space2D.PROPERTY_POSITION);
    }

    @Override
    public void execute(IEnvironmentSpace space, ISpaceObject obj, long progress, IClockService clock) {

//       TODO Verificar a cada passo se estamos por cima de debris
//       TODO Probabilidade de ficar ferido
//       TODO Enviar mensagem se ferido
//       TODO Não se poder mover se ferido

        path = (List<AStar.Node>) getProperty("path");

        IVector2 dest = (IVector2) getProperty(PROPERTY_DESTINATION);
        Vector2Double iDest = new Vector2Double(path.get(0).x,path.get(0).y);

        double speed = ((Number) obj.getProperty(PROPERTY_SPEED)).doubleValue();
        double maxdist = progress * speed / 1000;


        actualPos =
                (Vector2Double) (((Space2D) space).getDistance(actualPos, iDest).getAsDouble() <= maxdist
                        ? iDest :
                        iDest.copy().subtract(actualPos).normalize().multiply(maxdist).add(actualPos));



        ((Space2D) space).setPosition(obj.getId(), actualPos);

        if (actualPos.equals(dest)) {
            setFinished(space,obj,true);

        } else if (actualPos == iDest) {
            path.remove(0);
        }


    }
}