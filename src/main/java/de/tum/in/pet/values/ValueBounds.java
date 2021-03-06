package de.tum.in.pet.values;

import static de.tum.in.probmodels.util.Util.isEqual;

import de.tum.in.probmodels.util.annotation.Tuple;
import org.immutables.value.Value;

@Value.Immutable
@Tuple
abstract class ValueBounds extends Bounds {
  @Override
  public Bounds withUpper(double upperBound) {
    return of(lowerBound(), upperBound);
  }

  @Override
  public Bounds withLower(double lowerBound) {
    return of(lowerBound, upperBound());
  }

  @Override
  public String toString() {
    return isEqual(lowerBound(), upperBound())
        ? String.format("=%.5g", lowerBound())
        : String.format("[%.5g,%.5g]", lowerBound(), upperBound());
  }
}
