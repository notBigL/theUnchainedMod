package theUnchainedMod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = GameActionManager.class,
        method = SpirePatch.CLASS
)
public class ChainsFinishedThisCombat {
    public static SpireField<Integer> chainsFinishedThisCombat = new SpireField<>(() -> 0);

    public static void IncreaseChainsFinishedThisCombat(int i)
    {
        chainsFinishedThisCombat.set(AbstractDungeon.actionManager, i + chainsFinishedThisCombat.get(AbstractDungeon.actionManager));
    }
}
