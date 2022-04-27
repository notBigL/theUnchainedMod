package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.MultiAttackAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.RelentlessBatteryPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class RelentlessBattery extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(RelentlessBattery.class.getSimpleName());
    public static final String IMG = makeCardPath("RelentlessBattery.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int CHAIN_LENGTH = 1;
    private static final int MAGIC_NUMBER = 3;

    public RelentlessBattery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        this.exhaust = true;
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
        DamageInfo info = new DamageInfo(p, this.damage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToBottom(new MultiAttackAction(magicNumber, m, info));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RelentlessBatteryPower(p, CHAIN_LENGTH, this.damage, this.magicNumber, m, info, TYPE)));
    }
}
