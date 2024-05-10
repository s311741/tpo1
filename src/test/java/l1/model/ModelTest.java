package l1.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
  private World world;
  private final static Class<? extends Throwable> iae = IllegalArgumentException.class;

  @BeforeEach
  void setup() {
    world = new World();

    world.addEntity("Arthur");
    world.addEntity("Building");
    world.addEntity("Window");
    world.addEntity("Stage");
    world.addEntity("Speaker");

    world.relate("Building", "Window", new SpatialRelation(Spatial.INSIDE, 0.0));
    world.relate("Arthur", "Window", new SpatialRelation(Spatial.NONE, 30.0));
    world.relate("Speaker", "Stage", new SpatialRelation(Spatial.ON_TOP, 0.0));
    world.relate("Building", "Stage", new SpatialRelation(Spatial.ACROSS, 50.0));
  }

  @Test
  void referencing() {
    final var nse = NoSuchElementException.class;
    assertThrows(nse, () -> world.relate("Somebody else", "Arthur", new SpatialRelation(Spatial.ACROSS, 20.0)));
    assertThrows(nse, () -> world.moveTowards("Somebody else", "Building", 20.0));
    assertDoesNotThrow(() -> world.moveTowards("Arthur", "Window", 5.0));
  }

  @Test
  void spatial() {
    assertThrows(iae, () -> world.moveTowards("Building", "Window", 5.0));
    assertDoesNotThrow(() -> world.moveTowards("Speaker", "Window", 10.0));
    assertThrows(iae, () -> world.moveTowards("Arthur", "Window", 50.0));
  }

  @Test
  void mentionsSpeedSign() {
    var msg = assertThrows(iae, () -> world.moveTowards("Arthur", "Window", -10.0)).getMessage();
    assertTrue(msg.contains("positive") || msg.contains("negative"));
  }
}
