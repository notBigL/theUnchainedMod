package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spireTogether.SpireTogetherMod;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.AbstractDynamicBoosterPackCard;
import theUnchainedMod.cards.AbstractDynamicBoosterPackRelayCard;
import theUnchainedMod.characters.TheUnchained;

import java.util.Iterator;

@SpirePatch2(clz = AbstractDungeon.class, method = "initializeCardPools")
public class RemoveBoosterPackCardsFromPoolPatch {
    public static void Postfix(){

        if(AbstractDungeon.player instanceof TheUnchained)
            if(!TheUnchainedMod.UNCHAINED_BOOSTER_PACK_ACTIVATED || SpireTogetherMod.isConnected) {
                RemoveBoosterPackCardsFromPool(AbstractDungeon.commonCardPool);
                RemoveBoosterPackCardsFromPool(AbstractDungeon.uncommonCardPool);
                RemoveBoosterPackCardsFromPool(AbstractDungeon.rareCardPool);
                RemoveBoosterPackCardsFromPool(AbstractDungeon.colorlessCardPool);
                RemoveBoosterPackCardsFromPool(AbstractDungeon.curseCardPool);
        }
    }

    public static void RemoveBoosterPackCardsFromPool(CardGroup g){
        Iterator i = g.group.iterator();

        while(i.hasNext()){
            AbstractCard c  = (AbstractCard) i.next();
            if(c instanceof AbstractDynamicBoosterPackCard || c instanceof AbstractDynamicBoosterPackRelayCard){
                i.remove();
            }
        }
    }
}