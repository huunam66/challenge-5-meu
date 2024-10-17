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

public class DistrictsEntityEntityGraph implements EntityGraph {
  public static final DistrictsEntityEntityGraph NOOP = new DistrictsEntityEntityGraph(EntityGraph.NOOP);

  private final EntityGraph delegate;

  private DistrictsEntityEntityGraph(EntityGraph delegate) {
    this.delegate = delegate;
  }

  private DistrictsEntityEntityGraph(RootComposer rootComposer) {
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

    public DistrictsEntityEntityGraph ____() {
      return new DistrictsEntityEntityGraph(this);
    }

    public ProvinceEntityEntityGraph.NodeComposer<RootComposer> province() {
      List<String> path = new ArrayList<String>();
      path.add("province");
      entityGraphAttributePaths.add(path);
      return new ProvinceEntityEntityGraph.NodeComposer<RootComposer>(this, path);
    }

    public WardEntityEntityGraph.NodeComposer<RootComposer> wards() {
      List<String> path = new ArrayList<String>();
      path.add("wards");
      entityGraphAttributePaths.add(path);
      return new WardEntityEntityGraph.NodeComposer<RootComposer>(this, path);
    }
  }

  public static class NodeComposer<R> {
    public final R ____;

    private final List<String> path;

    public NodeComposer(R root, List<String> path) {
      this.____ = root;
      this.path = path;
    }

    public ProvinceEntityEntityGraph.NodeComposer<R> province() {
      path.add("province");
      return new ProvinceEntityEntityGraph.NodeComposer<R>(____, path);
    }

    public WardEntityEntityGraph.NodeComposer<R> wards() {
      path.add("wards");
      return new WardEntityEntityGraph.NodeComposer<R>(____, path);
    }
  }
}
