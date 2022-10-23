package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.powers.MomentumPower;

public class ChainAction extends AbstractGameAction {
    private final AbstractCreature player;
    private final AbstractCard card;
    private final AbstractCard.CardType cardType;
    private final AbstractGameAction finishedChainAction;
    private final String ChainPowerID;
    private final String special;

    public ChainAction(AbstractCreature p, AbstractCard c, AbstractCard.CardType cT, AbstractGameAction fCA, String iD) {
        player = p;
        card = c;
        cardType = cT;
        finishedChainAction = fCA;
        ChainPowerID = iD;
        this.special = "";
    }

    public ChainAction(AbstractCreature p, AbstractCard c, AbstractCard.CardType cT, AbstractGameAction fCA, String iD, String special) {
        player = p;
        card = c;
        cardType = cT;
        finishedChainAction = fCA;
        ChainPowerID = iD;
        this.special = special;
    }

    @Override
    public void update() {
        AbstractPower chainPower = player.getPower(ChainPowerID);
        if (chainPower != null) {
            switch (special) {
                case "liberation":
                    this.addToBot(finishedChainAction);
                    player.powers.remove(chainPower);
                    checkForFinishers();
                    this.isDone = true;
                    break;
                case "link":
                    --chainPower.amount;
                    if (chainPower.amount == 0) {
                        this.addToBot(finishedChainAction);
                        player.powers.remove(chainPower);
                        checkForFinishers();
                    }
                    chainPower.updateDescription();
                    this.isDone = true;
                    break;
                default:
                    if (cardType == card.type) {
                        --chainPower.amount;
                    }
                    if (chainPower.amount == 0) {
                        this.addToBot(finishedChainAction);
                        player.powers.remove(chainPower);
                        checkForFinishers();
                    }
                    chainPower.updateDescription();
                    this.isDone = true;
                    break;
            }
        }
    }


    private void checkForFinishers() {
        if (player.hasPower("theUnchainedMod:AccelerationPower")) {
            player.getPower("theUnchainedMod:AccelerationPower").onSpecificTrigger();
        }
        if (player.hasPower("theUnchainedMod:FluidMovementPower")) {
            int momentumAmount = player.getPower("theUnchainedMod:FluidMovementPower").amount;
            if (momentumAmount > 0) {
                AbstractDungeon.actionManager.addToBottom(new GainMomentumAction(momentumAmount));
            }
        }
        if (player.hasPower("theUnchainedMod:ChaseDestinyPower")) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
    }
}
