package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = GameActionManager.class,
        method = "<class>"
)
public class SwirlsGeneratedThisCombat {
    public static SpireField<Integer> swirlsGeneratedThisCombat = new SpireField(() -> {
        return 0;
    });

    public SwirlsGeneratedThisCombat() {
    }

    public static void IncreaseSwirlsGeneratedThisCombat(int i) {
        swirlsGeneratedThisCombat.set(AbstractDungeon.actionManager, i + (Integer) swirlsGeneratedThisCombat.get(AbstractDungeon.actionManager));
    }
}
