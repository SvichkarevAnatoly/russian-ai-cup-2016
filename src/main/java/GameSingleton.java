import model.Game;

public class GameSingleton {
    private static Game game;

    public static void setInstance(Game game) {
        GameSingleton.game = game;
    }

    public static Game getInstance() {
        return game;
    }

    private GameSingleton() {
    }
}
