import model.*;

import java.util.*;

public final class MyStrategy implements Strategy {
    private static final double LOW_HP_FACTOR = 0.25D;

    private Wizard self;
    private World world;
    private Game game;
    private Move move;

    private Random random;
    private GlobalMoving globalMoving;
    private Moving moving;
    private EnemyAnalysis enemyAnalysis;

    /**
     * Основной метод стратегии, осуществляющий управление волшебником.
     * Вызывается каждый тик для каждого волшебника.
     *
     * @param self  Волшебник, которым данный метод будет осуществлять управление.
     * @param world Текущее состояние мира.
     * @param game  Различные игровые константы.
     * @param move  Результатом работы метода является изменение полей данного объекта.
     */
    @Override
    public void move(Wizard self, World world, Game game, Move move) {
        initializeTick(self, world, game, move);
        initializeStrategy();

        // Если осталось мало жизненной энергии, отступаем к предыдущей ключевой точке на линии.
        if (self.getLife() < self.getMaxLife() * LOW_HP_FACTOR) {
            moving.goTo(globalMoving.getPreviousWaypoint());
            return;
        }

        final LivingUnit nearestTarget = enemyAnalysis.getNearestTarget();
        final Attack attack = new Attack(self, game);
        if (attack.canAttack(nearestTarget)) {
            attack.getMove(move, nearestTarget);
        } else {
            // Если нет других действий, просто продвигаемся вперёд.
            moving.goTo(globalMoving.getNextWaypoint());
        }
    }

    /**
     * Сохраняем все входные данные в полях класса для упрощения доступа к ним.
     */
    private void initializeTick(Wizard self, World world, Game game, Move move) {
        this.self = self;
        this.world = world;
        this.game = game;
        this.move = move;
    }

    /**
     * Инциализируем стратегию.
     * <p>
     * Для этих целей обычно можно использовать конструктор, однако в данном случае мы хотим инициализировать генератор
     * случайных чисел значением, полученным от симулятора игры.
     */
    private void initializeStrategy() {
        random = random == null ?
                new Random(game.getRandomSeed()) : random;

        globalMoving = new GlobalMoving(self, game);
        moving = new Moving(self, move, game);
        enemyAnalysis = new EnemyAnalysis(self, world);
    }
}