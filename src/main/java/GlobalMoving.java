import model.LaneType;
import model.Wizard;

// Singleton
public class GlobalMoving {
    private static GlobalMoving instance = new GlobalMoving();

    public static GlobalMoving getInstance() {
        return instance;
    }

    private WaypointsByLane waypointsByLane;
    private int pastWaypointIndex = -1;

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
     * Дополнительно проверяем, не находится ли волшебник достаточно близко к какой-либо из ключевых точек. Если это
     * так, то мы сразу возвращаем следующую ключевую точку.
     */
    public Point getNextWaypoint(Wizard self) {
        final Point[] waypoints = waypointsByLane.get(currentLane);
        int minDistIndex = waypoints.length - 1;
        double minDist = waypoints[minDistIndex].getDistanceTo(self);
        for (int i = waypoints.length - 2; i > pastWaypointIndex; --i) {
            Point waypoint = waypoints[i];

            if (waypoint.getDistanceTo(self) <= Const.WAYPOINT_RADIUS) {
                pastWaypointIndex = i;
                return waypoints[i + 1];
            }

            if (waypoint.getDistanceTo(self) <= minDist) {
                minDist = waypoint.getDistanceTo(self);
                minDistIndex = i;
            }
        }

        return waypoints[minDistIndex];
    }

    /**
     * Действие данного метода абсолютно идентично действию метода {@code getNextWaypoint}, если перевернуть массив
     * {@code waypoints}.
     */
    public Point getPreviousWaypoint(Wizard self) {
        final Point[] waypoints = waypointsByLane.get(currentLane);
        int minDistIndex = 0;
        double minDist = waypoints[minDistIndex].getDistanceTo(self);

        for (int i = 1; i <= pastWaypointIndex; i++) {
            Point waypoint = waypoints[i];

            if (waypoint.getDistanceTo(self) <= Const.WAYPOINT_RADIUS) {
                pastWaypointIndex = i - 1;
                return waypoints[i - 1];
            }

            if (waypoint.getDistanceTo(self) <= minDist) {
                minDist = waypoint.getDistanceTo(self);
                minDistIndex = i;
            }
        }

        return waypoints[minDistIndex];
    }

    public Point getLaneForkPoint(Wizard self) {
        if (nextLane != null && pastWaypointIndex + 1 == waypointsByLane.getLaneForkPointIndex()) {
            currentLane = nextLane;
            nextLane = null;
        }
        return getPreviousWaypoint(self);
    }

    public boolean hasNextLane() {
        return nextLane != null;
    }

    public void resetWaypoint() {
        pastWaypointIndex = -1;
    }

    public void setPastWaypointToNearest(Point point) {
        final Point[] waypoints = waypointsByLane.get(currentLane);
        int minDistIndex = waypoints.length - 1;
        double minDist = waypoints[minDistIndex].getDistanceTo(point);
        for (int i = waypoints.length - 2; i > pastWaypointIndex; --i) {
            Point waypoint = waypoints[i];
            if (waypoint.getDistanceTo(point) <= minDist) {
                minDist = waypoint.getDistanceTo(point);
                minDistIndex = i;
            }
        }
        pastWaypointIndex = minDistIndex - 1;
    }
}
