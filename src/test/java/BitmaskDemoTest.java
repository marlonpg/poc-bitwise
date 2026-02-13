import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import org.junit.jupiter.api.Test;

class BitmaskDemoTest {
    // Each constant is a single bit; combinations are OR'ed together.
    enum SoldierType {
        NONE(0),
        PRIVATE(1),
        CORPORAL(2),
        SERGEANT(4),
        LIEUTENANT(8),
        MAJOR(16),
        GENERAL(32);

        final int mask;

        SoldierType(int mask) {
            this.mask = mask;
        }
    }

    @Test
    void bitwiseOperationsDemo() {
        int soldiers = 0;

        // OR: add flags
        soldiers = soldiers | SoldierType.MAJOR.mask;
        soldiers = soldiers | SoldierType.GENERAL.mask;
        assertEquals("110000", toBinary(soldiers));

        // AND: check flag
        boolean hasGeneral = (soldiers & SoldierType.GENERAL.mask) != 0;
        assertTrue(hasGeneral);

        // XOR: toggle flag
        soldiers = soldiers ^ SoldierType.MAJOR.mask; // remove Major
        assertEquals("100000", toBinary(soldiers));

        // NOT: flip bits (mask to keep only known flags)
        int allFlags = SoldierType.PRIVATE.mask | SoldierType.CORPORAL.mask | SoldierType.SERGEANT.mask
                | SoldierType.LIEUTENANT.mask | SoldierType.MAJOR.mask | SoldierType.GENERAL.mask;
        int notMajor = (~SoldierType.MAJOR.mask) & allFlags;
        assertFalse((notMajor & SoldierType.MAJOR.mask) != 0);
        assertTrue((notMajor & SoldierType.PRIVATE.mask) != 0);

        // Right shift: divide by 2
        int sergeant = SoldierType.SERGEANT.mask; // 4
        int rightShift = sergeant >> 1; // 2 -> CORPORAL
        assertEquals(SoldierType.CORPORAL.mask, rightShift);

        // Left shift: multiply by 2
        int leftShift = sergeant << 1; // 8 -> LIEUTENANT
        assertEquals(SoldierType.LIEUTENANT.mask, leftShift);

        // Show which SoldierType flags are in a mask
        EnumSet<SoldierType> active = toSet(soldiers);
        assertEquals(EnumSet.of(SoldierType.GENERAL), active);
    }

    private static String toBinary(int value) {
        return Integer.toBinaryString(value);
    }

    private static EnumSet<SoldierType> toSet(int mask) {
        EnumSet<SoldierType> set = EnumSet.noneOf(SoldierType.class);
        for (SoldierType type : SoldierType.values()) {
            if (type != SoldierType.NONE && (mask & type.mask) != 0) {
                set.add(type);
            }
        }
        return set;
    }
}
