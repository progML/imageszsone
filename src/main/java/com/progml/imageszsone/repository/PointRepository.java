package com.progml.imageszsone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.progml.imageszsone.model.Point;

public interface PointRepository extends JpaRepository<Point, Long> {
}
