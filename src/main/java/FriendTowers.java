import model.Building;
import model.LaneType;
import model.World;

import java.util.*;

public class FriendTowers {
    private static final double DIST_EPS = 0.01D;

    private final List<Building> towers;

    private static final Map<LaneType, List<Point>> initTowersOnLane;
    static {
        Map<LaneType, List<Point>> aMap = new EnumMap<>(LaneType.class);
        aMap.put(LaneType.BOTTOM, Arrays.asList(
                new Point(1370.6603203516029, 3650.0),
                new Point(2312.1259974228437, 3950.0)
        ));
        aMap.put(LaneType.MIDDLE, Arrays.asList(
                new Point(902.6130586671778, 2768.0976194514765),
                new Point(1929.2893218813454, 2400.0)
        ));
        aMap.put(LaneType.TOP, Arrays.asList(
                new Point(50.0, 2693.2577778083373),
                new Point(350.0, 1656.7486446626867)
        ));
        initTowersOnLane = Collections.unmodifiableMap(aMap);
    }

    public FriendTowers(World world) {
        this.towers = Arrays.asList(world.getBuildings());
    }

    public int countOnLane(LaneType lane) {
        final List<Point> laneTowers = initTowersOnLane.get(lane);
        int counter = 0;
        for (Building tower : towers) {
            for (Point laneTower : laneTowers) {
                if (laneTower.getDistanceTo(tower) <= DIST_EPS) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
