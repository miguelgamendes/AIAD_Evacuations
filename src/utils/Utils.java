package utils;

import jadex.extension.envsupport.environment.ISpaceObject;
import map.EvacScene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by joao on 21-11-2015.
 */

public class Utils {

    public static EvacScene scene;

    public static Random rand = new Random();

    public static List<ISpaceObject> injuredAgents = new ArrayList<>();
}
