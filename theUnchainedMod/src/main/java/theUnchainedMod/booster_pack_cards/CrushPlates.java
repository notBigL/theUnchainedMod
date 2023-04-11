package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.ApplyCrushedArmorAction;
import theUnchainedMod.cards.AbstractDynamicCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.CrushedArmorPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class CrushPlates extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(CrushPlates.class.getSimpleName());
    public static final String IMG = makeCardPath("CrushPlates.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 3;
    private static final int DAMAGE = 0;
    //private static final int UPGRADE_PLUS_DMG = 4;
    //private static final int MAGIC_NUMBER = 6; // the +X on the description
    //private static final int UPGRADE_PLUS_MAGIC_NUMBER = 4;

    private static final CardStrings STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);

    public CrushPlates() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        //baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeDamage(UPGRADE_PLUS_DMG);
            //upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }
    @Override
    public void applyPowers() { //your own powers
        super.applyPowers();
        this.rawDescription = STRINGS.DESCRIPTION + STRINGS.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }
    @Override
    public void calculateCardDamage(AbstractMonster mo) { // vuln and extra stuff
        int realBaseDamage = this.baseDamage;
        int enemyCrushedArmor = getEnemyCrushedArmor(mo);
        baseDamage += (enemyCrushedArmor * 2);
        super.calculateCardDamage(mo);
        baseDamage = realBaseDamage;

        this.rawDescription = STRINGS.DESCRIPTION + STRINGS.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }
    public int getEnemyCrushedArmor(AbstractMonster mo)
    {
        int enemyCrushedArmor = 0;
        if(mo.hasPower(CrushedArmorPower.POWER_ID)) enemyCrushedArmor = mo.getPower(CrushedArmorPower.POWER_ID).amount;
        return enemyCrushedArmor;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new ApplyCrushedArmorAction(m, getEnemyCrushedArmor(m)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
}
