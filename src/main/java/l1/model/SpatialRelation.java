package l1.model;

public record SpatialRelation(Spatial kind, double distance) {
  public SpatialRelation {
    assert distance >= 0;
    assert !kind.mustHaveNonzeroDistance() || distance > 0;
    assert !kind.mustHaveZeroDistance() || distance == 0;
  }
}