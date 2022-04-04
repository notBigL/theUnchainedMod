package theUnchainedMod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.MultiAttackAction;

public class SolarPlexusPower extends AbstractChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("SolarPlexusPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static AbstractPower.PowerType POWER_TYPE = PowerType.BUFF;

    private final AbstractMonster monster;
    private final int damageAmount;
    private final int attackAmount;

    public SolarPlexusPower(AbstractCreature owner, int amount, AbstractMonster monster, int damageAmount, int attackAmount, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, new MultiAttackAction(attackAmount, monster, new DamageInfo(owner, damageAmount, DamageInfo.DamageType.NORMAL)), cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        this.monster = monster;
        this.damageAmount = damageAmount;
        this.attackAmount = attackAmount;
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.damageAmount + DESCRIPTIONS[1] + this.attackAmount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[4] + this.damageAmount + DESCRIPTIONS[1] + this.attackAmount + DESCRIPTIONS[2];
        }
    }
}
