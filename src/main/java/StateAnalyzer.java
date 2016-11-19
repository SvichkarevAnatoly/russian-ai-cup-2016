import model.*;

public class StateAnalyzer {
    private Wizard self;
    private World world;
    private Game game;
    private Move move;

    public StateAnalyzer(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
    }

    public StateShot getStateShot(History history) {
        final GameStateAnalyzer gsa = new GameStateAnalyzer(self, world, game, move);
        final GameState gameState = gsa.getGameState(history);

        final WizardParamsAnalyzer wpa = new WizardParamsAnalyzer(self, world, game, move);
        final WizardParams wizardParams = wpa.getWizardParams();

        return new StateShot(gameState, wizardParams);
    }
}
