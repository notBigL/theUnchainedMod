package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theUnchainedMod.powers.RelayPower;

public class GainRelayAction extends AbstractGameAction {

    public GainRelayAction(AbstractCreature target, int amount) {
        this.amount = amount;
        this.target = target;
    }

    @Override
    public void update() {
        if(!target.isDying && !target.isDead) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SHIELD));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new RelayPower(target, target, amount)));
        }
        this.isDone = true;
    }
}
