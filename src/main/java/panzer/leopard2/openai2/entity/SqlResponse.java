package panzer.leopard2.openai2.entity;

import java.util.List;
import java.util.Map;

public record SqlResponse(String sqlQuery, List<Map<String, Object>> results) { }
