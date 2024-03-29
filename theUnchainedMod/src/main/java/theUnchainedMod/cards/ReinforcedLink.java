package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.ReinforcedElementAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.AbstractChainPower;
import theUnchainedMod.powers.AbstractMasterChainPower;

import java.util.Iterator;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class ReinforcedLink extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(ReinforcedLink.class.getSimpleName());
    public static final String IMG = makeCardPath("ReinforcedLink.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    public ReinforcedLink() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
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
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ReinforcedElementAction());
    }
}
