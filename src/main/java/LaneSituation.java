import model.LaneType;
import model.World;

import java.util.ArrayList;

public class LaneSituation {
    private final World world;

    public LaneSituation(World world) {
        this.world = world;
    }

    public boolean isNeedChangeLane(LaneType lane) {
        final FriendTowers friendTowers = new FriendTowers(world);
        final int laneTowersCounter = friendTowers.countOnLane(lane);
        if (laneTowersCounter == 0) {
            return false;
        } else {
            for (LaneType laneType : LaneType.values()) {
                if (friendTowers.countOnLane(laneType) == 0) {
                    return true;
                }
            }
            return false;
        }
    }

    public LaneType chooseNewLane() {
        final FriendTowers friendTowers = new FriendTowers(world);
        final ArrayList<LaneType> badLaneTypes = new ArrayList<>();
        for (LaneType laneType : LaneType.values()) {
            if (friendTowers.countOnLane(laneType) == 0) {
                badLaneTypes.add(laneType);
            }
        }
        return badLaneTypes.get(0);
    }
}
