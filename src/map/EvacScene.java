package map;

import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.Vector2Int;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Jo�o on 11/11/2015.
 */
public class EvacScene {

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

    public EvacScene(String filename) {

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

    public void instantiateObjects(Space2D space) {
        for(Vector2Int vec : walls) {
            Map wallProperties = new HashMap();
            wallProperties.put("position",vec);
            wallProperties.put("type",0);
            space.createSpaceObject("terrain",wallProperties,null);
        }

        for(Vector2Int vec : exits) {
            Map wallProperties = new HashMap();
            wallProperties.put("position",vec);
            wallProperties.put("type",1);
            space.createSpaceObject("terrain",wallProperties,null);
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
}
