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

public class ProfileEntityEntityGraph implements EntityGraph {
  public static final ProfileEntityEntityGraph NOOP = new ProfileEntityEntityGraph(EntityGraph.NOOP);

  private final EntityGraph delegate;

  private ProfileEntityEntityGraph(EntityGraph delegate) {
    this.delegate = delegate;
  }

  private ProfileEntityEntityGraph(RootComposer rootComposer) {
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

    public ProfileEntityEntityGraph ____() {
      return new ProfileEntityEntityGraph(this);
    }

    public ProfileLocationEntityEntityGraph.NodeComposer<RootComposer> profileLocation() {
      List<String> path = new ArrayList<String>();
      path.add("profileLocation");
      entityGraphAttributePaths.add(path);
      return new ProfileLocationEntityEntityGraph.NodeComposer<RootComposer>(this, path);
    }

    public UserEntityEntityGraph.NodeComposer<RootComposer> user() {
      List<String> path = new ArrayList<String>();
      path.add("user");
      entityGraphAttributePaths.add(path);
      return new UserEntityEntityGraph.NodeComposer<RootComposer>(this, path);
    }
  }

  public static class NodeComposer<R> {
    public final R ____;

    private final List<String> path;

    public NodeComposer(R root, List<String> path) {
      this.____ = root;
      this.path = path;
    }

    public ProfileLocationEntityEntityGraph.NodeComposer<R> profileLocation() {
      path.add("profileLocation");
      return new ProfileLocationEntityEntityGraph.NodeComposer<R>(____, path);
    }

    public UserEntityEntityGraph.NodeComposer<R> user() {
      path.add("user");
      return new UserEntityEntityGraph.NodeComposer<R>(____, path);
    }
  }
}
