import model.Wizard;

import java.util.Arrays;
import java.util.List;

public class EnemyMiddleTower {
    public static final List<Point> towers =
            Arrays.asList(new Point(2070.7, 1600));
    public static final double towerAttackRange = 600.0D;

    public static boolean isInRange(Wizard self) {
        for (Point tower : towers) {
            if (tower.getDistanceTo(self) <= towerAttackRange + Const.WARNING_DIST) {
                return true;
            }
        }
        return false;
    }
}
