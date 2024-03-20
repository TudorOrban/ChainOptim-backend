package org.chainoptim.features.scanalysis.production.productgraph.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chainoptim.features.scanalysis.production.factorygraph.model.Edge;
import org.chainoptim.features.scanalysis.production.factorygraph.model.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGraph {
    private Map<Integer, Node> nodes = new HashMap<>(); // Key: stageId
    private Map<Integer, List<Edge>> adjList = new HashMap<>(); // Key: stageId

}
