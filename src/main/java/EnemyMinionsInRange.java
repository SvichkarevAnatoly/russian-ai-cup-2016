import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EnemyMinionsInRange extends EnemiesInRange {
    protected List<LivingUnit> minions = new ArrayList<>();

    public EnemyMinionsInRange(World world, Faction enemyFaction, Wizard self) {
        super(world, enemyFaction, self);
        for (LivingUnit enemy : enemies) {
            if (enemy instanceof Minion) {
                minions.add(enemy);
            }
        }
    }

    public void sortMostInjured() {
        Collections.sort(minions, new Comparator<LivingUnit>() {
            @Override
            public int compare(LivingUnit o1, LivingUnit o2) {
                final double o1rate = (double) o1.getLife() / o1.getMaxLife();
                final double o2rate = (double) o2.getLife() / o2.getMaxLife();
                return Double.compare(o1rate, o2rate);
            }
        });
    }

    public LivingUnit getFirstInRange(Wizard self) {
        for (LivingUnit minion : minions) {
            double distance = self.getDistanceTo(minion);
            if (distance <= self.getCastRange()) {
                return minion;
            }
        }
        return null;
    }
}
