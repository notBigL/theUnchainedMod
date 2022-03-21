package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.BrassKnucklePower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class BrassKnuckle extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(BrassKnuckle.class.getSimpleName());
    public static final String IMG = makeCardPath("BrassKnuckle.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int CHAIN_LENGTH = 1;
    private static final int MAGIC_NUMBER = 1;

    public BrassKnuckle() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BrassKnucklePower(p, CHAIN_LENGTH, new GainEnergyAction(baseMagicNumber), TYPE)));
    }
}
