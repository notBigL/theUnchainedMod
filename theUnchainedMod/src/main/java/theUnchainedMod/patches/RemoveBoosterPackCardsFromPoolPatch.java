package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.Loader;
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

        if(AbstractDungeon.player instanceof TheUnchained) {
            boolean cardsHaveBeenRemoved = false;
            if (!TheUnchainedMod.BoosterpackActivated())
            {
                RemoveAllCards();
                cardsHaveBeenRemoved = true;
            }
            if(Loader.isModLoaded("spireTogether"))
            {
                if (SpireTogetherMod.isConnected && !cardsHaveBeenRemoved)
                    RemoveAllCards();
            }
        }
    }
    public static void RemoveAllCards()
    {
        RemoveBoosterPackCardsFromPool(AbstractDungeon.commonCardPool);
        RemoveBoosterPackCardsFromPool(AbstractDungeon.uncommonCardPool);
        RemoveBoosterPackCardsFromPool(AbstractDungeon.rareCardPool);
        RemoveBoosterPackCardsFromPool(AbstractDungeon.colorlessCardPool);
        RemoveBoosterPackCardsFromPool(AbstractDungeon.curseCardPool);

        RemoveBoosterPackCardsFromPool(AbstractDungeon.srcCommonCardPool);
        RemoveBoosterPackCardsFromPool(AbstractDungeon.srcUncommonCardPool);
        RemoveBoosterPackCardsFromPool(AbstractDungeon.srcRareCardPool);
        RemoveBoosterPackCardsFromPool(AbstractDungeon.srcColorlessCardPool);
        RemoveBoosterPackCardsFromPool(AbstractDungeon.srcCurseCardPool);
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