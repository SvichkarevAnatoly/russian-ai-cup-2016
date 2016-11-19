import model.Faction;
import model.LivingUnit;
import model.Wizard;
import model.World;

import java.util.*;

public class Enemies extends LivingUnits {
    protected List<LivingUnit> enemies = new ArrayList<>();

    public Enemies(World world, Faction enemy) {
        super(world);
        for (LivingUnit unit : units) {
            if (unit.getFaction() == enemy) {
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
