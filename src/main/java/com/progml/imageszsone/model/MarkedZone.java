package com.progml.imageszsone.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class MarkedZone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private long imageId;

    @OneToOne
    private Point leftTop;

    @OneToOne
    private Point rightBottom;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<String> tags;
}
