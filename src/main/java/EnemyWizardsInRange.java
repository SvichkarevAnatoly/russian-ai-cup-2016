import model.Faction;
import model.LivingUnit;
import model.Wizard;
import model.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EnemyWizardsInRange extends EnemiesInRange {
    protected List<LivingUnit> wizards = new ArrayList<>();

    public EnemyWizardsInRange(World world, Faction enemyFaction, Wizard self) {
        super(world, enemyFaction, self);
        for (LivingUnit enemy : enemiesInRange) {
            if (enemy instanceof Wizard) {
                wizards.add(enemy);
            }
        }
    }

    public void sortMostInjured() {
        Collections.sort(wizards, new Comparator<LivingUnit>() {
            @Override
            public int compare(LivingUnit o1, LivingUnit o2) {
                final double o1rate = (double) o1.getLife() / o1.getMaxLife();
                final double o2rate = (double) o2.getLife() / o2.getMaxLife();
                return Double.compare(o1rate, o2rate);
            }
        });
    }

    public boolean isEmpty() {
        return wizards.isEmpty();
    }
}
