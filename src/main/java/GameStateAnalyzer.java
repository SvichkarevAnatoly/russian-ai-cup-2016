import model.*;

public class GameStateAnalyzer {
    private Wizard self;
    private World world;
    private Game game;
    private Move move;

    private GameState gameState = new GameState();

    public GameStateAnalyzer(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
    }

    public GameState getGameState(History history) {
        initSelf(history);
        initEnemy();
        initFriend();

        return gameState;
    }

    private void initSelf(History history) {
        initIsLowHP();
        initCanNotMove(history);
    }

    private void initCanNotMove(History history) {
        if (history.isEmpty()) {
            return;
        }

        final WizardParams wizardParams = history.getWizardParams();
        final Point point = wizardParams.point;
        if (point.getDistanceTo(self) < Const.DIST_EPS) {
            gameState.canNotMove = true;
        }
    }

    private void initEnemy() {
        final EnemyAnalysis enemyAnalysis = new EnemyAnalysis(self, world);
        final LivingUnit nearestEnemy = enemyAnalysis.getNearestEnemy();
        if (nearestEnemy != null) {
            gameState.hasNearEnemy = true;
            final Attack attack = new Attack(self, game);
            if (attack.canAttack(nearestEnemy)) {
                gameState.canAttack = true;
            }
        }
    }

    private void initFriend() {

    }

    private void initIsLowHP() {
        // Если осталось мало жизненной энергии
        if (self.getLife() < self.getMaxLife() * Const.LOW_HP_FACTOR) {
            gameState.isLowHP = true;
        }
    }
}
