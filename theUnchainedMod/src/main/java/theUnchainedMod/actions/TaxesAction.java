package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import theUnchainedMod.powers.TaxedPower;

public class TaxesAction extends AbstractGameAction {


    public TaxesAction(int amount) {
        this.amount = amount;
        this.source = AbstractDungeon.player;
    }


    @Override
    public void update() {
        for (AbstractCreature m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!m.isDeadOrEscaped() && !m.hasPower(TaxedPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new TaxedPower(m)));
                AbstractDungeon.actionManager.addToBottom(new GainGoldAction(amount));
                for (int i = 0; i < this.amount; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(this.source, m.hb.cX, m.hb.cY, this.source.hb.cX, this.source.hb.cY, true));
                }
            }
        }
        this.isDone = true;
    }
}
