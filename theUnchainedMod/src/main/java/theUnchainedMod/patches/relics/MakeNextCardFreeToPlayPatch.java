package theUnchainedMod.patches.relics;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.powers.OiledChainsPower;
import theUnchainedMod.util.UtilityClass;

@SpirePatch(clz = AbstractCard.class, method = "freeToPlay")
public class MakeNextCardFreeToPlayPatch {

    @SpirePostfixPatch
    public static boolean patch(boolean __result, AbstractCard __instance) {
        return __result || (UtilityClass.isInCombat() && AbstractDungeon.player.hasPower(OiledChainsPower.POWER_ID));
    }
}
