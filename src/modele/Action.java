package modele;

public class Action {
    private TypeAction type;
    private Direction direction;

    public Action(TypeAction type, Direction direction) {
        this.type = type;
        this.direction = direction;
    }

    public Type getType() {
    public TypeAction getType() {
        return type;
    }

    public Direction getDirection() {
        return direction;
    }
}
