package map;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector2Double;
import jadex.extension.envsupport.math.Vector2Int;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Jo�o on 11/11/2015.
 */
public class EvacScene {

    // The Scene
    private Space2D scene;

    // Objects of interest
    private List<Vector2Int> walls;
    private List<Vector2Int> exits;

    public enum CellType {
        Blank,
        Wall,
        Exit
    }

    public CellType[][] map;

    private int width, height;

    public EvacScene(String filename, Space2D space) {

        this.scene = space;

        walls = new ArrayList<>();
        exits = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String currentLine;
            int line = 0;
            int col = 0;

            while ((currentLine = br.readLine()) != null) {
                col = 0;

                for (char c:currentLine.toCharArray()) {
                    if (c == 'X') {
                        walls.add(new Vector2Int(col,line));
                    } else if (c == 'E') {
                        exits.add(new Vector2Int(col,line));
                    }
                    col++;
                }
                line ++;

            }

            this.width = col;
            this.height = line;

            map = new CellType[height][width];

            for (int i = 0; i < height; i++) {
                Arrays.fill(map[i],CellType.Blank);
            }

            for(Vector2Int vec : walls) {
                map[vec.getYAsInteger()][vec.getXAsInteger()] = CellType.Wall;
            }

            for(Vector2Int vec : exits) {
                map[vec.getYAsInteger()][vec.getXAsInteger()] = CellType.Exit;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void instantiateObjects() {
        for(Vector2Int vec : walls) {
            Map wallProperties = new HashMap();
            wallProperties.put("position",vec);
            wallProperties.put("type",0);
            scene.createSpaceObject("terrain",wallProperties,null);
        }

        for(Vector2Int vec : exits) {
            Map wallProperties = new HashMap();
            wallProperties.put("position",vec);
            wallProperties.put("type",1);
            scene.createSpaceObject("terrain",wallProperties,null);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public CellType getCell(int x, int y) {
        return map[y][x];
    }

    public void setCell(int x, int y, CellType type) {
        map[y][x] = type;

        if (type == CellType.Wall) {
            System.out.println("Created wall at (" + x + "," + y + ")");
            Map objectProperties = new HashMap();
            objectProperties.put("position", new Vector2Int(x, y));
            objectProperties.put("type", 0);
            scene.createSpaceObject("terrain", objectProperties, null);
        }

        else if (type == CellType.Blank) {
            ISpaceObject[] list = scene.getSpaceObjectsByType("terrain");
            IVector2 pos = new Vector2Int(x,y);

            for (ISpaceObject object : list) {
                if ( object.getProperty("position").equals(pos)) {
                    System.out.println("Destroyed wall at (" + x + "," + y + ")");
                    scene.destroySpaceObject(object.getId());
                    break;
                }
            }
        }


    }
}
