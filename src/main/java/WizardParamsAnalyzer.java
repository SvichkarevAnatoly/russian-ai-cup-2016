import model.Game;
import model.Move;
import model.Wizard;
import model.World;

public class WizardParamsAnalyzer {
    private Wizard self;
    private World world;
    private Game game;
    private Move move;

    public WizardParamsAnalyzer(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
    }

    public WizardParams getWizardParams() {
        final WizardParams wizardParams = new WizardParams();

        wizardParams.hp = self.getLife();
        wizardParams.mana = self.getMana();

        wizardParams.x = self.getX();
        wizardParams.y = self.getY();

        return wizardParams;
    }
}
