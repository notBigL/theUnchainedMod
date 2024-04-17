package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CrushPlatesAction extends AbstractGameAction {

    private final DamageInfo info;

    public CrushPlatesAction(DamageInfo damageInfo, AbstractCreature m) {
        this.amount = damageInfo.base;
        this.target = m;
        this.info = damageInfo;
    }

    @Override
    public void update() {
        int crushedArmorAmount;
        if (target.currentBlock > this.amount) {
            crushedArmorAmount = this.amount;
        } else {
            crushedArmorAmount = this.amount - (this.amount - target.currentBlock);
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, info, AttackEffect.BLUNT_HEAVY));
        if (crushedArmorAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyCrushedArmorAction(target, crushedArmorAmount));
        }
        this.isDone = true;
    }
}
