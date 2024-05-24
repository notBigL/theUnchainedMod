package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.cards.Swirl;

import java.util.Iterator;

public class MakeHasteAction extends AbstractGameAction {

    @Override
    public void update() {
        Iterator var1;
        AbstractCard c;
        int swirlsToExhaust = 0;

        var1 = AbstractDungeon.player.hand.group.iterator();

        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c instanceof Swirl){
                swirlsToExhaust++;
            }
        }
        for(int i = 0; i < swirlsToExhaust; i++)
        {
            this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null,
                    true, AbstractDungeon.cardRandomRng),
                    false));
        }
        this.isDone = true;
    }
}
