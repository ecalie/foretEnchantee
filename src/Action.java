public class Action {
    private Type type;
    private Direction direction;

    public Action(Type type, Direction direction) {
        this.type = type;
        this.direction = direction;
    }

    public Type getType() {
        return type;
    }

    public Direction getDirection() {
        return direction;
    }
}
