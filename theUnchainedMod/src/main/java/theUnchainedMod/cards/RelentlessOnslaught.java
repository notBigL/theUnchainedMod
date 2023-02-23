package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.MultiAttackAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.DeliciousChurroPower;
import theUnchainedMod.powers.RelentlessBatteryPower;
import theUnchainedMod.powers.WhiplashPower;
import theUnchainedMod.relics.Churros;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class RelentlessOnslaught extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(RelentlessOnslaught.class.getSimpleName());
    public static final String IMG = makeCardPath("RelentlessOnslaught.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 2;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int CHAIN_LENGTH = 1;
    private static final int MAGIC_NUMBER = 3;

    public RelentlessOnslaught() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        tags.add(CustomTags.CHAIN);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, this.damage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToBottom(new MultiAttackAction(magicNumber, m, info));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RelentlessBatteryPower(p, CHAIN_LENGTH, this.damage, this.magicNumber, m, info, TYPE)));
        if (p.hasPower(DeliciousChurroPower.POWER_ID)) {
            p.getPower(DeliciousChurroPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RelentlessBatteryPower(p, CHAIN_LENGTH, this.damage, this.magicNumber, m, info, TYPE)));
        }
    }
}
