import model.*;

public class StateAnalyzer {
    private Wizard self;
    private World world;
    private Move move;

    public StateAnalyzer(Wizard self, World world, Move move) {
        this.self = self;
        this.world = world;
        this.move = move;
    }

    public StateShot getStateShot() {
        final WizardParamsAnalyzer wpa = new WizardParamsAnalyzer(self);
        final WizardParams wizardParams = wpa.getWizardParams();

        final GameStateAnalyzer gsa = new GameStateAnalyzer(self, world);
        final GameState gameState = gsa.getGameState();

        return new StateShot(gameState, wizardParams);
    }
}
