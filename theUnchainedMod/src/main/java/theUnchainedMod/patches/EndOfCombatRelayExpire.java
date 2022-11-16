package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = GameActionManager.class, method = "clear")
public class EndOfCombatRelayExpire {

    @SpirePostfixPatch
    public static void clearCurrentRelay(GameActionManager gAM) {
        RelayHelpers.currentRelay.set(AbstractDungeon.player, 0);
    }
}
