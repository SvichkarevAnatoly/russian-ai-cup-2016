import model.*;

public class GameStateAnalyzer {
    private Wizard self;
    private World world;
    private Game game;
    private Move move;

    public GameStateAnalyzer(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
    }

    public GameState getGameState() {
        final GameState gameState = new GameState();
        initHP(gameState);
        initEnemy(gameState);
        initFriend(gameState);

        return gameState;
    }

    private void initHP(GameState gameState) {
        // Если осталось мало жизненной энергии
        if (self.getLife() < self.getMaxLife() * Const.LOW_HP_FACTOR) {
            gameState.isLowHP = true;
        }
    }

    private void initEnemy(GameState gameState) {
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

    private void initFriend(GameState gameState) {

    }
}
