import model.LivingUnit;
import model.Wizard;
import model.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LivingUnits {
    protected List<LivingUnit> units = new ArrayList<>();

    public LivingUnits(World world) {
        units.addAll(Arrays.asList(world.getBuildings()));
        units.addAll(Arrays.asList(world.getWizards()));
        units.addAll(Arrays.asList(world.getMinions()));
    }

    public LivingUnit getNearest(Wizard self) {
        LivingUnit nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (LivingUnit unit : units) {
            double distance = self.getDistanceTo(unit);

            if (distance < nearestDistance) {
                nearest = unit;
                nearestDistance = distance;
            }
        }
        return nearest;
    }
}
