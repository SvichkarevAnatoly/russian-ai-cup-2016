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
        final GameState gs = history.getGameState();
        final Params params = new Params(self);

        final GlobalMoving globalMoving = new GlobalMoving(self, game);
        final Moving moving = new Moving(self, move, game, gs);

        if (gs.isNotAlive){
            return;
        }

        final Attack attack = new Attack(self, game);
        final Enemies enemies = new Enemies(world, params.enemy);
        final NearMinionFriends nearMinionFriends = new NearMinionFriends(world, params.self, self);

        if(gs.canNotMove) {
            moving.goSomewhere();
        } else if (gs.isLowHP) {
            moving.goTo(globalMoving.getPreviousWaypoint());
        } else if (gs.wasUnderAttack) {
            final LivingUnit lastEnemy = history.getLastEnemy();
            moving.goOpposite(new Point(lastEnemy));
            if (gs.canAttack) {
                final LivingUnit nearest = enemies.getNearest(self);
                attack.attack(move, nearest);
            }
        } else if (gs.hasEnemy && gs.canAttack) {
            final LivingUnit selectedEnemy;
            if (gs.hasMoreThanOneEnemy) {
                enemies.sortMostInjured();
                selectedEnemy = enemies.getFirstInRange(self);
            } else {
                selectedEnemy = enemies.getNearest(self);
            }
            attack.attack(move, selectedEnemy);
        } else if (gs.isFriendMinionsAhead) {
            final Point center = nearMinionFriends.getCenter();
            moving.goTo(center);
        } else if (gs.canBeUnderTowerAttack) {
            if (gs.canAttack) {
                final LivingUnit nearest = enemies.getNearest(self);
                attack.attack(move, nearest);
            }
        } else {
            // Если нет других действий, просто продвигаемся вперёд.
            moving.goToNextWaypoint(globalMoving.getNextWaypoint());
        }
    }
}
