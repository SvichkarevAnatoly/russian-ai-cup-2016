import model.*;

import java.util.ArrayList;
import java.util.List;

public class EnemiesInRange extends Enemies {
    protected List<LivingUnit> enemiesInRange = new ArrayList<>();

    public EnemiesInRange(World world, Faction enemyFaction, Wizard self) {
        super(world, enemyFaction);
        for (LivingUnit enemy : enemies) {
            double distance = self.getDistanceTo(enemy);
            if (distance <= self.getCastRange()) {
                enemiesInRange.add(enemy);
            }
        }
    }
}
