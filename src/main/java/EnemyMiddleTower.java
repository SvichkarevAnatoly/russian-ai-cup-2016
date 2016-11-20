import model.Wizard;

public class EnemyMiddleTower {
    public static final Point[] towers =
            new Point[]{new Point(2070.7, 1600),
            new Point(3097.4, 1231.9),
            new Point(3600, 400)};
    public static final boolean[] isAlive = new boolean[]{true, true, true};
    public static final double[] towerAttackRange = new double[]{600.0D, 600.0D, 800.0D};

    public static boolean isInRange(Wizard self) {
        for (int i = 0; i < towers.length; i++) {
            final Point tower = towers[i];
            if (isAlive[i]) {
                if (tower.getDistanceTo(self) <= towerAttackRange[i] + Const.WARNING_DIST) {
                    return true;
                }
            }
        }
        return false;
    }
}
