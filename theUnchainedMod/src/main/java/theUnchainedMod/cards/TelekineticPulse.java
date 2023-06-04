package theUnchainedMod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.AllEnemiesGainBlockAction;
import theUnchainedMod.actions.AllEnemiesLoseHPAction;
import theUnchainedMod.actions.TelekineticPulseAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.DeliciousChurroPower;
import theUnchainedMod.powers.TelekineticPulsePower;
import theUnchainedMod.util.UtilityClass;
import theUnchainedMod.vfx.TelekineticPulseWaveEffect;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class TelekineticPulse extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(TelekineticPulse.class.getSimpleName());
    public static final String IMG = makeCardPath("TelekineticPulse.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 1;
    private static final int CHAIN_LENGTH = 1;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 2;
    private static final int SECOND_MAGIC_NUMBER = 5;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 2;

    public TelekineticPulse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        tags.add(CustomTags.CHAIN);
        this.isMultiDamage = true;
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
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new TelekineticPulsePower(p, CHAIN_LENGTH, new TelekineticPulseAction(magicNumber, defaultSecondMagicNumber ), magicNumber, defaultSecondMagicNumber, TYPE)));
        if (p.hasPower(DeliciousChurroPower.POWER_ID)) {
            p.getPower(DeliciousChurroPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new TelekineticPulsePower(p, CHAIN_LENGTH, new TelekineticPulseAction(magicNumber, defaultSecondMagicNumber), magicNumber, defaultSecondMagicNumber, TYPE)));}
    }
}
