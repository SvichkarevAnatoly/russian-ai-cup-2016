import model.Faction;
import model.LivingUnit;
import model.World;

import java.util.ArrayList;
import java.util.List;

public class Friends extends LivingUnits {
    protected List<LivingUnit> friends = new ArrayList<>();

    public Friends(World world, Faction friendFaction) {
        super(world);
        for (LivingUnit unit : units) {
            if (unit.getFaction() == friendFaction) {
                friends.add(unit);
            }
        }
    }
}
