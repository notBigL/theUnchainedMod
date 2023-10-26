package theUnchainedMod.patches;

import basemod.patches.com.megacrit.cardcrawl.screens.VictoryScreen.TrackKilledHeart;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import theUnchainedMod.characters.TheUnchained;

@SpirePatch(
        clz = SingleCardViewPopup.class,
        method = "canToggleBetaArt"
)
public class BoosterPackBetaArtTogglePatch {
    @SpirePostfixPatch
    public static boolean otherCharacterCheck(boolean __result, SingleCardViewPopup __instance, AbstractCard ___card) {
        /*if (___card != null && !__result && ___card.color == TheUnchained.Enums.COLOR_BOOSTER) { // Check if card is a booster pack card
            for (AbstractPlayer p : CardCrawlGame.characterManager.getAllCharacters()) {
                if (p.getCardColor() == TheUnchained.Enums.COLOR_ORANGE) { // Unchained Color
                    Prefs playerPrefs = p.getPrefs();
                    return playerPrefs != null && playerPrefs.getBoolean(TrackKilledHeart.HEART_KILL, false);
                }
            }
        }
        return __result;*/
        if (___card != null && !__result) {
            for (AbstractPlayer p : CardCrawlGame.characterManager.getAllCharacters()) {
                if (p.getCardColor() == TheUnchained.Enums.COLOR_ORANGE) { // Unchained Color
                    Prefs playerPrefs = p.getPrefs();
                    return playerPrefs != null && playerPrefs.getBoolean(TrackKilledHeart.HEART_KILL, false);
                }
            }
        }
        return __result;
    }
}