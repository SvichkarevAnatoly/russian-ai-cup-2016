import model.Faction;
import model.LivingUnit;
import model.Wizard;
import model.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnemyAnalysis {
    private Wizard self;
    private World world;

    public EnemyAnalysis(Wizard self, World world) {
        this.self = self;
        this.world = world;
    }

    /**
     * Находим ближайшую цель для атаки, независимо от её типа и других характеристик.
     */
    public LivingUnit getNearestEnemy() {
        List<LivingUnit> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getBuildings()));
        targets.addAll(Arrays.asList(world.getWizards()));
        targets.addAll(Arrays.asList(world.getMinions()));

        LivingUnit nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;

        for (LivingUnit target : targets) {
            if (target.getFaction() == Faction.NEUTRAL || target.getFaction() == self.getFaction()) {
                continue;
            }

            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }

        return nearestTarget;
    }
}
