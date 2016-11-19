import model.*;

import java.util.ArrayList;
import java.util.List;

public class EnemyTowers extends Enemies {
    protected List<Building> towers = new ArrayList<>();

    public EnemyTowers(World world, Faction enemyFaction) {
        super(world, enemyFaction);
        for (LivingUnit enemy : enemies) {
            if (enemy instanceof Building) {
                towers.add((Building) enemy);
            }
        }
    }

    @Override
    public LivingUnit getNearest(Wizard self) {
        LivingUnit nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (LivingUnit tower : towers) {
            double distance = self.getDistanceTo(tower);

            if (distance < nearestDistance) {
                nearest = tower;
                nearestDistance = distance;
            }
        }
        return nearest;
    }

    public boolean isInRange(Wizard self) {
        for (Building tower : towers) {
            if (self.getDistanceTo(tower) <= tower.getAttackRange() + Const.WARNING_DIST) {
                return true;
            }
        }
        return false;
    }
}
