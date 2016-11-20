import model.*;

import java.util.ArrayList;
import java.util.List;

public class NearMinionFriends extends MinionFriends {
    protected List<LivingUnit> nearMinionFriends = new ArrayList<>();

    public NearMinionFriends(World world, Faction friendFaction, Wizard self) {
        super(world, friendFaction);
        for (LivingUnit minionFriend : minionFriends) {
            if (minionFriend.getDistanceTo(self) <= self.getVisionRange() + Const.NEAR_DIST_INC) {
                nearMinionFriends.add(minionFriend);
            }
        }
    }

    public boolean isEmpty() {
        return nearMinionFriends.isEmpty();
    }

    public Point getCenter() {
        if (isEmpty()){
            return null;
        }

        double centerX = 0;
        double centerY = 0;
        for (LivingUnit nearMinionFriend : nearMinionFriends) {
            centerX += nearMinionFriend.getX();
            centerY += nearMinionFriend.getY();
        }
        centerX /= nearMinionFriends.size();
        centerY /= nearMinionFriends.size();

        return new Point(centerX, centerY);
    }
}
