package theUnchainedMod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.CuffPower;
import theUnchainedMod.powers.DeliciousChurroPower;
import theUnchainedMod.util.UtilityClass;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class Cuff extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(Cuff.class.getSimpleName());
    public static final String IMG = makeCardPath("Cuff.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;
    public static final String UPGRADE_DESCRIPTION = languagePack.getCardStrings(ID).UPGRADE_DESCRIPTION;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;
    private static final int SECOND_MAGIC_NUMBER = 1;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 1;

    public Cuff() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        tags.add(CustomTags.CHAIN);
        this.isInnate = true;
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

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(UtilityClass.ChurrosPowerActivated()) this.glowColor = Color.PURPLE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, false, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, defaultSecondMagicNumber, false)));
        CardCrawlGame.sound.play("normalChainAttack");
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CuffPower(p, 2, m, magicNumber, TYPE)));
        if (p.hasPower(DeliciousChurroPower.POWER_ID)) {
            p.getPower(DeliciousChurroPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new CuffPower(p, 2, m, magicNumber, TYPE)));
        }
    }
}
