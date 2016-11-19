import model.Faction;
import model.LivingUnit;
import model.Wizard;
import model.World;

import java.util.ArrayList;
import java.util.List;

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
}
