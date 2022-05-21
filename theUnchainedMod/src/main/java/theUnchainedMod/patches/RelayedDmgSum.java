package theUnchainedMod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;

@SpirePatch(
        clz = GameActionManager.class,
        method = SpirePatch.CLASS
)
public class RelayedDmgSum {
    public static SpireField<Integer> relayedDamageSum = new SpireField<>(() -> 0);
}
