import model.Game;
import model.Move;
import model.Wizard;
import model.World;

public final class MyStrategy implements Strategy {
    // DEBUG
    private static int iter = 0;

    private History history = new History();

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
        System.out.println(iter++);
        initializeStrategy(self, world, game, move);

        final MoveBuilder moveBuilder = new MoveBuilder(history, self, world, game, move);
        moveBuilder.build();
    }

    /**
     * Инциализируем стратегию.
     * <p>
     * Для этих целей обычно можно использовать конструктор, однако в данном случае мы хотим инициализировать генератор
     * случайных чисел значением, полученным от симулятора игры.
     */
    private void initializeStrategy(Wizard self, World world, Game game, Move move) {
        final StateAnalyzer stateAnalyzer = new StateAnalyzer(self, world, game, move);
        final StateShot stateShot = stateAnalyzer.getStateShot(history);
        history.add(stateShot);
    }
}