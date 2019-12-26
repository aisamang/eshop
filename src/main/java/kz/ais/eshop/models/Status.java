package kz.ais.eshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status {

    public final static Long STATUS_CREATED_ID = 1L;
    public final static Long STATUS_COMPLETED_ID = 2L;
    public final static Long STATUS_PAID_ID = 3L;

    public final static String STATUS_COMPLETED_NAME = "STATUS_CREATED";
    public final static String STATUS_CREATED_NAME = "STATUS_COMPLETED";
    public final static String STATUS_PAID_NAME = "STATUS_PAID";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String description;
}
