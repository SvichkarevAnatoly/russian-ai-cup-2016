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

        final Point previousWaypoint = globalMoving.getPreviousWaypoint();
        final Point nextWaypoint = globalMoving.getNextWaypoint();

        if(gs.canNotMove) {
            moving.goStrafe();
            attackIfCanWithoutTurnNearest(attack, enemies);
        } else if (gs.isLowHP) {
            moving.goTo(previousWaypoint);
            attackIfCanWithoutTurnNearest(attack, enemies);
        } else if (gs.wasUnderAttack) {
            final LivingUnit lastEnemy = history.getLastEnemy();
            if (lastEnemy != null) { // not enemy unit fire
                moving.goBackward(previousWaypoint);
                attackIfCanWithoutTurnNearest(attack, enemies);
            } else { // don't know how to react for friend fire
                moving.goToNextWaypoint(nextWaypoint);
            }
        } else if (gs.isOrkAtWarningDistance) {
            moving.goBackward(previousWaypoint);
            attackIfCanWithoutTurnNearest(attack, enemies);
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
            if (!gs.isNearEnemyBase || (nearMinionFriends.size() > 8)) {
                final Point center = nearMinionFriends.getCenter();
                moving.goTo(center);
            }
        } else if (gs.canBeUnderTowerAttack) { // TODO: по-моему лишняя проверка
            if (gs.canAttack) {
                final LivingUnit nearest = enemies.getNearest(self);
                attack.attack(move, nearest);
            }
        } else {
            // Если нет других действий, просто продвигаемся вперёд.
            moving.goToNextWaypoint(nextWaypoint);
        }
    }

    private void attackIfCanWithoutTurnNearest(Attack attack, Enemies enemies) {
        final LivingUnit nearest = enemies.getNearest(self);
        if (nearest != null) {
            attack.attackIfCanWithoutTurn(move, nearest);
        }
    }
}
