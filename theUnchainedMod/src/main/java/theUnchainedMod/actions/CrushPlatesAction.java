package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.powers.CrushedArmorPower;

public class CrushPlatesAction extends AbstractGameAction {

    private final DamageInfo info;

    public CrushPlatesAction(DamageInfo damageInfo, AbstractCreature m) {
        this.amount = damageInfo.base;
        this.target = m;
        this.info = damageInfo;
    }

    @Override
    public void update() {
        int dentArmorAmount;
        if (target.currentBlock > this.amount) {
            dentArmorAmount = this.amount;
        } else {
            dentArmorAmount = this.amount - (this.amount - target.currentBlock);
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(this.target, info, AttackEffect.BLUNT_HEAVY));
        if (dentArmorAmount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new CrushedArmorPower(target, AbstractDungeon.player, dentArmorAmount)));
        }
        this.isDone = true;
    }
}
