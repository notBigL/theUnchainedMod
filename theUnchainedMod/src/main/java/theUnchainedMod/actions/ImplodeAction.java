package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.powers.CrushedArmorPower;

public class ImplodeAction extends AbstractGameAction {


    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
        this.tickDuration();
        if (this.isDone) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDeadOrEscaped() && mo.hasPower(CrushedArmorPower.POWER_ID)) {
                    int crushedArmorAmount = mo.getPower(CrushedArmorPower.POWER_ID).amount;
                    if (crushedArmorAmount > 0) {
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(AbstractDungeon.player, crushedArmorAmount, DamageInfo.DamageType.HP_LOSS), AttackEffect.FIRE));
                    }
                }
            }
        }
    }
}
