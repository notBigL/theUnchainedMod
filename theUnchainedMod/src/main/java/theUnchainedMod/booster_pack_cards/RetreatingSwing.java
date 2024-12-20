package theUnchainedMod.booster_pack_cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.AbstractDynamicBoosterPackCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class RetreatingSwing extends AbstractDynamicBoosterPackCard {

    public static final String ID = TheUnchainedMod.makeID(RetreatingSwing.class.getSimpleName());
    public static final String IMG = makeCardPath("RetreatingSwing.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 2;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC_NUMBER = 8;

    public RetreatingSwing() {
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
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("swingAttack", MathUtils.random(-0.2F, 0.2F));
        this.addToBot(new WallopAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }
}
