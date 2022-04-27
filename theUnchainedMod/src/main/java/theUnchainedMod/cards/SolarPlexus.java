package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.MultiAttackAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.SolarPlexusPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class SolarPlexus extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(SolarPlexus.class.getSimpleName());
    public static final String IMG = makeCardPath("SolarPlexus.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 4;
    private static final int MAGIC_NUMBER = 4;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;
    private static final int SECOND_MAGIC_NUMBER = 3;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = -1;


    public SolarPlexus() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MultiAttackAction(magicNumber, m, new DamageInfo(p, damage, damageTypeForTurn)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SolarPlexusPower(p, defaultSecondMagicNumber, m, CardType.ATTACK)));
    }
}
