package theUnchainedMod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;

@SpirePatch(
        clz = GameActionManager.class,
        method = "clear")
public class SetRelayedDmgSumToZero {

    @SpirePostfixPatch
    public static void clearRelayedDmgSum(GameActionManager actionManager) {
        RelayedDmgSum.relayedDamageSum.set(actionManager, 0);
    }
}
