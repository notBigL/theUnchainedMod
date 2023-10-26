package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.AbstractDynamicBoosterPackCard;
import theUnchainedMod.cards.AbstractDynamicBoosterPackRelayCard;
import theUnchainedMod.characters.TheUnchained;

import java.util.Iterator;

@SpirePatch2(clz = AbstractDungeon.class, method = "initializeCardPools")
public class RemoveBoosterPackCardsFromPoolPatch {
    public static void Postfix(){

        if(!(AbstractDungeon.player instanceof TheUnchained)) return;
        if(TheUnchainedMod.UNCHAINED_BOOSTER_PACK_ACTIVATED) return;
        PurgeCardsFromPool(AbstractDungeon.commonCardPool);
        PurgeCardsFromPool(AbstractDungeon.uncommonCardPool);
        PurgeCardsFromPool(AbstractDungeon.rareCardPool);
        PurgeCardsFromPool(AbstractDungeon.colorlessCardPool);
        PurgeCardsFromPool(AbstractDungeon.curseCardPool);
    }

    public static void PurgeCardsFromPool(CardGroup g){
        Iterator i = g.group.iterator();

        while(i.hasNext()){
            AbstractCard c  = (AbstractCard) i.next();
            if(c instanceof AbstractDynamicBoosterPackCard || c instanceof AbstractDynamicBoosterPackRelayCard){
                i.remove();
            }
        }
    }
}