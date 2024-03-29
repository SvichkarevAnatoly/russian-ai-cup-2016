import model.*;

import java.util.List;

public class GameStateAnalyzer {
    private Wizard self;
    private World world;

    private GameState gameState = new GameState();

    public GameStateAnalyzer(Wizard self, World world) {
        this.self = self;
        this.world = world;
    }

    public GameState getGameState() {
        initSelf();
        initPosition();
        initEnemy();
        initFriend();

        return gameState;
    }

    private void initSelf() {
        final History history = History.getInstance();
        if (!initIsNotAlive()) {
            initIsLowHP();
            initCanNotMove(history);
            initIsUnderAttack(history);
            initWasUnderAttack(history);
        }
    }

    private void initWasUnderAttack(History history) {
        if (history.size() < Const.WAS_ATTACK_TIME) {
            return;
        }

        final List<GameState> lastGameStates = history.getLastGameStates(Const.WAS_ATTACK_TIME);
        for (GameState state : lastGameStates) {
            if (state.isUnderAttack) {
                gameState.wasUnderAttack = true;
                return;
            }
        }
    }

    private void initIsUnderAttack(History history) {
        if (history.size() < 2) {
            return;
        }

        final WizardParams preWizardParams = history.getPreviousWizardParams();
        if (self.getLife() < preWizardParams.hp) {
            gameState.isUnderAttack = true;

            final Faction enemyFaction = new Params(self).enemy;
            final Enemies enemies = new Enemies(world, enemyFaction);
            history.getOtherState().lastEnemy = enemies.getNearest(self);
        }
    }

    private void initCanNotMove(History history) {
        if (history.size() < 2) {
            return;
        }

        final GameState preGameState = history.getPreviousGameState();
        if (preGameState.isMoving && !preGameState.isOnlyTurning) {
            final WizardParams preWizardParams = history.getPreviousWizardParams();
            final Point point = preWizardParams.point;
            if (point.getDistanceTo(self) < Const.DIST_EPS) {
                gameState.canNotMove = true;
            }
        }
    }

    private void initPosition() {
        initIsNearEnemyBase();
        initIsNearFriendBase();

        initIsBaseUnderAttack();
        initIsNeedChangeLane();
    }

    private void initIsNearEnemyBase() {
        gameState.isNearEnemyBase = EnemyMiddleTower.isInRangeBase(self, world);
    }

    private void initIsNearFriendBase() {
        final Params params = new Params(self);
        final FriendBase friendBase = new FriendBase(world, params.self);
        gameState.isNearFriendBase = friendBase.isNear(self);
    }

    private void initIsBaseUnderAttack() {
        final Faction friendFaction = new Params(this.self).self;
        final FriendBase base = new FriendBase(world, friendFaction);
        gameState.baseLife = base.getLife();
        final History history = History.getInstance();
        if (history.isEmpty()) {
            return;
        }

        final GameState preGameState = history.getGameState();
        if (gameState.baseLife < preGameState.baseLife) {
            gameState.isBaseUnderAttack = true;
        }
    }

    private void initIsNeedChangeLane() {
        final LaneSituation situation = new LaneSituation(world);
        final History history = History.getInstance();
        if (history.isEmpty()) {
            gameState.isNeedChangeLane = false;
        } else {
            final GlobalMoving globalMoving = GlobalMoving.getInstance();
            if (globalMoving.hasNextLane()) {
                gameState.isNeedChangeLane = situation.chooseNewLane() != globalMoving.getLane();
            } else {
                final LaneType currentLane = globalMoving.getLane();
                gameState.isNeedChangeLane = situation.isNeedChangeLane(currentLane);
            }
        }
    }

    private void initEnemy() {
        final Params params = new Params(self);

        initCanBeUnderAttack();

        final Enemies enemies = new EnemiesInRange(world, params.enemy, self);
        if (enemies.enemies.size() > 1) {
            gameState.hasMoreThanOneEnemy = true;
        }

        final LivingUnit nearestEnemy = enemies.getNearest(self);
        if (nearestEnemy != null) {
            gameState.hasEnemy = true;
            gameState.isOrkAtWarningDistance = enemies.isOrkAtWarningDistance(self);
            final Attack attack = new Attack(self);
            if (attack.canAttack(nearestEnemy)) {
                gameState.canAttack = true;
            }
        }
    }

    private void initCanBeUnderAttack() {
        gameState.canBeUnderTowerAttack = EnemyMiddleTower.isInRange(self);
    }

    private void initFriend() {
        final Params params = new Params(self);
        final NearMinionFriends nearMinionFriends = new NearMinionFriends(world, params.self, self);
        if (nearMinionFriends.isFriendsAhead(self)) {
            gameState.isFriendMinionsAhead = true;
        }
    }

    private boolean initIsNotAlive() {
        if (self.getLife() == 0) {
            gameState.isNotAlive = true;
            GlobalMoving.getInstance().resetWaypoint();
        }
        return gameState.isNotAlive;
    }

    private void initIsLowHP() {
        // Если осталось мало жизненной энергии
        if (self.getLife() < self.getMaxLife() * Const.LOW_HP_FACTOR) {
            gameState.isLowHP = true;
        }
    }
}
