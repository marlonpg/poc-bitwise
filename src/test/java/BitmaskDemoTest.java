import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import org.junit.jupiter.api.Test;

class BitmaskDemoTest {
    @Test
    void orAddsFlags() {
        int soldiers = 0;

        soldiers = soldiers | SoldierType.MAJOR.mask;
        soldiers = soldiers | SoldierType.GENERAL.mask;

        System.out.println("OR add flags: Major + General -> " + toBinary(soldiers));

        assertEquals("110000", toBinary(soldiers));
        assertEquals(EnumSet.of(SoldierType.MAJOR, SoldierType.GENERAL), toSet(soldiers));
    }

    @Test
    void andChecksFlag() {
        int soldiers = SoldierType.MAJOR.mask | SoldierType.GENERAL.mask;

        boolean hasGeneral = (soldiers & SoldierType.GENERAL.mask) != 0;
        boolean hasPrivate = (soldiers & SoldierType.PRIVATE.mask) != 0;

        System.out.println("AND check: mask=" + toBinary(soldiers)
                + " hasGeneral=" + hasGeneral + " hasPrivate=" + hasPrivate);

        assertTrue(hasGeneral);
        assertFalse(hasPrivate);
    }

    @Test
    void xorTogglesFlag() {
        int soldiers = SoldierType.MAJOR.mask | SoldierType.GENERAL.mask;

        soldiers = soldiers ^ SoldierType.MAJOR.mask;

        System.out.println("XOR toggle Major: " + toBinary(soldiers));

        assertEquals("100000", toBinary(soldiers));
        assertEquals(EnumSet.of(SoldierType.GENERAL), toSet(soldiers));
    }

    @Test
    void notFlipsWithinKnownFlags() {
        int allFlags = SoldierType.PRIVATE.mask | SoldierType.CORPORAL.mask | SoldierType.SERGEANT.mask
                | SoldierType.LIEUTENANT.mask | SoldierType.MAJOR.mask | SoldierType.GENERAL.mask;
        int notMajor = (~SoldierType.MAJOR.mask) & allFlags;

        System.out.println("NOT within flags: all=" + toBinary(allFlags)
            + " notMajor=" + toBinary(notMajor));

        assertFalse((notMajor & SoldierType.MAJOR.mask) != 0);
        assertTrue((notMajor & SoldierType.PRIVATE.mask) != 0);
    }

    @Test
    void rightShiftDividesByTwo() {
        int sergeant = SoldierType.SERGEANT.mask; // 4
        int rightShift = sergeant >> 1; // 2 -> CORPORAL

        System.out.println("Right shift: " + toBinary(sergeant) + " >> 1 = " + toBinary(rightShift));

        assertEquals(SoldierType.CORPORAL.mask, rightShift);
    }

    @Test
    void leftShiftMultipliesByTwo() {
        int sergeant = SoldierType.SERGEANT.mask; // 4
        int leftShift = sergeant << 1; // 8 -> LIEUTENANT

        System.out.println("Left shift: " + toBinary(sergeant) + " << 1 = " + toBinary(leftShift));

        assertEquals(SoldierType.LIEUTENANT.mask, leftShift);
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
