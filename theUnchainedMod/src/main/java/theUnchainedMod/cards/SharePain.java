package theUnchainedMod.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.MultiAttackAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.patches.RelayedDmgSum;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class SharePain extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(SharePain.class.getSimpleName());
    public static final String IMG = makeCardPath("SharePain.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 0;
    private static final int SECOND_MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 1;

    public SharePain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = 0;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
        }
    }

    @Override
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        this.baseMagicNumber = RelayedDmgSum.relayedDamageSum.get(AbstractDungeon.actionManager);
        this.baseDamage += this.baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseMagicNumber = RelayedDmgSum.relayedDamageSum.get(AbstractDungeon.actionManager);
        int realBaseDamage = this.baseDamage;
        this.baseDamage += this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage += this.magicNumber;
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new MultiAttackAction(defaultSecondMagicNumber, m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        //AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, true));
    }
}
