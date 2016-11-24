import model.*;

public class FriendBase {
    private Building base;

    public FriendBase(World world, Faction friend) {
        final Building[] buildings = world.getBuildings();
        for (Building building : buildings) {
            if (building.getType() == BuildingType.FACTION_BASE &&
                    building.getFaction() == friend) {
                base = building;
            }
        }
    }

    public int getLife() {
        if (base != null) {
            return base.getLife();
        }
        return -1;
    }

    public boolean isNear(Wizard self) {
        final double baseDistance = getBaseDistance(self);
        return baseDistance <= Const.DIST_TO_FRIEND_BASE_TO_SWITCH_BEHAVIOUR;
    }

    private double getBaseDistance(Wizard self) {
        return base.getDistanceTo(self);
    }
}
