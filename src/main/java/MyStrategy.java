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

        // Постоянно двигаемся из-стороны в сторону, чтобы по нам было сложнее попасть.
        // Считаете, что сможете придумать более эффективный алгоритм уклонения? Попробуйте! ;)
        move.setStrafeSpeed(random.nextBoolean() ? game.getWizardStrafeSpeed() : -game.getWizardStrafeSpeed());

        // Если осталось мало жизненной энергии, отступаем к предыдущей ключевой точке на линии.
        if (self.getLife() < self.getMaxLife() * LOW_HP_FACTOR) {
            goTo(globalMoving.getPreviousWaypoint());
            return;
        }

        LivingUnit nearestTarget = getNearestTarget();

        // Если видим противника ...
        if (nearestTarget != null) {
            double distance = self.getDistanceTo(nearestTarget);

            // ... и он в пределах досягаемости наших заклинаний, ...
            if (distance <= self.getCastRange()) {
                double angle = self.getAngleTo(nearestTarget);

                // ... то поворачиваемся к цели.
                move.setTurn(angle);

                // Если цель перед нами, ...
                if (StrictMath.abs(angle) < game.getStaffSector() / 2.0D) {
                    // ... то атакуем.
                    move.setAction(ActionType.MAGIC_MISSILE);
                    move.setCastAngle(angle);
                    move.setMinCastDistance(distance - nearestTarget.getRadius() + game.getMagicMissileRadius());
                }

                return;
            }
        }

        // Если нет других действий, просто продвигаемся вперёд.
        goTo(globalMoving.getNextWaypoint());
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
    }

    /**
     * Простейший способ перемещения волшебника.
     */
    private void goTo(Point2D point) {
        double angle = self.getAngleTo(point.getX(), point.getY());

        move.setTurn(angle);

        if (StrictMath.abs(angle) < game.getStaffSector() / 4.0D) {
            move.setSpeed(game.getWizardForwardSpeed());
        }
    }

    /**
     * Находим ближайшую цель для атаки, независимо от её типа и других характеристик.
     */
    private LivingUnit getNearestTarget() {
        List<LivingUnit> targets = new ArrayList<>();
        targets.addAll(Arrays.asList(world.getBuildings()));
        targets.addAll(Arrays.asList(world.getWizards()));
        targets.addAll(Arrays.asList(world.getMinions()));

        LivingUnit nearestTarget = null;
        double nearestTargetDistance = Double.MAX_VALUE;

        for (LivingUnit target : targets) {
            if (target.getFaction() == Faction.NEUTRAL || target.getFaction() == self.getFaction()) {
                continue;
            }

            double distance = self.getDistanceTo(target);

            if (distance < nearestTargetDistance) {
                nearestTarget = target;
                nearestTargetDistance = distance;
            }
        }

        return nearestTarget;
    }
}