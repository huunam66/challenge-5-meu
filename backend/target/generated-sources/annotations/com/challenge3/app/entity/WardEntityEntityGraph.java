package com.challenge3.app.entity;

import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphQueryHint;
import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraphType;
import jakarta.persistence.EntityManager;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WardEntityEntityGraph implements EntityGraph {
  public static final WardEntityEntityGraph NOOP = new WardEntityEntityGraph(EntityGraph.NOOP);

  private final EntityGraph delegate;

  private WardEntityEntityGraph(EntityGraph delegate) {
    this.delegate = delegate;
  }

  private WardEntityEntityGraph(RootComposer rootComposer) {
    this(new DynamicEntityGraph(rootComposer.entityGraphType, rootComposer.entityGraphAttributePaths.stream().map(pathParts -> String.join(".", pathParts)).collect(Collectors.toList())));
  }

  public static RootComposer ____() {
    return new RootComposer();
  }

  public static RootComposer ____(EntityGraphType entityGraphType) {
    return new RootComposer(entityGraphType);
  }

  @Override
  public Optional<EntityGraphQueryHint> buildQueryHint(EntityManager entityManager,
      Class<?> entityType) {
    return delegate.buildQueryHint(entityManager, entityType);
  }

  public static class RootComposer {
    private final EntityGraphType entityGraphType;

    private final List<List<String>> entityGraphAttributePaths;

    private RootComposer() {
      this(EntityGraphType.LOAD);
    }

    private RootComposer(EntityGraphType entityGraphType) {
      this.entityGraphType = entityGraphType;
      entityGraphAttributePaths = new ArrayList<List<String>>();
    }

    public WardEntityEntityGraph ____() {
      return new WardEntityEntityGraph(this);
    }

    public DistrictsEntityEntityGraph.NodeComposer<RootComposer> district() {
      List<String> path = new ArrayList<String>();
      path.add("district");
      entityGraphAttributePaths.add(path);
      return new DistrictsEntityEntityGraph.NodeComposer<RootComposer>(this, path);
    }

    public ProfileLocationEntityEntityGraph.NodeComposer<RootComposer> profileLocations() {
      List<String> path = new ArrayList<String>();
      path.add("profileLocations");
      entityGraphAttributePaths.add(path);
      return new ProfileLocationEntityEntityGraph.NodeComposer<RootComposer>(this, path);
    }
  }

  public static class NodeComposer<R> {
    public final R ____;

    private final List<String> path;

    public NodeComposer(R root, List<String> path) {
      this.____ = root;
      this.path = path;
    }

    public DistrictsEntityEntityGraph.NodeComposer<R> district() {
      path.add("district");
      return new DistrictsEntityEntityGraph.NodeComposer<R>(____, path);
    }

    public ProfileLocationEntityEntityGraph.NodeComposer<R> profileLocations() {
      path.add("profileLocations");
      return new ProfileLocationEntityEntityGraph.NodeComposer<R>(____, path);
    }
  }
}
