package theUnchainedMod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.DefaultMod;

public class InertiaPower extends AbstractChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("InertiaPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static AbstractPower.PowerType POWER_TYPE = PowerType.BUFF;
    private final int energyAmount;


    public InertiaPower(AbstractCreature owner, int amount, int energyAmount, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, new GainEnergyAction(energyAmount), cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        this.energyAmount = energyAmount;
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.energyAmount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3] + DESCRIPTIONS[1];
        }
    }
}
