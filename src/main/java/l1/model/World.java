package l1.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class World {
  private final Map<CommutativePair, SpatialRelation> arrangement = new HashMap<>();
  private final Map<String, Integer> entities = new HashMap<>();
  private int nextId = 1;

  public void addEntity(String name) {
    entities.put(name, nextId++);
  }

  private CommutativePair getNamedPair(String a, String b) throws NoSuchElementException {
    var ea = entities.get(a);
    var eb = entities.get(b);
    if (ea == null || eb == null) {
      throw new NoSuchElementException();
    }
    return new CommutativePair(ea, eb);
  }

  private void relate(CommutativePair p, SpatialRelation r) {
    arrangement.put(p, r);
  }

  public void relate(String a, String b, SpatialRelation relation) throws NoSuchElementException {
    relate(getNamedPair(a, b), relation);
  }

  public void moveTowards(String a, String b, double speed) throws NoSuchElementException, IllegalArgumentException {
    var pair = getNamedPair(a, b);
    var rel = arrangement.get(pair);
    if (rel == null || rel.kind() == Spatial.UNKNOWN) {
      return; // nothing happens
    }
    if (rel.kind().mustHaveZeroDistance()) {
      throw new IllegalArgumentException();
    }
    if (speed < 0) {
      throw new IllegalArgumentException("Speed cannot be negative");
    }
    if (speed >= rel.distance()) {
      throw new IllegalArgumentException("Moving too fast, collision!");
    }
    var newRel = new SpatialRelation(rel.kind(), rel.distance() - speed);
    relate(pair, newRel);
  }
}