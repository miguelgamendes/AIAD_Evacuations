package map;

import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.SimplePropertyObject;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceProcess;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.Vector2Int;
import jadex.extension.envsupport.observer.gui.ObserverCenter;
import utils.Utils;


/**
 * Created by Jo�o on 11/11/2015.
 */
public class MapGenerator extends SimplePropertyObject implements ISpaceProcess {

    @Override
    public void start(IClockService iClockService, IEnvironmentSpace iEnvironmentSpace) {


        Space2D space2D = (Space2D) iEnvironmentSpace;

        EvacScene evacScene = new EvacScene("resources/maps/map_"+getProperty("map_number")+".txt");

        space2D.setAreaSize(new Vector2Int(evacScene.getWidth(),evacScene.getHeight()));

        evacScene.instantiateObjects(space2D);

        Utils.scene = evacScene;
    }

    @Override
    public void shutdown(IEnvironmentSpace iEnvironmentSpace) {

    }

    @Override
    public void execute(IClockService iClockService, IEnvironmentSpace iEnvironmentSpace) {

    }
}
