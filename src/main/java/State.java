import model.*;

public class State {
    public boolean isLowHP;
    public boolean hasEnemy;
    public boolean canAttack;

    private Wizard self;
    private World world;
    private Game game;
    private Move move;

    public State(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;

        checkState();
    }

    private void checkState() {
        // Если осталось мало жизненной энергии
        if (self.getLife() < self.getMaxLife() * Const.LOW_HP_FACTOR) {
            isLowHP = true;
        }

        final EnemyAnalysis enemyAnalysis = new EnemyAnalysis(self, world);
        final LivingUnit nearestEnemy = enemyAnalysis.getNearestEnemy();
        if (nearestEnemy != null) {
            hasEnemy = true;
            final Attack attack = new Attack(self, game);
            if (attack.canAttack(nearestEnemy)) {
                canAttack = true;
            }
        }
    }
}
