import model.Building;
import model.BuildingType;
import model.Wizard;
import model.World;

public class EnemyMiddleTower {
    public static final Point[] towers =
            new Point[]{new Point(2070.7, 1600),
            new Point(3097.4, 1231.9),
            new Point(3600, 400)};
    public static final boolean[] isAlive = new boolean[]{true, true, true};
    public static final double[] towerAttackRange = new double[]{600.0D, 600.0D, 800.0D};

    // TODO: нужно как-то отмечать уничтоженные башни
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

    public static boolean isInRangeBase(Wizard self, World world) {
        final Params params = new Params(self);
        final Building[] buildings = world.getBuildings();
        for (Building building : buildings) {
            if (building.getType() == BuildingType.FACTION_BASE
                    && building.getFaction() == params.enemy) {
                final double enemyBaseLife = GameSingleton.getInstance().getFactionBaseLife();
                if (building.getLife() < Const.LOW_HP_ENEMY_BASE_FACTOR * enemyBaseLife){
                    return false;
                }
            }
        }
        return towers[2].getDistanceTo(self) <= Const.WARNING_DIST_TO_ENEMY_BASE;
    }
}
