package theUnchainedMod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.MultiAttackAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.DeliciousChurroPower;
import theUnchainedMod.powers.RelentlessOnslaughtPower;
import theUnchainedMod.util.UtilityClass;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class RelentlessOnslaught extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(RelentlessOnslaught.class.getSimpleName());
    public static final String IMG = makeCardPath("RelentlessOnslaught.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 2;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int CHAIN_LENGTH = 1;
    private static final int MAGIC_NUMBER = 2;

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

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if(UtilityClass.ChurrosPowerActivated()) this.glowColor = Color.PURPLE;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DamageInfo info = new DamageInfo(p, this.damage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToBottom(new MultiAttackAction(magicNumber, m, info));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RelentlessOnslaughtPower(p, CHAIN_LENGTH, this.damage, this.magicNumber, m, info, TYPE)));
        if (p.hasPower(DeliciousChurroPower.POWER_ID)) {
            p.getPower(DeliciousChurroPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new RelentlessOnslaughtPower(p, CHAIN_LENGTH, this.damage, this.magicNumber, m, info, TYPE)));
        }
    }
}
