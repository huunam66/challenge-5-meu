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

public class ProfileLocationEntityEntityGraph implements EntityGraph {
  public static final ProfileLocationEntityEntityGraph NOOP = new ProfileLocationEntityEntityGraph(EntityGraph.NOOP);

  private final EntityGraph delegate;

  private ProfileLocationEntityEntityGraph(EntityGraph delegate) {
    this.delegate = delegate;
  }

  private ProfileLocationEntityEntityGraph(RootComposer rootComposer) {
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

    public ProfileLocationEntityEntityGraph ____() {
      return new ProfileLocationEntityEntityGraph(this);
    }

    public ProfileEntityEntityGraph.NodeComposer<RootComposer> profile() {
      List<String> path = new ArrayList<String>();
      path.add("profile");
      entityGraphAttributePaths.add(path);
      return new ProfileEntityEntityGraph.NodeComposer<RootComposer>(this, path);
    }

    public WardEntityEntityGraph.NodeComposer<RootComposer> ward() {
      List<String> path = new ArrayList<String>();
      path.add("ward");
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

    public ProfileEntityEntityGraph.NodeComposer<R> profile() {
      path.add("profile");
      return new ProfileEntityEntityGraph.NodeComposer<R>(____, path);
    }

    public WardEntityEntityGraph.NodeComposer<R> ward() {
      path.add("ward");
      return new WardEntityEntityGraph.NodeComposer<R>(____, path);
    }
  }
}
