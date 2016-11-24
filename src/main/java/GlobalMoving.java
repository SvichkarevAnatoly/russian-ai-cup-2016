import model.LaneType;
import model.Wizard;

// Singleton
public class GlobalMoving {
    private static GlobalMoving instance = new GlobalMoving();

    public static GlobalMoving getInstance() {
        return instance;
    }

    private WaypointsByLane waypointsByLane;
    private LaneType currentLane;
    private LaneType nextLane;

    private GlobalMoving() {
        this.currentLane = LaneType.MIDDLE;
        waypointsByLane = new WaypointsByLane();
    }

    public LaneType getLane() {
        return currentLane;
    }

    public void switchLane(LaneType newLane) {
        nextLane = newLane;
    }

    /**
     * Данный метод предполагает, что все ключевые точки на линии упорядочены по уменьшению дистанции до последней
     * ключевой точки. Перебирая их по порядку, находим первую попавшуюся точку, которая находится ближе к последней
     * точке на линии, чем волшебник. Это и будет следующей ключевой точкой.
     * <p>
     * Дополнительно проверяем, не находится ли волшебник достаточно близко к какой-либо из ключевых точек. Если это
     * так, то мы сразу возвращаем следующую ключевую точку.
     */
    public Point getNextWaypoint(Wizard self) {
        if (nextLane != null) {
            final Point laneForkPoint = waypointsByLane.getLaneForkPoint();
            if (laneForkPoint.getDistanceTo(self) <= Const.WAYPOINT_RADIUS) {
                currentLane = nextLane;
                nextLane = null;
            }
            return laneForkPoint;
        } else {
            final Point[] waypoints = waypointsByLane.get(currentLane);
            int lastWaypointIndex = waypoints.length - 1;
            Point lastWaypoint = waypoints[lastWaypointIndex];

            for (int waypointIndex = 0; waypointIndex < lastWaypointIndex; ++waypointIndex) {
                Point waypoint = waypoints[waypointIndex];

                if (waypoint.getDistanceTo(self) <= Const.WAYPOINT_RADIUS) {
                    return waypoints[waypointIndex + 1];
                }

                if (lastWaypoint.getDistanceTo(waypoint) < lastWaypoint.getDistanceTo(self)) {
                    return waypoint;
                }
            }

            return lastWaypoint;
        }
    }

    /**
     * Действие данного метода абсолютно идентично действию метода {@code getNextWaypoint}, если перевернуть массив
     * {@code waypoints}.
     */
    public Point getPreviousWaypoint(Wizard self) {
        final Point[] waypoints = waypointsByLane.get(currentLane);
        Point firstWaypoint = waypoints[0];

        for (int waypointIndex = waypoints.length - 1; waypointIndex > 0; --waypointIndex) {
            Point waypoint = waypoints[waypointIndex];

            if (waypoint.getDistanceTo(self) <= Const.WAYPOINT_RADIUS) {
                return waypoints[waypointIndex - 1];
            }

            if (firstWaypoint.getDistanceTo(waypoint) < firstWaypoint.getDistanceTo(self)) {
                return waypoint;
            }
        }

        return firstWaypoint;
    }

    public Point getLaneForkPoint(Wizard self) {
        final Point previousWaypoint = getPreviousWaypoint(self);
        final Point laneForkPoint = waypointsByLane.getLaneForkPoint();
        if (previousWaypoint.getDistanceTo(self) < laneForkPoint.getDistanceTo(self)) {
            return previousWaypoint;
        } else {
            return laneForkPoint;
        }
    }

    public boolean hasNextLane() {
        return nextLane != null;
    }
}
