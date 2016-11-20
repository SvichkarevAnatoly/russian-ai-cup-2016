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

        final Attack attack = new Attack(self, game);
        final Enemies enemies = new Enemies(world, params.enemy);

        if(gameState.canNotMove) {
            moving.goSomewhere();
        } else if (gameState.isLowHP) {
            moving.goTo(globalMoving.getPreviousWaypoint());
        } else if (gameState.wasUnderAttack) {
            final LivingUnit lastEnemy = history.getLastEnemy();
            moving.goOpposite(new Point(lastEnemy));
            if (gameState.canAttack) {
                final LivingUnit nearest = enemies.getNearest(self);
                attack.attack(move, nearest);
            }
        } else if (gameState.hasEnemy && gameState.canAttack) {
            final LivingUnit selectedEnemy;
            if (gameState.hasMoreThanOneEnemy) {
                enemies.sortMostInjured();
                selectedEnemy = enemies.getFirstInRange(self);
            } else {
                selectedEnemy = enemies.getNearest(self);
            }
            attack.attack(move, selectedEnemy);
        } else {
            // Если нет других действий, просто продвигаемся вперёд.
            moving.goToNextWaypoint(globalMoving.getNextWaypoint());
        }
    }
}
