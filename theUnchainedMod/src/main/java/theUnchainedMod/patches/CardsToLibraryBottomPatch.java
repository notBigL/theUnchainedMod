package theUnchainedMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.screens.compendium.CardLibSortHeader;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import theUnchainedMod.cards.AbstractDynamicBoosterPackCard;

@SpirePatch2(clz = CardLibraryScreen.class, method = "updateCards")
public class CardsToLibraryBottomPatch{
    public static void Prefix(CardGroup ___visibleCards, CardLibSortHeader ___sortHeader){
        if(___sortHeader.justSorted){
            int greaterThan = 1;
            int equal = 0;
            int lessThan = -1;

            ___visibleCards.group.sort((card1, card2) -> {
                if(card1 instanceof AbstractDynamicBoosterPackCard){
                    return (card2 instanceof AbstractDynamicBoosterPackCard) ? equal : greaterThan;
                }
                return (card2 instanceof AbstractDynamicBoosterPackCard) ? lessThan : equal;
            });
        }
    }
}