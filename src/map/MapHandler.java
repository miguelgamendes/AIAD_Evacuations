package map;

import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.SimplePropertyObject;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceProcess;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.Vector2Int;
import jadex.extension.envsupport.observer.gui.ObserverCenter;
import utils.Utils;

import java.util.Random;


/**
 * Created by Jo�o on 11/11/2015.
 */
public class MapHandler extends SimplePropertyObject implements ISpaceProcess {

    private Random rand = new Random();

    // Evacuation scene
    EvacScene evacScene;

    // Probability of a downfall
    private double downfall_prob;

    @Override
    public void start(IClockService iClockService, IEnvironmentSpace iEnvironmentSpace) {


        Space2D space2D = (Space2D) iEnvironmentSpace;

        evacScene = new EvacScene("resources/maps/map_"+getProperty("map_number")+".txt", (Space2D) iEnvironmentSpace);

        space2D.setAreaSize(new Vector2Int(evacScene.getWidth(),evacScene.getHeight()));

        evacScene.instantiateObjects();

        Utils.scene = evacScene;

        downfall_prob = (double) getProperty("downfall_prob");
    }

    @Override
    public void shutdown(IEnvironmentSpace iEnvironmentSpace) {

    }

    @Override
    public void execute(IClockService iClockService, IEnvironmentSpace iEnvironmentSpace) {
        float prob = rand.nextFloat();

        if (prob < downfall_prob) {
            Space2D space = (Space2D) iEnvironmentSpace;
            Vector2Int pos;

            do {
                pos = (Vector2Int) space.getRandomPosition(null);
            } while (pos.getXAsInteger() == 0 || pos.getXAsInteger() == evacScene.getWidth() - 1 || pos.getYAsInteger() == 0 || pos.getYAsInteger() == evacScene.getHeight() - 1 );


            EvacScene.CellType type = evacScene.getCell(pos.getXAsInteger(), pos.getYAsInteger());

//            System.out.println("Downfall at (" + pos.getXAsInteger() + "," + pos.getYAsInteger() + ")\nType = " + type );

            evacScene.addDebris(pos.getXAsInteger(), pos.getYAsInteger());

        }
    }
}
