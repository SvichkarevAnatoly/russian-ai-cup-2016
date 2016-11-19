import model.Faction;
import model.Wizard;

public class Params {
    public Faction self;
    public Faction enemy;

    public Params(Wizard self) {
        this.self = self.getFaction();
        enemy = this.self == Faction.ACADEMY ?
                Faction.RENEGADES : Faction.ACADEMY;
    }
}
