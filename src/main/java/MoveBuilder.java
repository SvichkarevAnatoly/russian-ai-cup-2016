import model.*;

public class MoveBuilder {
    private final Wizard self;
    private final World world;
    private final Game game;
    private final Move move;

    private final History history;

    public MoveBuilder(History history, Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;

        this.history = history;
    }

    public Move build() {
        final GameState gameState = history.getGameState();

        final GlobalMoving globalMoving = new GlobalMoving(self, game);
        final Moving moving = new Moving(self, move, game, gameState);
        final EnemyAnalysis enemyAnalysis = new EnemyAnalysis(self, world);

        if(gameState.canNotMove) {
            moving.goSomewhere();
        } else if (gameState.isLowHP) {
            moving.goTo(globalMoving.getPreviousWaypoint());
        } else if (gameState.hasNearEnemy) {
            final LivingUnit nearestTarget = enemyAnalysis.getNearestEnemy();
            final Attack attack = new Attack(self, game);
            attack.getMove(move, nearestTarget);
        } else {
            // Если нет других действий, просто продвигаемся вперёд.
            moving.goToNextWaypoint(globalMoving.getNextWaypoint());
        }

        return move;
    }
}
