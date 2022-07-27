package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.MomentumPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class HalfSwing extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(HalfSwing.class.getSimpleName());
    public static final String IMG = makeCardPath("HalfSwing.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;


    private static final int COST = 1;
    private static final int DAMAGE = 11;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC_NUMBER = 3;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;

    public HalfSwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        tags.add(CustomTags.SWING);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ScryAction(magicNumber));
    }
}
