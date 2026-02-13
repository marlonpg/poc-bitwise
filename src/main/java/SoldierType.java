public enum SoldierType {
    NONE(0),
    PRIVATE(1),
    CORPORAL(2),
    SERGEANT(4),
    LIEUTENANT(8),
    MAJOR(16),
    GENERAL(32);

    public final int mask;

    SoldierType(int mask) {
        this.mask = mask;
    }
}
