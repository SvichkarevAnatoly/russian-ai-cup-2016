import model.Unit;

/**
 * Вспомогательный класс для хранения позиций на карте.
 */
final class Point2D {
    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistanceTo(double x, double y) {
        return StrictMath.hypot(this.x - x, this.y - y);
    }

    public double getDistanceTo(Point2D point) {
        return getDistanceTo(point.x, point.y);
    }

    public double getDistanceTo(Unit unit) {
        return getDistanceTo(unit.getX(), unit.getY());
    }
}