package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.powers.RelayPower;
import theUnchainedMod.powers.RelayedDamagePower;

public class PeaceOfferingAction extends AbstractGameAction {

    public PeaceOfferingAction() {
        this.amount = 0;
        if (AbstractDungeon.player.hasPower(RelayedDamagePower.POWER_ID)) {
            this.amount = AbstractDungeon.player.getPower(RelayedDamagePower.POWER_ID).amount;
        }
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToBottom(new GainRelayAction(player, this.amount));        }
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo.currentBlock > 0 && !mo.isDead) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, mo.currentBlock));
            }
        }
        this.isDone = true;
    }
}
