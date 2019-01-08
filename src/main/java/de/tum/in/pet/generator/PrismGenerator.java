package de.tum.in.pet.generator;

import de.tum.in.pet.util.PrismWrappedException;
import it.unimi.dsi.fastutil.objects.AbstractObject2DoubleMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import parser.State;
import prism.ModelGenerator;
import prism.PrismException;

public abstract class PrismGenerator implements Generator<State> {
  private final ModelGenerator generator;

  public PrismGenerator(ModelGenerator generator) {
    this.generator = generator;
  }

  public ModelGenerator generator() {
    return generator;
  }

  @Override
  public Collection<State> initialStates() throws PrismException {
    return generator.getInitialStates();
  }


  protected Collection<Object2DoubleMap.Entry<State>> transitions(int choiceIndex, int count) {
    return new TransitionLazyCollection(generator, count, choiceIndex);
  }

  private static final class TransitionLazyCollection
      extends AbstractCollection<Object2DoubleMap.Entry<State>> {
    private final ModelGenerator generator;
    private final int count;
    private final int choiceIndex;

    public TransitionLazyCollection(ModelGenerator generator, int count, int choiceIndex) {
      this.generator = generator;
      this.count = count;
      this.choiceIndex = choiceIndex;
    }

    @Override
    public Iterator<Object2DoubleMap.Entry<State>> iterator() {
      return new TransitionLazyIterator(generator, choiceIndex, count);
    }

    @Override
    public int size() {
      return count;
    }
  }

  private static final class TransitionLazyIterator
      implements Iterator<Object2DoubleMap.Entry<State>> {
    private final ModelGenerator generator;
    private final int transitionCount;
    private final int choiceIndex;

    private int transitionIndex = 0;

    TransitionLazyIterator(ModelGenerator generator, int choiceIndex, int transitionCount) {
      this.generator = generator;
      this.choiceIndex = choiceIndex;
      this.transitionCount = transitionCount;
    }

    @Override
    public boolean hasNext() {
      return transitionIndex < transitionCount;
    }

    @Override
    public Object2DoubleMap.Entry<State> next() {
      try {
        State target = generator.computeTransitionTarget(choiceIndex, transitionIndex);
        double probability = generator.getTransitionProbability(choiceIndex, transitionIndex);
        assert probability > 0.0d;

        var entry = new AbstractObject2DoubleMap.BasicEntry<>(target, probability);
        transitionIndex += 1;
        return entry;
      } catch (PrismException e) {
        throw new PrismWrappedException(e);
      }
    }
  }
}