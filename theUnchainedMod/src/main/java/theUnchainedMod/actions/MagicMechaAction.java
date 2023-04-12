package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theUnchainedMod.patches.RelayHelpers;
import theUnchainedMod.powers.ArcaneMasteryPower;

public class MagicMechaAction extends AbstractGameAction {

    public MagicMechaAction(int amount, AbstractCreature p) {
        this.amount = amount;
        this.target = p;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new StrengthPower(target, this.amount), this.amount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new DexterityPower(target, this.amount), this.amount));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new ArcaneMasteryPower(target, this.amount), this.amount));
        this.isDone = true;
    }
}
