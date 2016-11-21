import model.Faction;
import model.LivingUnit;
import model.Wizard;
import model.World;

import java.util.*;

public class Enemies extends LivingUnits {
    protected List<LivingUnit> enemies = new ArrayList<>();

    public Enemies(World world, Faction enemyFaction) {
        super(world);
        for (LivingUnit unit : units) {
            if (unit.getFaction() == enemyFaction) {
                enemies.add(unit);
            }
        }
    }

    @Override
    public LivingUnit getNearest(Wizard self) {
        LivingUnit nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (LivingUnit enemy : enemies) {
            double distance = self.getDistanceTo(enemy);

            if (distance < nearestDistance) {
                nearest = enemy;
                nearestDistance = distance;
            }
        }
        return nearest;
    }

    public boolean isOrkAtWarningDistance(Wizard self) {
        final LivingUnit nearest = getNearest(self);
        if (nearest != null) {
            double distance = self.getDistanceTo(nearest);
            if (distance <= Const.DIST_ORC_CAN_ATTACK + Const.WARNING_DIST_TO_ORC) {
                return true;
            }
        }
        return false;
    }

    public void sortMostInjured() {
        Collections.sort(enemies, new Comparator<LivingUnit>() {
            @Override
            public int compare(LivingUnit o1, LivingUnit o2) {
                final double o1rate = (double) o1.getLife() / o1.getMaxLife();
                final double o2rate = (double) o2.getLife() / o2.getMaxLife();
                return Double.compare(o1rate, o2rate);
            }
        });
    }

    public LivingUnit getFirstInRange(Wizard self) {
        for (LivingUnit enemy : enemies) {
            double distance = self.getDistanceTo(enemy);
            if (distance <= self.getCastRange()) {
                return enemy;
            }
        }
        return null;
    }
}
