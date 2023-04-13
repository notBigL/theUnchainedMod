package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = GameActionManager.class, method = "clear")
public class EndOfCombatExpirePatch {

    @SpirePostfixPatch
    public static void clearCurrentRelay(GameActionManager gAM) {
        if (AbstractDungeon.player != null) {
            RelayHelpers.currentRelay.set(AbstractDungeon.player, 0);
            RelayHelpers.nextTurnRelayedDamage.set(AbstractDungeon.player, 0);
            RelayHelpers.thisTurnRelayedDamage.set(AbstractDungeon.player, 0);
        }
        RelayedDmgSum.relayedDamageSum.set(gAM, 0);
        ChainsFinishedThisCombat.chainsFinishedThisCombat.set(gAM, 0);
    }
}
