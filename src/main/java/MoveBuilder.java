import model.*;

public class MoveBuilder {
    private final Wizard self;
    private final World world;
    private final Move move;

    public MoveBuilder(Wizard self, World world, Move move) {
        this.self = self;
        this.world = world;
        this.move = move;
    }

    public void build() {
        final History history = History.getInstance();
        final GameState gs = history.getGameState();

        final Params params = new Params(self);

        final GlobalMoving globalMoving = GlobalMoving.getInstance();
        final Moving moving = new Moving(self, move, gs);

        final Attack attack = new Attack(self);

        final Enemies enemies = new EnemiesInRange(world, params.enemy, self);
        final NearMinionFriends nearMinionFriends = new NearMinionFriends(world, params.self, self);

        if (gs.isNotAlive){
            return;
        }
        System.out.println("x=" + self.getX() + " y=" + self.getY());
        if(gs.canNotMove) {
            moving.goStrafe();
            attackIfCanWithoutTurnNearest(attack, enemies);
        } else if (gs.isLowHP) {
            final Point previousWaypoint = globalMoving.getLaneForkPoint(self);
            moving.goTo(previousWaypoint);
            attackIfCanWithoutTurnNearest(attack, enemies);
        } else if (gs.wasUnderAttack) {
            final LivingUnit lastEnemy = history.getLastEnemy();
            if (lastEnemy != null) { // not enemy unit fire
                final Point previousWaypoint = globalMoving.getLaneForkPoint(self);
                moving.goBackward(previousWaypoint);
                attackIfCanWithoutTurnNearest(attack, enemies);
            } else { // don't know how to react for friend fire
                final Point nextWaypoint = globalMoving.getNextWaypoint(self);
                moving.goToNextWaypoint(nextWaypoint);
            }
        } else if (gs.isNeedChangeLane || gs.isBaseUnderAttack) {
            System.out.println("isNeedChangeLane");
            if (!globalMoving.hasNextLane()) {
                final LaneSituation laneSituation = new LaneSituation(world);
                final LaneType newLane = laneSituation.chooseNewLane();
                globalMoving.switchLane(newLane);
            }
            final Point laneForkPoint = globalMoving.getLaneForkPoint(self);
            moving.goTo(laneForkPoint);
        } else if (gs.isOrkAtWarningDistance) {
            final Point previousWaypoint = globalMoving.getLaneForkPoint(self);
            moving.goBackward(previousWaypoint);
            attackIfCanWithoutTurnNearest(attack, enemies);
        } else if (gs.hasEnemy && gs.canAttack) {
            attackBestTarget(gs, attack, enemies);
        } else if (gs.isFriendMinionsAhead && !gs.isNearFriendBase) {
            if (!gs.isNearEnemyBase || (nearMinionFriends.size() > 8)) {
                final Point center = nearMinionFriends.getCenter();
                globalMoving.setPastWaypointToNearest(center);
                moving.goTo(center);
            }
        } else if (gs.canBeUnderTowerAttack) {
            if (gs.canAttack) {
                final LivingUnit nearest = enemies.getNearest(self);
                attack.attack(move, nearest);
            }
        } else {
            // Если нет других действий, просто продвигаемся вперёд.
            final Point nextWaypoint = globalMoving.getNextWaypoint(self);
            moving.goToNextWaypoint(nextWaypoint);
        }
    }

    private void attackBestTarget(GameState gs, Attack attack, Enemies enemies) {
        final Params params = new Params(self);
        final EnemyMinionsInRange enemyMinions = new EnemyMinionsInRange(world, params.enemy, self);
        final EnemyWizardsInRange enemyWizards = new EnemyWizardsInRange(world, params.enemy, self);
        final EnemyTowersInRange enemyTowers = new EnemyTowersInRange(world, params.enemy, self);

        final LivingUnit selectedEnemy;
        if (gs.hasMoreThanOneEnemy) {
            if (!enemyTowers.isEmpty() && attack.canAttack(enemyTowers.getNearest(self))) {
                selectedEnemy = enemyTowers.getNearest(self);
            } else if (!enemyWizards.isEmpty() && attack.canAttack(enemyWizards.getNearest(self))) {
                enemyWizards.sortMostInjured();
                selectedEnemy = enemyWizards.getFirstInRange(self);
            } else {
                enemyMinions.sortMostInjured();
                selectedEnemy = enemyMinions.getFirstInRange(self);
            }
        } else {
            selectedEnemy = enemies.getNearest(self);
        }
        attack.attack(move, selectedEnemy);
    }

    private void attackIfCanWithoutTurnNearest(Attack attack, Enemies enemies) {
        final LivingUnit nearest = enemies.getNearest(self);
        if (nearest != null) {
            attack.attackIfCanWithoutTurn(move, nearest);
        }
    }
}
