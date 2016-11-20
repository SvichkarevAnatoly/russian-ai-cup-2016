import model.*;

import java.util.ArrayList;
import java.util.List;

public class MinionFriends extends MovingFriends {
    protected List<LivingUnit> minionFriends = new ArrayList<>();

    public MinionFriends(World world, Faction friendFaction) {
        super(world, friendFaction);
        for (LivingUnit movingFriend : movingFriends) {
            if (movingFriend instanceof Minion) {
                minionFriends.add(movingFriend);
            }
        }
    }
}
