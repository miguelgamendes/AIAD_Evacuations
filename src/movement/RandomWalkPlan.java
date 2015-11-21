package movement;

import jadex.bdiv3.annotation.*;
import jadex.bdiv3.runtime.IPlan;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Int;
import map.EvacScene;
import utils.Utils;

/**
 * Created by Jo�o on 16/11/2015.
 */

@Plan
public class RandomWalkPlan {

    @PlanCapability
    protected MovementCapability capa;

    @PlanAPI
    protected IPlan rplan;

    @PlanReason
    protected MovementCapability.WalkAround goal;

    public RandomWalkPlan() {
    }

    @PlanBody
    public void body() {
        while (true) {
            IVector2 pos = (IVector2) capa.getMyself().getProperty("position");
            IVector2 dest;
            do {
                dest = ((Space2D) capa.getEnv()).getRandomPosition(Vector2Int.ZERO);
            } while (!dest.equals(pos) && Utils.scene.getCell(dest.getXAsInteger(), dest.getYAsInteger()) != EvacScene.CellType.Blank);
            MovementCapability.Move move = capa.new Move(dest);
            rplan.dispatchSubgoal(move).get();
        }
    }

}
