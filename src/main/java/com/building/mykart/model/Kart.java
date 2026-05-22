package com.building.mykart.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Map;

@Entity
@Table(name="kart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private List<Map<String, Object>> items;

    @Column(name = "user_id")
    private Long userId;
}
