package theUnchainedMod.packs;

import theUnchainedMod.cards.*;

import java.util.ArrayList;

import static theUnchainedMod.TheUnchainedMod.makeID;

public class UnchainedSupportPack extends AbstractUnchainedPack {
    public static final String ID = makeID("SupportPack");

    public UnchainedSupportPack() {
        super(ID, RoyalDecree.ID, new PackSummary(1, 3, 4, 2, 3));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(OffhandedSwing.ID);
        cards.add(GraspFuture.ID);
        cards.add(PropheticSwing.ID);

        cards.add(MuscleMemory.ID);
        cards.add(Prophecy.ID);
        cards.add(TieDown.ID);
        cards.add(Premonition.ID);
        cards.add(RoyalDecree.ID);

        cards.add(Taxes.ID);
        cards.add(Unshackling.ID);
        return cards;
    }
}