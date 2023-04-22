package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.DamoclesChainPower;
import theUnchainedMod.powers.DeliciousChurroPower;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class SwordOfDamocles extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(SwordOfDamocles.class.getSimpleName());
    public static final String IMG = makeCardPath("SwordOfDamocles.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 4; // Temp Strength
    private static final int UPGRADE_MAGIC_NUMBER = 2;
    private static final int CHAIN_LENGTH = 1; //Chain Length
    private static final int SECOND_MAGIC_NUMBER = 8; // Self Damage

    public SwordOfDamocles() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        this.exhaust = true;
        tags.add(CustomTags.CHAIN);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DamoclesChainPower(p, CHAIN_LENGTH, SECOND_MAGIC_NUMBER,  magicNumber, TYPE)));
        if (p.hasPower(DeliciousChurroPower.POWER_ID)) {
            p.getPower(DeliciousChurroPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DamoclesChainPower(p, CHAIN_LENGTH, SECOND_MAGIC_NUMBER, magicNumber, TYPE)));
        }
    }
}
