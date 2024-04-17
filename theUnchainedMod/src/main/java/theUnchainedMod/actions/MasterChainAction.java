package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theUnchainedMod.powers.AbstractMasterChainPower;


public class MasterChainAction extends AbstractGameAction {
    private final AbstractCreature player;
    private final AbstractCard card;
    private final String ChainPowerID;
    private final AbstractMasterChainPower masterChainPower;

    public MasterChainAction(AbstractCreature p, AbstractCard c, String iD, AbstractMasterChainPower mcp) {
        player = p;
        card = c;
        ChainPowerID = iD;
        masterChainPower = mcp;
    }

    @Override
    public void update() {
        if (masterChainPower != null)
        {
            //  reduce the individual parts of the master chain
            if (card.type == AbstractCard.CardType.ATTACK && masterChainPower.attacksRequired > 0) {
                masterChainPower.attacksRequired--;
                masterChainPower.flash();
            }
            if (card.type == AbstractCard.CardType.POWER && masterChainPower.powersRequired > 0) {
                masterChainPower.powersRequired--;
                masterChainPower.flash();
            }
            if (card.type == AbstractCard.CardType.SKILL && masterChainPower.skillsRequired > 0) {
                masterChainPower.skillsRequired--;
                masterChainPower.flash();
            }

            masterChainPower.loadTextures();
            masterChainPower.updateDescription();

            //  Check if the master chain has been completed!
            if (masterChainPower.attacksRequired == 0 && masterChainPower.skillsRequired == 0 && masterChainPower.powersRequired == 0)
                masterChainPower.finishMe();

        }
        this.isDone = true;
    }
}
