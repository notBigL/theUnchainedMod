package theUnchainedMod.powers;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;

public class WhiplashPower extends AbstractChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheUnchainedMod.makeID("WhiplashPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static AbstractPower.PowerType POWER_TYPE = PowerType.BUFF;
    private final int chainDamage;


    public WhiplashPower(AbstractCreature owner, int amount, AbstractMonster monster, int damageAmount, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, new DamageAction(monster, new DamageInfo(owner, damageAmount, DamageInfo.DamageType.HP_LOSS)), cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        chainDamage = damageAmount;
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.chainDamage + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3] + this.chainDamage + DESCRIPTIONS[1];
        }
    }
}
