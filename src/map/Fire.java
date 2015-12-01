package map;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector1;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.extension.envsupport.math.Vector2Int;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Miguel Mendes on the day fire was invented.
 */
public class Fire {

    private Space2D scene;

    private double prob;

    private double spread_rate;

    private Random rand = new Random();

    public Fire(Space2D space, double spread_rate) {
        scene = space;

        Map properties = new HashMap();
        Vector2Double vec = new Vector2Double(1.d, 1.d);
        properties.put("position", vec);
        properties.put("type", "fire");
        scene.createSpaceObject("hazard", properties, null);

        this.spread_rate = spread_rate;
    }

    //Calculates the current spread directions for the fire
    void spread() {
        ISpaceObject[] allFire = scene.getSpaceObjectsByType("hazard");

        for (ISpaceObject fire : allFire) {
            prob = rand.nextDouble();

            if(prob > spread_rate) {
                continue;
            }

            Vector2Double currentPos = (Vector2Double) fire.getProperty("position");

            int up = rand.nextInt(2);
            int down = rand.nextInt(2);
            int left = rand.nextInt(2);
            int right = rand.nextInt(2);

            boolean permitUp = true;
            boolean permitDown = true;
            boolean permitLeft = true;
            boolean permitRight = true;

            //disable directions to prevent creation of unecessary flames
            for(ISpaceObject flame : allFire){
                Vector2Double otherPos = (Vector2Double) flame.getProperty("position");
                if(currentPos.getXAsDouble() == otherPos.getXAsDouble() && currentPos.getYAsDouble() + 1.d == otherPos.getYAsDouble())
                    permitUp = false;
                if(currentPos.getXAsDouble() == otherPos.getXAsDouble() && currentPos.getYAsDouble() - 1.d == otherPos.getYAsDouble())
                    permitDown = false;
                if(currentPos.getXAsDouble() - 1.d == otherPos.getXAsDouble() && currentPos.getYAsDouble() == otherPos.getYAsDouble())
                    permitLeft = false;
                if(currentPos.getXAsDouble() + 1.d == otherPos.getXAsDouble() && currentPos.getYAsDouble() == otherPos.getYAsDouble())
                    permitRight = false;
            }

            if(up == 1 && (currentPos.getYAsDouble() + 1.d) < scene.getAreaSize().getYAsDouble() && permitUp) {
                Map properties = new HashMap();
                Vector2Double vec = new Vector2Double(currentPos.getXAsDouble(), currentPos.getYAsDouble() + 1.d);
                properties.put("position", vec);
                properties.put("type", "fire");
                scene.createSpaceObject("hazard", properties, null);
            } if(down == 1 && (currentPos.getYAsDouble() - 1.d) >= 0 && permitDown) {
                Map properties = new HashMap();
                Vector2Double vec = new Vector2Double(currentPos.getXAsDouble(), currentPos.getYAsDouble() - 1.d);
                properties.put("position", vec);
                properties.put("type", "fire");
                scene.createSpaceObject("hazard", properties, null);
            } if(left == 1 && (currentPos.getXAsDouble() - 1.d) >= 0 && permitLeft) {
                Map properties = new HashMap();
                Vector2Double vec = new Vector2Double(currentPos.getXAsDouble() - 1.d, currentPos.getYAsDouble());
                properties.put("position", vec);
                properties.put("type", "fire");
                scene.createSpaceObject("hazard", properties, null);
            } if(right == 1 && (currentPos.getXAsDouble() + 1.d) < scene.getAreaSize().getXAsInteger() && permitRight) {
                Map properties = new HashMap();
                Vector2Double vec = new Vector2Double(currentPos.getXAsDouble() + 1.d, currentPos.getYAsDouble());
                properties.put("position", vec);
                properties.put("type", "fire");
                scene.createSpaceObject("hazard", properties, null);
            }
        }
    }

    //update to avoid walls and debris
    void update() {
        ISpaceObject[] terrain = scene.getSpaceObjectsByType("terrain");
        ISpaceObject[] fire = scene.getSpaceObjectsByType("hazard");

        for(ISpaceObject flame : fire) {
            Vector2Double flamePos = (Vector2Double) flame.getProperty("position");

            for(ISpaceObject block : terrain) {
                Vector2Double blockPos = (Vector2Double) block.getProperty("position");

                if(flamePos.getXAsDouble() == blockPos.getXAsDouble() && flamePos.getYAsDouble() == blockPos.getYAsDouble()) {
                    scene.destroySpaceObject(flame.getId());
                }
            }
        }
    }
}
