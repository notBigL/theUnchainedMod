package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BaguazhangAction extends AbstractGameAction {

    private final AbstractMonster targetMonster;
    private final int damageAmount;

    public BaguazhangAction(AbstractPlayer p, AbstractMonster m, int blockAmount, int damageAmount) {
        this.source = p;
        this.targetMonster = m;
        this.amount = blockAmount;
        this.damageAmount = damageAmount;
    }

    @Override
    public void update() {
        if (targetMonster != null && targetMonster.getIntentBaseDmg() <= damageAmount) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, amount));
        }
        this.isDone = true;
    }
}
