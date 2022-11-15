package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.powers.EnclosingSteelPower;

@SpirePatch(clz = GameActionManager.class, method = "getNextAction")
public class StartOfTurnRelayExpire {

    @SpirePostfixPatch
    public static void expireRelayAtStartOfTurn(GameActionManager gAM) {
        if (gAM.actions.isEmpty() && gAM.preTurnActions.isEmpty() && gAM.cardQueue.isEmpty() && gAM.monsterAttacksQueued && gAM.monsterQueue.isEmpty() && gAM.turnHasEnded && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            if (!AbstractDungeon.player.hasPower(EnclosingSteelPower.POWER_ID)) {
                RelayHelpers.loseRelay(false, AbstractDungeon.player);
            }
        }
    }
}
