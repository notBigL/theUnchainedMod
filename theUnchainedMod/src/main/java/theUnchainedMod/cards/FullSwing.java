package theUnchainedMod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;

import java.util.Iterator;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class FullSwing extends AbstractDynamicCard {
    public static final String ID = TheUnchainedMod.makeID(FullSwing.class.getSimpleName());
    public static final String IMG = makeCardPath("FullSwing.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    private static final int MAGIC_NUMBER = 5;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 2;

    public FullSwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        tags.add(CustomTags.SWING);
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.isMagicNumberModified = false;
        AbstractPlayer player = AbstractDungeon.player;
        float tmp = (float) this.baseMagicNumber;
        Iterator var3 = player.relics.iterator();

        while (var3.hasNext()) {
            AbstractRelic r = (AbstractRelic) var3.next();
            tmp = r.atDamageModify(tmp, this);
            if (this.baseMagicNumber != (int) tmp) {
                this.isMagicNumberModified = true;
            }
        }

        AbstractPower p;
        for (var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower) var3.next();
        }

        tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
        if (this.baseMagicNumber != (int) tmp) {
            this.isMagicNumberModified = true;
        }

        for (var3 = player.powers.iterator(); var3.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
            p = (AbstractPower) var3.next();
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        if (this.baseMagicNumber != MathUtils.floor(tmp)) {
            this.isMagicNumberModified = true;
        }

        this.magicNumber = MathUtils.floor(tmp);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        this.isMagicNumberModified = false;
        AbstractPlayer player = AbstractDungeon.player;
        if (mo != null) {
            float tmp = (float) this.baseMagicNumber;
            Iterator var9 = player.relics.iterator();

            while (var9.hasNext()) {
                AbstractRelic r = (AbstractRelic) var9.next();
                tmp = r.atDamageModify(tmp, this);
                if (this.baseMagicNumber != (int) tmp) {
                    this.isMagicNumberModified = true;
                }
            }

            AbstractPower p;
            for (var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower) var9.next();
            }

            tmp = player.stance.atDamageGive(tmp, this.damageTypeForTurn, this);
            if (this.baseMagicNumber != (int) tmp) {
                this.isMagicNumberModified = true;
            }

            for (var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageReceive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower) var9.next();
            }

            for (var9 = player.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalGive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower) var9.next();
            }

            for (var9 = mo.powers.iterator(); var9.hasNext(); tmp = p.atDamageFinalReceive(tmp, this.damageTypeForTurn, this)) {
                p = (AbstractPower) var9.next();
            }

            if (tmp < 0.0F) {
                tmp = 0.0F;
            }

            if (this.baseMagicNumber != MathUtils.floor(tmp)) {
                this.isMagicNumberModified = true;
            }

            this.magicNumber = MathUtils.floor(tmp);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        CardCrawlGame.sound.playA("swingAttack", MathUtils.random(-0.2F, 0.2F));
        this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }
}
