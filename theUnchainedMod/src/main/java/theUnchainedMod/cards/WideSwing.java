package theUnchainedMod.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.OverHeadSwingEndTurnAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.AbstractChainPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class WideSwing extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(WideSwing.class.getSimpleName());
    public static final String IMG = makeCardPath("WideSwing.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    public WideSwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        this.isMultiDamage = true;
        tags.add(CustomTags.SWING);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof AbstractChainPower) {
                this.glowColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
                return;
            }
        }
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("swingAttack", MathUtils.random(-0.2F, 0.2F));
        this.addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new OverHeadSwingEndTurnAction(p));
    }
}
