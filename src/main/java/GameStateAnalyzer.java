import model.*;

import java.util.List;

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

    private void initEnemy() {
        final Params params = new Params(self);

        initCanBeUnderAttack(params);

        final Enemies enemies = new Enemies(world, params.enemy);
        if (enemies.enemies.size() > 1) {
            gameState.hasMoreThanOneEnemy = true;
        }

        final LivingUnit nearestEnemy = enemies.getNearest(self);
        if (nearestEnemy != null) {
            gameState.hasEnemy = true;
            final Attack attack = new Attack(self, game);
            if (attack.canAttack(nearestEnemy)) {
                gameState.canAttack = true;
            }
        }
    }

    private void initCanBeUnderAttack(Params params) {
        final EnemyTowers enemyTowers = new EnemyTowers(world, params.enemy);
        gameState.canBeUnderTowerAttack = enemyTowers.isInRange(self);
    }

    private void initFriend() {

    }

    private boolean initIsNotAlive() {
        if (self.getLife() == 0) {
            gameState.isNotAlive = true;
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
