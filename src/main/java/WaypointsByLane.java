import model.LaneType;

import java.util.EnumMap;
import java.util.Map;

/**
 * Ключевые точки для каждой линии, позволяющие упростить управление перемещением волшебника.
 * <p>
 * Если всё хорошо, двигаемся к следующей точке и атакуем противников.
 * Если осталось мало жизненной энергии, отступаем к предыдущей точке.
 */
public class WaypointsByLane {
    private static final int LANE_FORK_POINT_INDEX = 4;

    private Map<LaneType, Point[]> waypointsByLane;

    public WaypointsByLane() {
        final double mapSize = GameSingleton.getInstance().getMapSize();
        waypointsByLane = new EnumMap<>(LaneType.class);

        waypointsByLane.put(LaneType.MIDDLE, new Point[]{
                new Point(50.0D, M.y(50.0D)),
                new Point(200, M.y(400)),
                new Point(250, M.y(600)),
                new Point(400, M.y(250)),
                new Point(550, M.y(550)),
                new Point(990, M.y(1180.0D)),
                new Point(4.45 * 400.0D, M.y(3.95 * 400.0D)),
                new Point(4.6 * 400.0D, M.y(4.25 * 400.0D)),
                new Point(5 * 400.0D, M.y(5 * 400.0D)),
                new Point(6 * 400.0D, M.y(6 * 400.0D)),
                new Point(9.5 * 400.0D, M.y(9.5 * 400.0D)),
        });

        waypointsByLane.put(LaneType.TOP, new Point[]{
                new Point(50.0D, M.y(50.0D)),
                new Point(100.0D, M.y(100.0D)),
                new Point(400.0D, M.y(230.0D)),
                new Point(600.0D, M.y(300.0D)),
                new Point(550, M.y(550)),
                new Point(300, M.y(700)),
                new Point(200.0D, mapSize * 0.75D),
                new Point(200.0D, mapSize * 0.5D),
                new Point(200.0D, mapSize * 0.25D),
                new Point(mapSize * 0.25D, 200.0D),
                new Point(mapSize * 0.5D, 200.0D),
                new Point(mapSize * 0.75D, 200.0D),
                new Point(mapSize - 200.0D, 200.0D)
        });

        waypointsByLane.put(LaneType.BOTTOM, new Point[]{
                new Point(50.0D, M.y(50.0D)),
                new Point(100.0D, M.y(100.0D)),
                new Point(230.0D, M.y(400.0D)),
                new Point(300.0D, M.y(600.0D)),
                new Point(550, M.y(550)),
                new Point(700, M.y(300)),
                new Point(mapSize * 0.25D, M.y(200.0D)),
                new Point(mapSize * 0.5D, M.y(200.0D)),
                new Point(mapSize * 0.75D, M.y(200.0D)),
                new Point(mapSize - 200.0D, mapSize * 0.75D),
                new Point(mapSize - 200.0D, mapSize * 0.5D),
                new Point(mapSize - 200.0D, mapSize * 0.25D),
                new Point(mapSize - 200.0D, 200.0D)
        });
    }

    public Point[] get(LaneType laneType) {
        return waypointsByLane.get(laneType);
    }

    public int getLaneForkPointIndex() {
        return LANE_FORK_POINT_INDEX;
    }
}
