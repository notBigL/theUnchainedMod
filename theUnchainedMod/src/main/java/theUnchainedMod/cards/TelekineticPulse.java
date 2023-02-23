package theUnchainedMod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.AllEnemiesGainBlockAction;
import theUnchainedMod.actions.AllEnemiesLoseHPAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.DeliciousChurroPower;
import theUnchainedMod.powers.WhiplashPower;
import theUnchainedMod.powers.WreckingBallPower;
import theUnchainedMod.relics.Churros;
import theUnchainedMod.vfx.TelekineticPulseWaveEffect;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class TelekineticPulse extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(TelekineticPulse.class.getSimpleName());
    public static final String IMG = makeCardPath("TelekineticPulse.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 2;
    private static final int CHAIN_LENGTH = 2;
    private static final int MAGIC_NUMBER = 25;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 6;
    private static final int SECOND_MAGIC_NUMBER = 7;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 3;

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

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new TelekineticPulseWaveEffect(p.hb.cX, p.hb.cY, 1000.0F * Settings.scale)));
        AbstractDungeon.actionManager.addToBottom(new AllEnemiesGainBlockAction(defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new WreckingBallPower(p, CHAIN_LENGTH, new AllEnemiesLoseHPAction(magicNumber), magicNumber, TYPE)));
        if (p.hasPower(DeliciousChurroPower.POWER_ID)) {
            p.getPower(DeliciousChurroPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new WreckingBallPower(p, CHAIN_LENGTH, new AllEnemiesLoseHPAction(magicNumber), magicNumber, TYPE)));
        }
    }
}
