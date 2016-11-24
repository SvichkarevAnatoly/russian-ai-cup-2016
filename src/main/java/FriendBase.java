import model.Building;
import model.BuildingType;
import model.Faction;
import model.World;

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
}
