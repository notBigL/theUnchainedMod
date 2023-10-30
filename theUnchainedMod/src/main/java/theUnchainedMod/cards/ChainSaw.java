package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.ChainSawAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.AbstractChainPower;
import theUnchainedMod.powers.AbstractMasterChainPower;
import theUnchainedMod.vfx.ChainSawAttackEffect;

import java.util.Iterator;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class ChainSaw extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(ChainSaw.class.getSimpleName());
    public static final String IMG = makeCardPath("ChainSaw.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 15;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 5;

    public ChainSaw() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int amountToAdd = 0;
        int realBaseDamage = this.baseDamage;
        for (AbstractPower po : AbstractDungeon.player.powers) {
            if (po instanceof AbstractChainPower || po instanceof AbstractMasterChainPower) amountToAdd += magicNumber;
        }
        this.baseDamage += amountToAdd;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator var1 = AbstractDungeon.player.powers.iterator();

        while(var1.hasNext()) {
            AbstractPower power = (AbstractPower) var1.next();
            if (power instanceof AbstractChainPower || power instanceof AbstractMasterChainPower) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ChainSawAttackEffect(m.hb.cX, m.hb.cY, true)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ChainSawAction(this.uuid, magicNumber));
    }
}
