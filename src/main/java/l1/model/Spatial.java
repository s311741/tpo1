package l1.model;

public enum Spatial {
  NONE,
  UNKNOWN,
  ACROSS,
  INSIDE,
  ON_TOP;

  public boolean mustHaveNonzeroDistance() {
    return this == ACROSS || this == NONE;
  }

  public boolean mustHaveZeroDistance() {
    return this == INSIDE || this == ON_TOP;
  }
}
