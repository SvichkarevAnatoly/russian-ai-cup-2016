import model.*;

import java.util.ArrayList;
import java.util.List;

public class EnemyTowersInRange extends EnemiesInRange {
    protected List<Building> towers = new ArrayList<>();

    public EnemyTowersInRange(World world, Faction enemyFaction, Wizard self) {
        super(world, enemyFaction, self);
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

    public boolean isEmpty() {
        return towers.isEmpty();
    }
}
