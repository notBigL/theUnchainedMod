package theUnchainedMod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.LoseAllEnergyAction;

public class LastResortPower extends AbstractChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheUnchainedMod.makeID("LastResortPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static AbstractPower.PowerType POWER_TYPE = PowerType.BUFF;


    public LastResortPower(AbstractCreature owner, int amount, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, new LoseAllEnergyAction(), cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}
