import model.*;

import java.util.ArrayList;
import java.util.List;

public class NearMinionFriends extends MinionFriends {
    protected List<LivingUnit> nearMinionFriends = new ArrayList<>();

    public NearMinionFriends(World world, Faction friendFaction, Wizard self) {
        super(world, friendFaction);
        for (LivingUnit minionFriend : minionFriends) {
            if (minionFriend.getDistanceTo(self) <= self.getVisionRange()) {
                nearMinionFriends.add(minionFriend);
            }
        }
    }

    public boolean isFriendsAhead(Wizard self) {
        for (LivingUnit minion : nearMinionFriends) {
            if (M.y(minion.getY()) > (M.y(self.getY()) - (minion.getX() - self.getX()))) {
                return true;
            }
        }

        return false;
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

    public int size() {
        return nearMinionFriends.size();
    }
}
