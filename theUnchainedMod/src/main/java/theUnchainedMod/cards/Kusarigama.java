package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class Kusarigama extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(Kusarigama.class.getSimpleName());
    public static final String IMG = makeCardPath("Kusarigama.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 2;
    private static final int DAMAGE = 30;
    private static final int UPGRADE_PLUS_DMG = 6;

    public Kusarigama() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        this.isMultiDamage = true;
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
        CardCrawlGame.sound.play("unchainedSelect");
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(!super.canUse(p, m)) return false;

        for (AbstractPower aP : p.powers) {
            if (aP.name.equals("Attack Chain") && aP.amount == 1) {
                return true;
            }
        }
        this.cantUseMessage = languagePack.getCardStrings(ID).EXTENDED_DESCRIPTION[0];
        return false;
    }
}
