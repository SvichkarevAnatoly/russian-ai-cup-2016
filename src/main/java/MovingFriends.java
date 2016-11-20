import model.Building;
import model.Faction;
import model.LivingUnit;
import model.World;

import java.util.ArrayList;
import java.util.List;

public class MovingFriends extends Friends {
    protected List<LivingUnit> movingFriends = new ArrayList<>();

    public MovingFriends(World world, Faction friendFaction) {
        super(world, friendFaction);
        for (LivingUnit friend : friends) {
            if (!(friend instanceof Building)) {
                movingFriends.add(friend);
            }
        }
    }
}
