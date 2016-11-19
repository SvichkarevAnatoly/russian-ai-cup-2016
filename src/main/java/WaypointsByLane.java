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
    private Map<LaneType, Point2D[]> waypointsByLane;

    public WaypointsByLane(double mapSize) {
        waypointsByLane = new EnumMap<>(LaneType.class);

        waypointsByLane.put(LaneType.MIDDLE, new Point2D[]{
                new Point2D(100.0D, mapSize - 100.0D),
                new Point2D(200.0D, mapSize - 600.0D),
                new Point2D(800.0D, mapSize - 800.0D),
                new Point2D(mapSize - 600.0D, 600.0D)
        });

        waypointsByLane.put(LaneType.TOP, new Point2D[]{
                new Point2D(100.0D, mapSize - 100.0D),
                new Point2D(100.0D, mapSize - 400.0D),
                new Point2D(200.0D, mapSize - 800.0D),
                new Point2D(200.0D, mapSize * 0.75D),
                new Point2D(200.0D, mapSize * 0.5D),
                new Point2D(200.0D, mapSize * 0.25D),
                new Point2D(200.0D, 200.0D),
                new Point2D(mapSize * 0.25D, 200.0D),
                new Point2D(mapSize * 0.5D, 200.0D),
                new Point2D(mapSize * 0.75D, 200.0D),
                new Point2D(mapSize - 200.0D, 200.0D)
        });

        waypointsByLane.put(LaneType.BOTTOM, new Point2D[]{
                new Point2D(100.0D, mapSize - 100.0D),
                new Point2D(400.0D, mapSize - 100.0D),
                new Point2D(800.0D, mapSize - 200.0D),
                new Point2D(mapSize * 0.25D, mapSize - 200.0D),
                new Point2D(mapSize * 0.5D, mapSize - 200.0D),
                new Point2D(mapSize * 0.75D, mapSize - 200.0D),
                new Point2D(mapSize - 200.0D, mapSize - 200.0D),
                new Point2D(mapSize - 200.0D, mapSize * 0.75D),
                new Point2D(mapSize - 200.0D, mapSize * 0.5D),
                new Point2D(mapSize - 200.0D, mapSize * 0.25D),
                new Point2D(mapSize - 200.0D, 200.0D)
        });
    }

    public Point2D[] get(LaneType laneType) {
        return waypointsByLane.get(laneType);
    }
}
