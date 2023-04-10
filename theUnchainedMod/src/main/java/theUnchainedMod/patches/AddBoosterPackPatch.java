package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import theUnchainedMod.booster_pack_cards.*;
import theUnchainedMod.characters.TheUnchained;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

@SpirePatch(clz= AbstractDungeon.class, method = "initializeCardPools")
public class AddBoosterPackPatch {
    private static ArrayList<AbstractCard> boosterPackCards = new ArrayList<>(Arrays.asList(
            new ArcaneArtillery(),
            new BarbedWireChains(),
            new ChainFlourish(),
            new CrushPlates(),
            new MagicMecha(),
            new MakeHaste(),
            new RetreatingSwing()
    ));

    @SpirePostfixPatch
    public static void AddBoosterPack(AbstractDungeon dungeon) {
        if(!TheUnchained.BOOSTER_PACK_ACTIVATED) return;

        Iterator var4 = boosterPackCards.iterator();
        AbstractCard c;
        while (var4.hasNext()) {
            c = (AbstractCard) var4.next();
            switch (c.rarity) {
                case COMMON:
                    dungeon.commonCardPool.addToTop(c);
                    break;
                case UNCOMMON:
                    dungeon.uncommonCardPool.addToTop(c);
                    break;
                case RARE:
                    dungeon.rareCardPool.addToTop(c);
                    break;
                case CURSE:
                    dungeon.curseCardPool.addToTop(c);
                    break;
                default:
                    //dungeon.logger.info("Unspecified rarity: " + c.rarity.name() + " when creating pools! AbstractDungeon: Line 827");
            }
        }
    }
}
