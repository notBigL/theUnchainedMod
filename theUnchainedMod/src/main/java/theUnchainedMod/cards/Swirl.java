package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.GainMomentumAction;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.FullSpinPower;
import theUnchainedMod.powers.MomentumPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class Swirl extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Swirl.class.getSimpleName());
    public static final String IMG = makeCardPath("Swirl.png");
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColor.COLORLESS;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private static final int BLOCK = 2;
    private int realBaseDamage;
    private int realBaseBlock;


    public Swirl() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        realBaseDamage = DAMAGE;
        realBaseBlock = BLOCK;
        baseDamage = damage = DAMAGE;
        baseBlock = block = BLOCK;
        tags.add(CustomTags.MOMENTUM);
        this.isMultiDamage = true;
        this.exhaust = true;
        this.selfRetain = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    public void fullSpinApply(int amountOfFullSpins) {
        int multiplier = amountOfFullSpins + 1;
        this.baseDamage = (int) Math.pow(realBaseDamage, multiplier);
        this.baseBlock = (int) Math.pow(realBaseBlock, multiplier);
    }

    public void revertToBaseValues() {
        this.baseDamage = this.damage = realBaseDamage;
        this.baseBlock = this.block = realBaseBlock;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
        if (p.hasPower("theUnchainedMod:SwirlsHitAllEnemiesPower")) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FullSpinPower(p)));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MomentumPower(p)));
        }
    }
}
