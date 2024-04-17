package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.cards.Swirl;
import theUnchainedMod.powers.MomentumPower;

public class GainMomentumAction extends AbstractGameAction {

    private final AbstractPlayer player;
    private final boolean upgraded;

    public GainMomentumAction(int momentumAmount) {
        this(momentumAmount, false);
    }

    public GainMomentumAction() {
        this(1);
    }

    public GainMomentumAction(int momentumAmount, boolean upgraded) {
        this.amount = momentumAmount;
        this.player = AbstractDungeon.player;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int tempAmount = this.amount;
        if(player.hasPower("theUnchainedMod:MomentumPower")) {
            tempAmount += player.getPower("theUnchainedMod:MomentumPower").amount;
        }

        Swirl card = new Swirl();
        if(upgraded) {
            card.upgrade();
        }
        int amountOfSwirls = 0;
        if (player.hasPower("theUnchainedMod:FullSpinPower")) {
            card.fullSpinApply(player.getPower("theUnchainedMod:FullSpinPower").amount);
        }

        while (tempAmount >= 3) {
            amountOfSwirls++;
            tempAmount -= 3;
        }
        for(int i = amountOfSwirls; i > 0; i--) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1, false));
        }
        if(tempAmount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(player, player, "theUnchainedMod:MomentumPower"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new MomentumPower(player, tempAmount)));
        }
        this.isDone = true;
    }
}

//TODO Check if this really works the way I want, and if not, put this Method back into MomentumPower into the Constructor and the StackPower method
/* private int checkForMomentumRequired(int amount) {
        Swirl card = new Swirl();
        int amountOfSwirls = 0;
        if (this.owner.hasPower("theUnchainedMod:FullSpinPower")) {
            card.fullSpinApply(this.owner.getPower("theUnchainedMod:FullSpinPower").amount);
        }
        while (amount >= momentumRequired) {
            amountOfSwirls++;
            amount -= momentumRequired;
        }
        for(int i = amountOfSwirls; i > 0; i--) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1, false));
        }
        if(amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
        return amount;
    }
 */
