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

    public void build() {
        final GameState gameState = history.getGameState();
        final Params params = new Params(self);

        final GlobalMoving globalMoving = new GlobalMoving(self, game);
        final Moving moving = new Moving(self, move, game, gameState);

        if (gameState.isNotAlive){
            return;
        }

        if(gameState.canNotMove) {
            moving.goSomewhere();
        } else if (gameState.isLowHP) {
            moving.goTo(globalMoving.getPreviousWaypoint());
        } else if (gameState.canBeUnderTowerAttack){
            final LivingUnit nearestTower = new EnemyTowers(world, params.enemy).getNearest(self);
            moving.goOpposite(new Point(nearestTower));
        } else if (gameState.canAttack) {
            final Enemies enemies = new Enemies(world, params.enemy);
            final LivingUnit nearestTarget = enemies.getNearest(self);
            final Attack attack = new Attack(self, game);
            attack.getMove(move, nearestTarget);
        } else {
            // Если нет других действий, просто продвигаемся вперёд.
            moving.goToNextWaypoint(globalMoving.getNextWaypoint());
        }
    }
}
